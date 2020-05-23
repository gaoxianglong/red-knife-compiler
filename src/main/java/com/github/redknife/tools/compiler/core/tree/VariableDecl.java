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
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/15 3:09 下午
 */
public class VariableDecl extends Statement {
    private TypeTag typeTag;
    private Logger log = LoggerFactory.getLogger(VariableDecl.class);

    public VariableDecl() {
    }

    public VariableDecl(Tag tag) {
        super(tag);
    }

    public VariableDecl(Tag tag, String name) {
        super(tag, name);
    }

    public TypeTag getTypeTag() {
        return typeTag;
    }

    public void setTypeTag(TypeTag typeTag) {
        this.typeTag = typeTag;
    }

    @Override
    public void accept(Visitor visitor, String tag) {
        visitor.visitVariableDecl(this, tag);
    }
}
