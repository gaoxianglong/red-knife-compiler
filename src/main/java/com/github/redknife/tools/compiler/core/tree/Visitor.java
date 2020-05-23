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

/**
 * 抽象访问者接口
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/15 2:17 下午
 */
public interface Visitor {
    void visit(Tree tree, String tab);

    void visitBlock(Block block, String tab);

    void visitClassDecl(ClassDecl classDecl, String tab);

    void visitExpression(Expression expression, String tab);

    void visitExpressionStatement(ExpressionStatement expressionStatement, String tab);

    void visitForDecl(ForDecl forDecl, String tab);

    void visitIf(If if_, String tab);

    void visitLiteral(Literal literal, String tab);

    void visitMethodDecl(MethodDecl methodDecl, String tab);

    void visitParens(Parens parens, String tab);

    void visitStatement(Statement statement, String tab);

    void visitVariableDecl(VariableDecl variableDecl, String tab);

    void visitBinary(Binary binary, String tab);

    void visitIdent(Ident ident, String tab);

    void visitFieldAccess(FieldAccess fieldAccess, String tab);
}
