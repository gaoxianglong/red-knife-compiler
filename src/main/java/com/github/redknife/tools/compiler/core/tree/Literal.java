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

/**
 * 字面值语法树
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/15 3:10 下午
 */
public class Literal extends Expression {
    private TypeTag typeTag;

    public Literal() {
    }

    public Literal(Tag tag) {
        super(tag);
    }

    public Literal(Tag tag, String name) {
        super(tag, name);
    }

    public Literal(Tag tag, TypeTag typeTag, String name) {
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
        visitor.visitLiteral(this, tag);
    }
}
