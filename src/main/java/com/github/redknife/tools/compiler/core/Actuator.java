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

import com.github.redknife.tools.compiler.utils.Constants;
import com.github.redknife.tools.compiler.utils.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
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
    private List<String> paths, names;
    private Context context;
    private Logger log = LoggerFactory.getLogger(Actuator.class);

    public Actuator(Context context) {
        this.context = context;
        paths = new ArrayList<>();
        names = new ArrayList<>();
    }

    /**
     * 编译结束后进行执行
     */
    public void execute() {
        if (!context.isExecute()) {
            return;
        }
        try {
            getClasses(new File(context.getOut()));//获取中间代码路径
            for (int i = 0; i < names.size(); i++) {
                log.info("执行{}", names.get(i));
                int index = i;
                var classLoader = new ClassLoader() {
                    @Override
                    protected Class<?> findClass(String name) throws ClassNotFoundException {
                        try (var in = new BufferedInputStream(new FileInputStream(String.format("%s%s%s",
                                paths.get(index), Constants.SEPARATE, names.get(index))))) {
                            byte[] value = new byte[in.available()];
                            in.read(value);
                            return defineClass(name, value, 0, value.length);
                        } catch (Throwable e) {
                            log.error("执行失败:\n", e);
                        }
                        return null;
                    }
                };
                var class_ = classLoader.loadClass(names.get(i).split("\\.")[0]);
                var method = class_.getDeclaredMethod("main", String[].class);
                method.invoke(class_, (Object) new String[]{});
            }
        } catch (Throwable e) {
            log.error("执行失败:\n", e);
        }
    }

    /**
     * 加载目标中间代码的路径
     *
     * @param file
     * @throws Throwable
     */
    private void getClasses(File file) throws Throwable {
        Objects.requireNonNull(file);
        var files = file.listFiles();
        for (File f : files) {
            if (f.isFile()) {
                if (f.getName().endsWith(Constants.TARGET_CODE_FILE_POSTFIX)) {
                    paths.add(f.getParent());
                    names.add(f.getName());
                }
            } else {
                getClasses(f);
            }
        }
    }
}
