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

import com.github.redknife.tools.compiler.core.lexer.Token;
import com.github.redknife.tools.compiler.core.lexer.TokenReader;
import com.github.redknife.tools.compiler.core.tree.*;
import com.github.redknife.tools.compiler.exceptions.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.CharBuffer;
import java.util.Objects;

/**
 * red-knife-compiler语法分析器
 * <p>
 * 上下文无关文法如下:
 * <p>
 * parse -> block
 * ifDecl -> 'if' '(' expression ')' block ('else' 'if' '(' expression ')' block)* else block
 * forDecl -> 'for' '(' variableDecl expression ';' expressionStatement')' block
 * expression -> primary | primary ('<' | '>' | '<=' | '>=' | '!=' | '==') primary
 * block -> '{' variableDecl expressionStatement';' forDecl ifDecl '}'
 * <p>
 * variableDecl -> intDecl | charsDecl | booleanDecl | assignmentStatement
 * assignmentStatement -> id '=' additive ';'
 * expressionStatement -> primary(++ | --) | primary ('.'primary)* '('primary')'
 * intDecl -> 'int' id '=' additive ';'
 * charsDecl -> 'chars' id '=' additive ';'
 * boolDecl -> 'bool' id '=' additive ';'
 * additive -> multiplicative ((+ | -) multiplicative)*
 * multiplicative -> primary ((* | /) primary)*
 * primary -> 0-9 | id | (additive) | true | false
 * id -> identifier
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/14 3:15 下午
 */
public class RedKnifeParser implements Parser {
    /**
     * 当前Token
     */
    private Token token;
    /**
     * 上一个Token
     */
    private Token prevToken;
    private TokenReader reader;
    private Logger log = LoggerFactory.getLogger(RedKnifeParser.class);

    /**
     * 初始化词法分析器
     *
     * @param cb
     */
    private void init(CharBuffer cb) {
        reader = new TokenReader().init(cb);
    }

    @Override
    public Tree parse(CharBuffer cb, String className) throws Throwable {
        Objects.requireNonNull(cb);
        Objects.requireNonNull(className);
        if (Objects.isNull(reader)) {
            init(cb);
            log.info("red-knife-compiler相关初始化完成...");
        } else {
            reader.reset(cb);//重设词法分析器相关属性值
        }
        var compilationUnit = new CompilationUnit(Tree.Tag.NO_TAG);
        var classDecl = new ClassDecl(Tree.Tag.CLASSDEF, className);
        var methodDecl = new MethodDecl(Tree.Tag.METHODDEF, TypeTag.VOID, "main");
        var child = block(0);
        if (Objects.nonNull(child)) {
            methodDecl.getChilds().add(child);
        }
        classDecl.getChilds().add(methodDecl);
        compilationUnit.getChilds().add(classDecl);
        return compilationUnit;
    }

    /**
     * 解析字符串变量声明语句
     * charsDecl -> 'chars' id '=' additive ';'
     *
     * @return
     * @throws Throwable
     */
    private VariableDecl charsDecl() throws Throwable {
        nextToken();
        if (Objects.isNull(token)) {
            return null;
        }
        var begin = token.pos;//记录错误开始位置
        if (isTokenKind(Token.TokenKind.CHARS)) {
            nextToken();
            if (isTokenKind(Token.TokenKind.IDENTIFIER)) {
                var result = new VariableDecl(Tree.Tag.VARDEF, getMorpheme());
                result.setTypeTag(TypeTag.CHARS);
                nextToken();
                if (isTokenKind(Token.TokenKind.EQ)) {
                    result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                    var child = additive(begin);
                    if (Objects.nonNull(child)) {
                        result.getChilds().add(child);
                        nextToken();
                        if (isTokenKind(Token.TokenKind.SEMI)) {
                            result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                            return result;
                        } else {
                            error(begin);
                        }
                    } else {
                        error(begin);
                    }
                } else if (isTokenKind(Token.TokenKind.SEMI)) {
                    result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                    return result;
                } else {
                    error(begin);
                }
            } else {
                error(begin);
            }
        } else {
            prevToken();
        }
        return null;
    }

    /**
     * 解析布尔变量声明语句
     * boolDecl -> 'bool' id '=' additive ';'
     *
     * @return
     * @throws Throwable
     */
    private VariableDecl boolDecl() throws Throwable {
        nextToken();
        if (Objects.isNull(token)) {
            return null;
        }
        var begin = token.pos;
        if (isTokenKind(Token.TokenKind.BOOL)) {
            nextToken();
            if (isTokenKind(Token.TokenKind.IDENTIFIER)) {
                var result = new VariableDecl(Tree.Tag.VARDEF, getMorpheme());
                result.setTypeTag(TypeTag.BOOL);
                nextToken();
                if (isTokenKind(Token.TokenKind.EQ)) {
                    result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                    var child = additive(begin);
                    if (Objects.nonNull(child)) {
                        result.getChilds().add(child);
                        nextToken();
                        if (isTokenKind(Token.TokenKind.SEMI)) {
                            result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                            return result;
                        }
                    } else {
                        error(begin);
                    }
                } else if (isTokenKind(Token.TokenKind.SEMI)) {
                    result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                    return result;
                } else {
                    error(begin);
                }
            } else {
                error(begin);
            }
        } else {
            prevToken();
        }
        return null;
    }

    /**
     * 解析int类型的变量声明
     * intDecl -> 'int' id '=' additive ';'
     *
     * @return
     * @throws Throwable
     */
    private VariableDecl intDecl() throws Throwable {
        nextToken();
        if (Objects.isNull(token)) {
            return null;
        }
        var begin = token.pos;
        if (isTokenKind(Token.TokenKind.INT)) {
            nextToken();
            if (isTokenKind(Token.TokenKind.IDENTIFIER)) {
                var result = new VariableDecl(Tree.Tag.VARDEF, getMorpheme());
                result.setTypeTag(TypeTag.INT);
                nextToken();
                if (isTokenKind(Token.TokenKind.EQ)) {
                    result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                    var child = additive(begin);//解析二元表达式
                    if (Objects.nonNull(child)) {
                        result.getChilds().add(child);
                        nextToken();
                        if (isTokenKind(Token.TokenKind.SEMI)) {
                            result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                            return result;
                        } else {
                            error(begin);
                        }
                    } else {
                        error(begin);
                    }
                } else if (isTokenKind(Token.TokenKind.SEMI)) {//成员变量允许仅声明
                    result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                    return result;
                } else {
                    error(begin);
                }
            } else {
                error(begin);
            }
        } else {
            prevToken();
        }
        return null;
    }

    /**
     * 解析if流程控制语句声明
     * ifDecl -> 'if' '(' expression ')' block ('else' 'if' '(' expression ')' block)* else block
     *
     * @return
     * @throws Throwable
     */
    private Tree ifDecl() throws Throwable {
        nextToken();
        if (Objects.isNull(token)) {
            return null;
        }
        var begin = token.pos;
        if (isTokenKind(Token.TokenKind.IF)) {
            var result = new If(Tree.Tag.IF);
            nextToken();
            if (isTokenKind(Token.TokenKind.LPAREN)) {
                result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                var child = expression(begin);
                if (Objects.nonNull(child)) {
                    result.getChilds().add(child);//添加表达式
                    nextToken();
                    if (isTokenKind(Token.TokenKind.RPAREN)) {
                        result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                        child = block(begin);
                        if (Objects.nonNull(child) && !child.getChilds().isEmpty()) {
                            result.getChilds().add(child);//添加block语句
                        }
                        while (true) {//循环解析else-if语句
                            var temp = nextToken();
                            if (Objects.isNull(temp)) {
                                error(begin);
                            } else if (isTokenKind(Token.TokenKind.ELSE)) {
                                nextToken();
                                if (isTokenKind(Token.TokenKind.IF)) {
                                    var else_if = new If(Tree.Tag.ELSEIF);
                                    nextToken();
                                    if (isTokenKind(Token.TokenKind.LPAREN)) {
                                        else_if.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                                        child = expression(begin);
                                        if (Objects.nonNull(child)) {
                                            else_if.getChilds().add(child);
                                        } else {
                                            error(begin);
                                        }
                                        nextToken();
                                        if (isTokenKind(Token.TokenKind.RPAREN)) {
                                            else_if.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                                            child = block(begin);
                                            if (Objects.nonNull(child)) {
                                                else_if.getChilds().add(child);
                                                result.getChilds().add(else_if);
                                            }
                                        } else {
                                            error(begin);
                                        }
                                    } else {
                                        error(begin);
                                    }
                                } else {
                                    prevToken(temp.pos);
                                    break;
                                }
                            } else {
                                prevToken();
                                break;
                            }
                        }
                        nextToken();
                        if (Objects.isNull(token)) {
                            error(begin);
                        } else if (isTokenKind(Token.TokenKind.ELSE)) {
                            var else_ = new If(Tree.Tag.ELSE);
                            child = block(begin);
                            if (Objects.nonNull(child)) {
                                else_.getChilds().add(child);
                            }
                            result.getChilds().add(else_);
                        } else {
                            prevToken();
                        }
                        return result;
                    } else {
                        error(begin);
                    }
                } else {
                    error(begin);
                }
            }
        } else {
            prevToken();
        }
        return null;
    }

    /**
     * 解析for循环控制语句声明
     * forDecl -> 'for' '(' variableDecl expression ';' expressionStatement')' block
     *
     * @return
     * @throws Throwable
     */
    private Tree forDecl() throws Throwable {
        nextToken();
        if (Objects.isNull(token)) {
            return null;
        }
        var begin = token.pos;
        if (isTokenKind(Token.TokenKind.FOR)) {
            var result = new ForDecl(Tree.Tag.FORLOOP);
            nextToken();
            if (isTokenKind(Token.TokenKind.LPAREN)) {
                result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                Tree child = variableDecl();
                if (Objects.nonNull(child)) {
                    result.getChilds().add(child);
                    child = expression(begin);
                    if (Objects.nonNull(child)) {
                        result.getChilds().add(child);
                        nextToken();
                        if (isTokenKind(Token.TokenKind.SEMI)) {
                            result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                            child = expressionStatement(begin);
                            if (Objects.nonNull(child)) {
                                result.getChilds().add(child);
                                nextToken();
                                if (isTokenKind(Token.TokenKind.RPAREN)) {
                                    result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                                    child = block(begin);
                                    if (Objects.nonNull(child)) {
                                        result.getChilds().add(child);
                                    }
                                } else {
                                    error(begin);
                                }
                                return result;
                            } else {
                                error(begin);
                            }
                        } else {
                            error(begin);
                        }
                    }
                } else {
                    error(begin);
                }
            }
        } else {
            prevToken();
        }
        return null;
    }

    /**
     * 解析表达式
     * expression -> primary | primary ('<' | '>' | '<=' | '>=' | '!=' | '==') primary
     *
     * @param begin
     * @return
     * @throws Throwable
     */
    private Tree expression(int begin) throws Throwable {
        var child = primary(begin);
        if (Objects.isNull(child)) {
            error(begin);
        }
        nextToken();
        var morpheme = getMorpheme();
        Expression result = null;
        if (isTokenKind(Token.TokenKind.LT)) {
            result = new Expression(Tree.Tag.LT, morpheme);
        } else if (isTokenKind(Token.TokenKind.GT)) {
            result = new Expression(Tree.Tag.GT, morpheme);
        } else if (isTokenKind(Token.TokenKind.LTEQ)) {
            result = new Expression(Tree.Tag.LE, morpheme);
        } else if (isTokenKind(Token.TokenKind.GTEQ)) {
            result = new Expression(Tree.Tag.GE, morpheme);
        } else if (isTokenKind(Token.TokenKind.BANGEQ)) {
            result = new Expression(Tree.Tag.NE, morpheme);
        } else if (isTokenKind(Token.TokenKind.EQEQ)) {
            result = new Expression(Tree.Tag.EQ, morpheme);
        } else {
            prevToken();
            return child;
        }
        result.getChilds().add(child);
        result.getChilds().add(new Other(Tree.Tag.NO_TAG, result.getName()));
        child = primary(begin);
        if (Objects.isNull(child)) {
            error(begin);
        }
        result.getChilds().add(child);
        return result;
    }

    /**
     * 解析代码块
     * block -> '{' variableDecl expressionStatement';' forDecl ifDecl '}'
     *
     * @param begin
     * @return
     */
    private Tree block(int begin) throws Throwable {
        nextToken();
        if (Objects.isNull(token)) {
            return null;
        }
        if (isTokenKind(Token.TokenKind.LBRACE)) {
            var result = new Block(Tree.Tag.NO_TAG);
            result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
            while (true) {
                Tree c1 = null;
                Tree c2 = null;
                Tree c3 = null;
                Tree c4 = null;
                if (Objects.nonNull(c1 = variableDecl())) {//解析字段声明
                    result.getChilds().add(c1);
                } else if (Objects.nonNull(c2 = expressionStatement(begin))) {//解析表达式语句
                    nextToken();
                    if (isTokenKind(Token.TokenKind.SEMI)) {
                        result.getChilds().add(c2);
                        result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                    } else {
                        error(begin);
                    }
                } else if (Objects.nonNull(c3 = forDecl())) {//解析for循环语句声明
                    result.getChilds().add(c3);
                } else if (Objects.nonNull(c4 = ifDecl())) {//解析if流程控制语句
                    result.getChilds().add(c4);
                }
                nextToken();
                if (Objects.isNull(token)) {
                    error(begin);
                } else if (isTokenKind(Token.TokenKind.RBRACE)) {//允许方法体为空
                    result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                    return result;
                } else {
                    if (Objects.isNull(c1) && Objects.isNull(c2) &&
                            Objects.isNull(c3) && Objects.isNull(c4)) {
                        error(begin);//不满足语法规范时抛出异常
                    }
                    prevToken();
                }
            }
        } else {
            error(begin);
        }
        return null;
    }

    /**
     * 解析变量声明
     * variableDecl -> intDecl | charsDecl | booleanDecl | assignmentStatement
     *
     * @return
     * @throws Throwable
     */
    private VariableDecl variableDecl() throws Throwable {
        var result = intDecl();
        if (Objects.isNull(result)) {
            result = charsDecl();
        }
        if (Objects.isNull(result)) {
            result = boolDecl();
        }
        if (Objects.isNull(result)) {
            result = assignmentStatement();
        }
        return result;
    }

    /**
     * 解析整数赋值语句
     * assignmentStatement -> id '=' additive ';'
     *
     * @return
     * @throws Throwable
     */
    private VariableDecl assignmentStatement() throws Throwable {
        var temp = nextToken();
        if (Objects.isNull(token)) {
            return null;
        }
        int begin = token.pos;
        if (isTokenKind(Token.TokenKind.IDENTIFIER)) {
            var result = new VariableDecl(Tree.Tag.VARDEF, getMorpheme());
            nextToken();
            if (isTokenKind(Token.TokenKind.EQ)) {
                result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                var child = additive(begin);
                if (Objects.isNull(child)) {
                    error(begin);
                }
                result.getChilds().add(child);
                nextToken();
                if (isTokenKind(Token.TokenKind.SEMI)) {
                    result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                    return result;
                } else {
                    error(begin);
                }
            } else {
                prevToken(temp.pos);
            }
        } else {
            prevToken();
        }
        return null;
    }

    /**
     * 解析加减二元运算
     * additive -> multiplicative ((+ | -) multiplicative)*
     *
     * @param begin
     * @return
     * @throws Throwable
     */
    private Tree additive(int begin) throws Throwable {
        Tree left = multiplicative(begin);
        Tree result = left;
        while (true) {
            nextToken();
            if (Objects.isNull(token)) {
                break;
            }
            if (isTokenKind(Token.TokenKind.PLUS) || isTokenKind(Token.TokenKind.SUB)) {
                var morpheme = getMorpheme();
                Tree.Tag tag = isTokenKind(Token.TokenKind.PLUS) ? Tree.Tag.PLUS : Tree.Tag.SUB;
                var right = multiplicative(begin);
                if (Objects.isNull(right)) {
                    error(begin);
                }
                result = new Binary(tag, morpheme);
                result.getChilds().add(left);
                result.getChilds().add(new Other(Tree.Tag.NO_TAG, morpheme));
                result.getChilds().add(right);
                left = result;
            } else {
                prevToken();
                break;
            }
        }
        return result;
    }

    /**
     * 解析乘除法二元运算
     * multiplicative -> primary ((* | /) primary)*
     *
     * @param begin
     * @return
     * @throws Throwable
     */
    private Tree multiplicative(int begin) throws Throwable {
        Tree left = primary(begin);
        if (Objects.isNull(left)) {
            return null;
        }
        Tree result = left;
        while (true) {
            nextToken();
            if (Objects.isNull(token)) {
                break;
            }
            if (isTokenKind(Token.TokenKind.STAR) || isTokenKind(Token.TokenKind.SLASH)) {
                var morpheme = getMorpheme();
                Tree.Tag tag = isTokenKind(Token.TokenKind.STAR) ? Tree.Tag.STAR : Tree.Tag.SLASH;
                var right = primary(begin);
                if (Objects.isNull(right)) {
                    error(begin);
                }
                result = new Binary(tag, morpheme);
                result.getChilds().add(left);
                result.getChilds().add(new Other(Tree.Tag.NO_TAG, morpheme));
                result.getChilds().add(right);
                left = result;
            } else {
                prevToken();
                break;
            }
        }
        return result;
    }

    /**
     * 解析为非终结符
     * primary -> 0-9 | id | (additive) | true | false
     *
     * @param begin
     * @return
     * @throws Throwable
     */
    private Tree primary(int begin) throws Throwable {
        nextToken();
        if (Objects.isNull(token)) {
            return null;
        } else if (isTokenKind(Token.TokenKind.IDENTIFIER)) {
            return new Ident(Tree.Tag.NO_TAG, getMorpheme());
        } else if (isTokenKind(Token.TokenKind.STRINGLITERAL)) {
            return new Literal(Tree.Tag.NO_TAG, TypeTag.CHARS, getMorpheme());
        } else if (isTokenKind(Token.TokenKind.INTLITERAL)) {
            return new Literal(Tree.Tag.NO_TAG, TypeTag.INT, getMorpheme());
        } else if (isTokenKind(Token.TokenKind.TRUE) || isTokenKind(Token.TokenKind.FALSE)) {
            return new Literal(Tree.Tag.NO_TAG, TypeTag.BOOL, getMorpheme());
        } else if (isTokenKind(Token.TokenKind.LPAREN)) {
            var result = new Other(Tree.Tag.NO_TAG);
            result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
            var child = additive(begin);
            if (Objects.nonNull(child)) {
                result.getChilds().add(child);
                nextToken();
                if (isTokenKind(Token.TokenKind.RPAREN)) {
                    result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                    return result;
                } else {
                    error(begin);
                }
            }
        } else {
            prevToken();
        }
        return null;
    }

    /**
     * 解析表达式语句
     * expressionStatement -> primary(++ | --) | primary ('.'primary)* '(' primary ')'
     * @param begin
     * @return
     * @throws Throwable
     */
    private Tree expressionStatement(int begin) throws Throwable {
        var child = primary(begin);
        if (Objects.nonNull(child)) {
            nextToken();
            if (isTokenKind(Token.TokenKind.PLUSPLUS) || isTokenKind(Token.TokenKind.SUBSUB)) {
                var morpheme = getMorpheme();
                Tree.Tag tag = isTokenKind(Token.TokenKind.PLUSPLUS) ? Tree.Tag.POSTINC : Tree.Tag.POSTDEC;
                var result = new ExpressionStatement(tag, morpheme);
                result.getChilds().add(child);
                result.getChilds().add(new Other(Tree.Tag.NO_TAG, morpheme));
                return result;
            } else if (isTokenKind(Token.TokenKind.LPAREN)) {
                var result = new ExpressionStatement(Tree.Tag.NO_TAG);
                var temp = new FieldAccess(Tree.Tag.NO_TAG);
                temp.getChilds().add(child);
                temp.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                result.getChilds().add(temp);
                child = primary(begin);
                if (Objects.isNull(child)) {
                    error(begin);
                }
                result.getChilds().add(child);//添加方法入参
                nextToken();
                if (isTokenKind(Token.TokenKind.RPAREN)) {
                    result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                    return result;
                } else {
                    error(begin);
                }
            } else if (isTokenKind(Token.TokenKind.DOT)) {
                prevToken();
                Tree prev = null;
                Tree first = null;
                while (true) {
                    var temp = new FieldAccess(Tree.Tag.NO_TAG);
                    temp.getChilds().add(child);
                    if (Objects.nonNull(prev)) {
                        prev.getChilds().add(temp);
                    }
                    prev = temp;
                    if (Objects.isNull(first)) {
                        first = temp;
                    }
                    nextToken();
                    if (isTokenKind(Token.TokenKind.DOT)) {
                        temp.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                        child = primary(begin);
                    } else {
                        if (isTokenKind(Token.TokenKind.LPAREN)) {
                            temp.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                            child = primary(begin);
                            var result = new ExpressionStatement(Tree.Tag.NO_TAG);
                            result.getChilds().add(first);
                            if (Objects.nonNull(child)) {
                                result.getChilds().add(child);//添加方法入参
                            }
                            nextToken();
                            if (isTokenKind(Token.TokenKind.RPAREN)) {
                                result.getChilds().add(new Other(Tree.Tag.NO_TAG, getMorpheme()));
                                return result;
                            } else {
                                error(begin);
                            }
                        } else {
                            error(begin);
                        }
                    }
                }
            } else {
                error(begin);
            }
        }
        return null;
    }

    /**
     * 从源文件中获取下一个Token
     *
     * @throws Throwable
     */
    private Token nextToken() throws Throwable {
        Objects.requireNonNull(reader);
        if (Objects.nonNull(token)) {
            prevToken = token;
        }
        token = reader.nextToken();
        return token;
    }

    /**
     * 获取Token类型
     *
     * @return
     */
    private Token.TokenKind getTokenKind() {
        if (Objects.isNull(token)) {
            token = prevToken;
        }
        return token.tokenKind;
    }

    /**
     * 获取词素
     *
     * @return
     */
    private String getMorpheme() {
        return Objects.nonNull(token) ? new String(token.attribute.morpheme) : null;
    }

    /**
     * TokenKind类型比对
     *
     * @param tokenKind
     * @return
     */
    private boolean isTokenKind(Token.TokenKind tokenKind) {
        return getTokenKind() == tokenKind;
    }

    /**
     * 返回上一个Token
     */
    private void prevToken() {
        reader.prevToken(token.pos);
    }

    private void prevToken(int pos) {
        reader.prevToken(pos);
    }

    /**
     * 语法解析错误抛出异常
     *
     * @param begin
     * @throws Throwable
     */
    private void error(int begin) throws Throwable {
        var end = getEnd();
        var codes = reader.getErrorCode(begin, ++end);
        Objects.requireNonNull(codes);
        throw new ParseException(String.format("语法错误: %s", codes));
    }

    /**
     * 解析失败时,返回最后一个Token的结束位置
     *
     * @return
     */
    private int getEnd() {
        if (Objects.isNull(token)) {
            token = prevToken;
        }
        return token.pos + token.length;
    }
}