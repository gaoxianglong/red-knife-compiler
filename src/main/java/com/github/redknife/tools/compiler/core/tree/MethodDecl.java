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

import java.util.*;

/**
 * 方法语法树
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/15 2:50 下午
 */
public class MethodDecl extends Tree {
    private TypeTag typeTag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodDecl)) return false;
        MethodDecl that = (MethodDecl) o;
        return getTypeTag() == that.getTypeTag() && getName() == that.getName();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTypeTag(), getName());
    }

    public MethodDecl() {
    }

    public MethodDecl(Tag tag) {
        super(tag);
    }

    public MethodDecl(Tag tag, String name) {
        super(tag, name);
    }

    public MethodDecl(Tag tag, TypeTag typeTag, String name) {
        this(tag, name);
        this.typeTag = typeTag;
    }

    public TypeTag getTypeTag() {
        return typeTag;
    }

    public void setTypeTag(TypeTag typeTag) {
        this.typeTag = typeTag;
    }

    @Override
    public void accept(Visitor visitor, String tag) throws Throwable {
        visitor.visitMethodDecl(this, tag);
    }
}
