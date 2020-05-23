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

import java.util.ArrayList;
import java.util.List;

/**
 * AST抽象语法树
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/13 5:46 下午
 */
public abstract class Tree {
    /**
     * 子节点
     */
    private List<Tree> childs;
    /**
     * 符号串
     */
    private String name;
    /**
     * 节点标签, 用于标记语法树类型
     */
    private Tag tag;

    public Tree() {
    }

    public Tree(Tag tag, String name) {
        this.tag = tag;
        this.name = name;
        childs = new ArrayList<>();
    }

    public Tree(Tag tag) {
        this(tag, null);
    }

    public abstract void accept(Visitor visitor, String tab);

    public List<Tree> getChilds() {
        return childs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag getTag() {
        return tag;
    }

    /**
     * 语法树标签
     */
    public enum Tag {
        CLASSDEF, METHODDEF, VARDEF, BLOCK, FORLOOP,
        CONDEXPR, POSTDEC, LITERAL, PARENS, STATEMENT,
        NO_TAG, PLUS, SUB, STAR, SLASH, POSTINC,
        IF, ELSEIF, ELSE,
        NE, // !=
        LT, // <
        GT, // >
        LE, // <=
        GE, // >=
        EQ; //==
    }

    @Override
    public String toString() {
        return "Tree{" +
                "childs=" + childs +
                ", name='" + name + '\'' +
                ", tag=" + tag +
                '}';
    }
}
