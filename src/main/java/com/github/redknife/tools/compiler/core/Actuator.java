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
package com.github.redknife.tools.compiler.core;

import com.github.redknife.tools.compiler.exceptions.ExecuteException;
import com.github.redknife.tools.compiler.utils.Constants;
import com.github.redknife.tools.compiler.utils.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 执行器
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/28 4:49 下午
 */
public class Actuator {
    private List<String> classes;
    private Context context;
    private Logger log = LoggerFactory.getLogger(Actuator.class);

    public Actuator(Context context) {
        this.context = context;
        classes = new ArrayList<>();
    }

    /**
     * 执行目标字节码
     *
     * @throws ExecuteException
     */
    public void execute() throws ExecuteException {
        if (!context.isExecute()) {
            return;
        }
        var outPath = context.getOut();
        getClasses(new File(outPath));//获取中间代码路径
        var classLoader = new ClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                var path = String.format("%s%s%s%s", outPath,
                        Constants.SEPARATE, name, Constants.TARGET_CODE_FILE_POSTFIX);
                try (var in = new BufferedInputStream(new FileInputStream(path))) {
                    byte[] value = new byte[in.available()];
                    in.read(value);
                    return defineClass(name, value, 0, value.length);
                } catch (Throwable e) {
                    throw new ClassNotFoundException(String.format("%s文件加载失败", path));
                }
            }
        };
        for (String cls : classes) {
            log.info("正在执行{}", cls);
            try {
                classLoader.loadClass(cls).
                        getDeclaredMethod("main", String[].class).
                        invoke(null, (Object) new String[]{});
            } catch (Throwable e) {
                throw new ExecuteException(String.format("%s执行失败!", cls), e);
            }
        }
    }

    /**
     * 加载目标目录下的所有.class文件
     *
     * @param file
     */
    private void getClasses(File file) {
        Objects.requireNonNull(file);
        var files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                if (f.getName().endsWith(Constants.TARGET_CODE_FILE_POSTFIX)) {
                    classes.add(f.getName().split("\\.")[0]);
                }
            } else {
                getClasses(f);
            }
        }
    }
}
