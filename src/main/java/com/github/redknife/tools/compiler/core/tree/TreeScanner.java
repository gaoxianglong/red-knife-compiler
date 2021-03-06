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
package com.github.redknife.tools.compiler.core.tree;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * 访问者实现，打印语法树
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/15 3:37 下午
 */
public class TreeScanner implements Visitor {
    private Logger log = LoggerFactory.getLogger("");

    @Override
    public void visit(Tree tree, String tab) throws Throwable {
        Objects.requireNonNull(tree);
        for (Tree t : tree.getChilds()) {
            t.accept(this, tab);
            visit(t, String.format("%s%s", "\t", tab));
        }
    }

    @Override
    public void visitBlock(Block block, String tab) throws Throwable {
        log.info("{}class:{}", tab, block.getClass().getSimpleName());
    }

    @Override
    public void visitClassDecl(ClassDecl classDecl, String tab) throws Throwable {
        log.info("{}class:{}\ttag:{}\tname:{}", tab, classDecl.getClass().getSimpleName(),
                classDecl.getTag(), classDecl.getName());
    }

    @Override
    public void visitExpression(Expression expression, String tab) throws Throwable {
        log.info("{}class:{}\ttag:{}\tname:{}", tab, expression.getClass().getSimpleName(),
                expression.getTag(), expression.getName());
    }

    @Override
    public void visitExpressionStatement(ExpressionStatement expressionStatement, String tab) throws Throwable {
        var tag = expressionStatement.getTag();
        var name = expressionStatement.getName();
        log.info("{}class:{}\t{}\t{}", tab, expressionStatement.getClass().getSimpleName(),
                tag != Tree.Tag.NO_TAG ? String.format("tag:%s", tag) : "",
                Objects.nonNull(name) ? String.format("name:%s", name) : "");
    }

    @Override
    public void visitForDecl(ForDecl forDecl, String tab) throws Throwable {
        log.info("{}class:{}\ttag:{}", tab, forDecl.getClass().getSimpleName(), forDecl.getTag());
    }

    @Override
    public void visitIf(If if_, String tab) throws Throwable {
        log.info("{}class:{}\ttag:{}", tab, if_.getClass().getSimpleName(), if_.getTag());
    }

    @Override
    public void visitLiteral(Literal literal, String tab) throws Throwable {
        log.info("{}class:{}\ttypeTag:{}\tname:{}", tab, literal.getClass().getSimpleName(),
                literal.getTypeTag(), literal.getName());
    }

    @Override
    public void visitMethodDecl(MethodDecl methodDecl, String tab) throws Throwable {
        log.info("{}class:{}\ttag:{}\ttypeTag:{}\tname:{}", tab, methodDecl.getClass().getSimpleName(),
                methodDecl.getTag(), methodDecl.getTypeTag(), methodDecl.getName());
    }

    @Override
    public void visitParens(Parens parens, String tab) throws Throwable {
        log.info("{}class:{}\ttag:{}\tname:{}", tab, parens.getClass().getSimpleName(),
                parens.getTag(), parens.getName());
    }

    @Override
    public void visitStatement(Statement statement, String tab) throws Throwable {
        log.info("{}class:{}\ttag:{}\tname:{}", tab, statement.getClass().getSimpleName(),
                statement.getTag(), statement.getName());
    }

    @Override
    public void visitVariableDecl(VariableDecl variableDecl, String tab) throws Throwable {
        var type = variableDecl.getTypeTag();
        if (Objects.nonNull(type)) {
            log.info("{}class:{}\ttag:{}\ttypeTag:{}\tname:{}", tab, variableDecl.getClass().getSimpleName(),
                    variableDecl.getTag(), type, variableDecl.getName());
        } else {
            log.info("{}class:{}\ttag:{}\tname:{}", tab, variableDecl.getClass().getSimpleName(),
                    variableDecl.getTag(), variableDecl.getName());
        }
    }

    @Override
    public void visitBinary(Binary binary, String tab) throws Throwable {
        log.info("{}class:{}\ttag:{}\tname:{}", tab, binary.getClass().getSimpleName(),
                binary.getTag(), binary.getName());
    }

    @Override
    public void visitIdent(Ident ident, String tab) throws Throwable {
        log.info("{}class:{}\tname:{}", tab, ident.getClass().getSimpleName(), ident.getName());
    }

    @Override
    public void visitFieldAccess(FieldAccess fieldAccess, String tab) throws Throwable {
        log.info("{}class:{}", tab, fieldAccess.getClass().getSimpleName());
    }

//    @Override
//    public void visitOther(Other other, String tab) throws Throwable {
//        log.info("{}class:{}\tname:{}", tab, other.getClass().getSimpleName(), other.getName());
//    }
}