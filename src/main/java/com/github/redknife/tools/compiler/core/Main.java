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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 编译入口类
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/13 10:58 上午
 */
public class Main {
    private Context context;
    private List<File> sourceFiles;
    private RedKnifeCompiler compiler;
    private Logger log = LoggerFactory.getLogger(Main.class);

    public Main(Context context) {
        this.context = context;
        sourceFiles = new ArrayList<>();
        compiler = new RedKnifeCompiler(context);
    }

    /**
     * 编译入口
     *
     * @throws Throwable
     */
    public void compile() throws Throwable {
        getSourceFiles(new File(context.getIn()));
        log.debug(sourceFiles.toString());
        if (!sourceFiles.isEmpty()) {
            compiler.compile(sourceFiles);
        } else {
            log.warn("目录: {}下没有加载到任何源文件", context.getIn());
        }
    }

    /**
     * 获取目标目录下所有源文件的绝对路径
     *
     * @param file
     */
    private void getSourceFiles(File file) {
        var files = file.listFiles();
        Stream.of(files).forEach(x -> {
            if (x.isFile()) {
                if (x.getName().endsWith(Constants.SOURCE_CODE_FILE_POSTFIX)) {//只加载指定后缀的文件
                    sourceFiles.add(x);
                }
            } else {
                getSourceFiles(x);
            }
        });
    }
}
