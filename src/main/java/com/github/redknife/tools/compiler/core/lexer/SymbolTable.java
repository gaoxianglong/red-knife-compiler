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
package com.github.redknife.tools.compiler.core.lexer;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 符号表
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/14 4:44 下午
 */
public class SymbolTable {
    /**
     * 记录所有保留字长度
     */
    public int length;
    public Map<Token.Chars, Token.Attribute> attributes;

    protected SymbolTable() {
        attributes = new ConcurrentHashMap<>();
    }

    /**
     * 根据词素从符号表中获取出对应的属性对象,如果不存在就先添加
     *
     * @param morpheme
     * @return
     */
    public Token.Attribute getAttribute(char[] morpheme) {
        var chars = new Token.Chars(morpheme);
        var attribute = attributes.get(chars);
        if (Objects.isNull(attribute)) {
            length += morpheme.length;
            attribute = new Token.Attribute(morpheme, length);
            attributes.put(chars, attribute);
        }
        return attribute;
    }
}