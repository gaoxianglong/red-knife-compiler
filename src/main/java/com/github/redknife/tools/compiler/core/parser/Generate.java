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
package com.github.redknife.tools.compiler.core.parser;

import com.github.redknife.tools.compiler.core.tree.*;
import com.github.redknife.tools.compiler.utils.Context;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * 生成字节码
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/26 4:43 下午
 */
public class Generate implements Visitor {
    private CtClass ctClass;
    private Context context;
    private ClassPool classPool;
    private StringBuffer sbuf;
    private Logger log = LoggerFactory.getLogger(Generate.class);

    public Generate(Context context) {
        this.context = context;
        sbuf = new StringBuffer();
        classPool = ClassPool.getDefault();
    }

    /**
     * 将目标代码转换为java代码，然后再进行语义分析和中间代码生成
     *
     * @param trees
     * @throws Throwable
     */
    public void compile(List<Tree> trees) throws Throwable {
        for (Tree t : trees) {
            visit(t, null);
            ctClass.writeFile(context.getOut());
        }
    }

    @Override
    public void visit(Tree tree, String tab) throws Throwable {
        Objects.requireNonNull(tree);
        for (Tree t : tree.getChilds()) {
            t.accept(this, tab);
            visit(t, tab);
            if (t.getTag() == Tree.Tag.CLASSDEF) {
                if (context.isDebug()) {
                    log.info(sbuf.toString());
                }
                generate();
            }
        }
    }

    @Override
    public void visitClassDecl(ClassDecl classDecl, String tab) throws Throwable {
        ctClass = classPool.makeClass(classDecl.getName());
    }

    @Override
    public void visitForDecl(ForDecl forDecl, String tab) throws Throwable {
        sbuf.append("for");
    }

    @Override
    public void visitIf(If if_, String tab) throws Throwable {
        switch (if_.getTag()) {
            case IF:
                sbuf.append("if");
                break;
            case ELSEIF:
                sbuf.append("else if");
                break;
            case ELSE:
                sbuf.append("else");
        }
    }

    @Override
    public void visitLiteral(Literal literal, String tab) throws Throwable {
        sbuf.append(literal.getName());
    }

    @Override
    public void visitMethodDecl(MethodDecl methodDecl, String tab) throws Throwable {
        sbuf.append("public static void main(String[] args)");
    }

    /**
     * 生成字节码
     *
     * @throws Throwable
     */
    private void generate() throws Throwable {
        try {
            var src = sbuf.toString();
            if (Objects.nonNull(src) && !src.isEmpty()) {
                var method = CtMethod.make("public static void println(String value){" +
                        "System.out.println(value);" +
                        "}", ctClass);
                ctClass.addMethod(method);
                method = CtMethod.make("public static void println(int value){" +
                        "System.out.println(value);" +
                        "}", ctClass);
                ctClass.addMethod(method);
                method = CtMethod.make("public static void println(boolean value){" +
                        "System.out.println(value);" +
                        "}", ctClass);
                ctClass.addMethod(method);
                method = CtMethod.make("public static void print(String value){" +
                        "System.out.print(value);" +
                        "}", ctClass);
                ctClass.addMethod(method);
                method = CtMethod.make("public static void print(int value){" +
                        "System.out.print(value);" +
                        "}", ctClass);
                ctClass.addMethod(method);
                method = CtMethod.make("public static void print(boolean value){" +
                        "System.out.print(value);" +
                        "}", ctClass);
                ctClass.addMethod(method);
                method = CtMethod.make(sbuf.toString(), ctClass);
                ctClass.addMethod(method);
            }
        } finally {
            sbuf.delete(0, sbuf.length());
        }
    }

    @Override
    public void visitVariableDecl(VariableDecl variableDecl, String tab) throws Throwable {
        var type = variableDecl.getTypeTag();
        if (Objects.nonNull(type)) {
            String name = null;
            switch (type) {
                case BOOL:
                    name = "boolean";
                    break;
                case CHARS:
                    name = "java.lang.String";
                    break;
                default:
                    name = type.name;
            }
            sbuf.append(String.format("%s ", name));
        }
        sbuf.append(variableDecl.getName());
    }

    @Override
    public void visitIdent(Ident ident, String tab) throws Throwable {
        sbuf.append(ident.getName());
    }

    @Override
    public void visitOther(Other other, String tab) throws Throwable {
        var name = other.getName();
        if (Objects.nonNull(name)) {
            sbuf.append(name);
        }
    }
}