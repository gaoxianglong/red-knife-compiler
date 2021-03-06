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

import com.github.redknife.tools.compiler.core.parser.Generate;
import com.github.redknife.tools.compiler.core.parser.Parser;
import com.github.redknife.tools.compiler.core.parser.RedKnifeParser;
import com.github.redknife.tools.compiler.core.tree.*;
import com.github.redknife.tools.compiler.exceptions.ExecuteException;
import com.github.redknife.tools.compiler.exceptions.ParseException;
import com.github.redknife.tools.compiler.utils.Constants;
import com.github.redknife.tools.compiler.utils.Context;
import com.github.redknife.tools.compiler.utils.RedKnifeFileManager;
import javassist.ClassPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 编译器核心控制器
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/13 4:59 下午
 */
public class RedKnifeCompiler {
    private ClassPool classPool;
    private Parser parser;
    private Context context;
    private Generate generate;
    private Logger log = LoggerFactory.getLogger(RedKnifeCompiler.class);

    public RedKnifeCompiler(Context context) {
        this.context = context;
        this.classPool = ClassPool.getDefault();
        this.generate = new Generate(context);
        this.parser = new RedKnifeParser();
    }

    public void compile(List<File> files) throws Throwable {
        var begin = System.currentTimeMillis();
        Objects.requireNonNull(files);
        var trees = compile01(files);//执行词法、语法解析
        if (trees.isEmpty()) {
            throw new ParseException("AST语法树无法生成");
        }
        printASTTree(trees);//打印语法树
        compile02(trees);//转义为Java代码后再进行语义分析和生成中间代码
        log.info("编译结束，耗时: {}ms", System.currentTimeMillis() - begin);
        try {
            new Actuator(context).execute();//调用执行器执行
        } catch (ExecuteException e) {
            log.error("{}", e);//执行失败异常单独处理
        }
    }

    /**
     * 语义分析、中间代码生成
     *
     * @param trees
     * @throws Throwable
     */
    private void compile02(List<Tree> trees) throws Throwable {
        generate.compile(trees);
    }

    /**
     * 打印语法树
     *
     * @param trees
     * @throws Throwable
     */
    private void printASTTree(List<Tree> trees) throws Throwable {
        if (!context.isDebug()) {
            return;
        }
        var visitor = new TreeScanner();
        log.info("AST:");
        for (Tree t : trees) {
            visitor.visit(t, "");
        }
    }

    /**
     * 执行词法、语法解析
     *
     * @param files
     * @return
     * @throws Throwable
     */
    private List<Tree> compile01(List<File> files) throws Throwable {
        var result = new ArrayList<Tree>();
        for (File file : files) {
            var sourceCode = readSource(file);
            if (context.isDebug()) {
                log.info("\nsource code:\n{}:\n{}\n", file.getName(), sourceCode);
            }
            var tree = parser.parse(sourceCode, file.getName().split("\\.")[0]);//开始执行语法解析
            if (Objects.nonNull(tree)) {
                result.add(tree);
            }
        }
        return result;
    }

    /**
     * 获取源码内容
     *
     * @param file
     * @return
     * @throws Throwable
     */
    private CharBuffer readSource(File file) throws Throwable {
        return RedKnifeFileManager.getCharContent(file);
    }
}