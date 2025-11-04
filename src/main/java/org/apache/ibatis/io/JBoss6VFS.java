/*
 *    Copyright 2009-2024 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.io;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

/**
 * JBoss 6虚拟文件系统(VFS)实现类，用于处理JBoss 6提供的VFS API。
 *
 * @author heng
 * @date 2025年11月04日22:38:43
 */
public class JBoss6VFS extends VFS {
    private static final Log log = LogFactory.getLog(JBoss6VFS.class);
    private static final ReentrantLock lock = new ReentrantLock();

    /** 模拟JBoss VirtualFile类的一个小子集的内部类 */
    static class VirtualFile {
        static Class<?> VirtualFile;
        static Method getPathNameRelativeTo;
        static Method getChildrenRecursively;

        Object virtualFile;

        VirtualFile(Object virtualFile) {
            this.virtualFile = virtualFile;
        }

        /**
         * 获取相对于父目录的路径名
         *
         * @param parent 父级VirtualFile对象
         * @return 相对于父级的路径名
         */
        String getPathNameRelativeTo(VirtualFile parent) {
            try {
                return invoke(getPathNameRelativeTo, virtualFile, parent.virtualFile);
            } catch (IOException e) {
                // This exception is not thrown by the called method
                log.error("This should not be possible. VirtualFile.getPathNameRelativeTo() threw IOException.");
                return null;
            }
        }

        /**
         * 获取子文件列表
         *
         * @return 子文件列表
         * @throws IOException IO异常
         */
        List<VirtualFile> getChildren() throws IOException {
            List<?> objects = invoke(getChildrenRecursively, virtualFile);
            List<VirtualFile> children = new ArrayList<>(objects.size());
            for (Object object : objects) {
                children.add(new VirtualFile(object));
            }
            return children;
        }
    }

    /** 模拟JBoss VFS类的一个小子集的内部类 */
    static class VFS {
        static Class<?> VFS;
        static Method getChild;

        private VFS() {
            // Prevent Instantiation
        }

        /**
         * 根据URL获取VirtualFile对象
         *
         * @param url URL地址
         * @return VirtualFile对象
         * @throws IOException IO异常
         */
        static VirtualFile getChild(URL url) throws IOException {
            Object o = invoke(getChild, VFS, url);
            return o == null ? null : new VirtualFile(o);
        }
    }

    /** 标记此VFS在当前环境下是否有效的标志 */
    private static Boolean valid;

    /**
     * 初始化方法，查找访问JBoss 6 VFS所需的所有类和方法
     */
    protected static void initialize() {
        lock.lock();
        try {
            if (valid == null) {
                // Assume valid. It will get flipped later if something goes wrong.
                valid = Boolean.TRUE;

                // Look up and verify required classes
                VFS.VFS = checkNotNull(getClass("org.jboss.vfs.VFS"));
                VirtualFile.VirtualFile = checkNotNull(getClass("org.jboss.vfs.VirtualFile"));

                // Look up and verify required methods
                VFS.getChild = checkNotNull(getMethod(VFS.VFS, "getChild", URL.class));
                VirtualFile.getChildrenRecursively = checkNotNull(getMethod(VirtualFile.VirtualFile,
                        "getChildrenRecursively"));
                VirtualFile.getPathNameRelativeTo = checkNotNull(
                        getMethod(VirtualFile.VirtualFile, "getPathNameRelativeTo", VirtualFile.VirtualFile));

                // Verify that the API has not changed
                checkReturnType(VFS.getChild, VirtualFile.VirtualFile);
                checkReturnType(VirtualFile.getChildrenRecursively, List.class);
                checkReturnType(VirtualFile.getPathNameRelativeTo, String.class);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 验证提供的对象引用是否为空。如果为空，则将此VFS标记为在当前环境中无效。
     *
     * @param <T>    泛型类型
     * @param object 要检查空值的对象引用
     * @return 原始对象
     */
    protected static <T> T checkNotNull(T object) {
        if (object == null) {
            setInvalid();
        }
        return object;
    }

    /**
     * 验证方法的返回类型是否符合预期。如果不符合，则将此VFS标记为在当前环境中无效。
     *
     * @param method   要检查其返回类型的方法
     * @param expected 方法返回类型必须可分配给的类型
     * @see Class#isAssignableFrom(Class)
     */
    protected static void checkReturnType(Method method, Class<?> expected) {
        if (method != null && !expected.isAssignableFrom(method.getReturnType())) {
            log.error("Method " + method.getClass().getName() + "." + method.getName() + "(..) should return "
                    + expected.getName() + " but returns " + method.getReturnType().getName() + " instead.");
            setInvalid();
        }
    }

    /**
     * 将此{@link VFS}标记为在当前环境中无效
     */
    protected static void setInvalid() {
        if (JBoss6VFS.valid.booleanValue()) {
            log.debug("JBoss 6 VFS API is not available in this environment.");
            JBoss6VFS.valid = Boolean.FALSE;
        }
    }

    static {
        initialize();
    }

    /**
     * 判断此VFS实现是否在当前环境有效
     *
     * @return 如果有效返回true，否则返回false
     */
    @Override
    public boolean isValid() {
        return valid;
    }

    /**
     * 列出指定URL和路径下的所有资源名称
     *
     * @param url  URL地址
     * @param path 路径
     * @return 资源名称列表
     * @throws IOException IO异常
     */
    @Override
    public List<String> list(URL url, String path) throws IOException {
        VirtualFile directory;
        directory = VFS.getChild(url);
        if (directory == null) {
            return Collections.emptyList();
        }

        if (!path.endsWith("/")) {
            path += "/";
        }

        List<VirtualFile> children = directory.getChildren();
        List<String> names = new ArrayList<>(children.size());
        for (VirtualFile vf : children) {
            names.add(path + vf.getPathNameRelativeTo(directory));
        }

        return names;
    }
}