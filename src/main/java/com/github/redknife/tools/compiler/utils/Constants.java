/*
 * Copyright 2019-2119 gao_xianglong@sina.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.redknife.tools.compiler.utils;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.UUID;

/**
 * 缺省静态常量相关
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/13 2:21 下午
 */
public class Constants {
    public final static String SEPARATE = File.separator;
    public final static String FILE_ENCODING = System.getProperty("file.encoding");
    public final static String SOURCE_CODE_FILE_POSTFIX = ".rs";
    public final static String TARGET_CODE_FILE_POSTFIX = ".class";
    public final static String OUTPUT_PATH = System.getProperty("java.io.tmpdir");
    public final static String OS_NAME = System.getProperty("os.name");
    public final static String COPYRIGHT = "gao_xianglong@sina.com. All Rights Reserved.";
    public final static String IGNITE_DOCUMENTATION = "https://github.com/gaoxianglong/red-knife-compiler";
    public final static String VERSION = "0.2-SNAPSHOT";
    public static final String LINE = System.getProperty("line.separator");
    public final static String EXCEPTION_PREFIX = "编译失败";

    public final static String JAVA_RUNTIME_TIME = System.getProperty("java.runtime.name");
    public final static String JAVA_RUNTIME_VERSION = System.getProperty("java.runtime.version");
    public final static String JAVA_VM_VENDOR = System.getProperty("java.vm.vendor");
    public final static String JAVA_VM_NAME = System.getProperty("java.vm.name");
    public final static MemoryMXBean MEMORY_BEAN = ManagementFactory.getMemoryMXBean();
    public final static long INIT_HEAP = MEMORY_BEAN.getHeapMemoryUsage().getInit();
    public final static long USE_HEAP = MEMORY_BEAN.getHeapMemoryUsage().getUsed();
    public final static long MAX_HEAP = MEMORY_BEAN.getHeapMemoryUsage().getMax();
    public final static RuntimeMXBean RUNTIME_BEAN = ManagementFactory.getRuntimeMXBean();
    public final static int PID = Integer.parseInt(RUNTIME_BEAN.getName().split("@")[0]);
}
