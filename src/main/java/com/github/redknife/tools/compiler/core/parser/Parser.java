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

import com.github.redknife.tools.compiler.core.tree.Tree;
import com.github.redknife.tools.compiler.exceptions.ParseException;

import java.nio.CharBuffer;

/**
 * 语法分析器接口
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/14 11:13 上午
 */
public interface Parser {
    /**
     * 语法分析过程
     *
     * @param cs
     * @param className
     * @return
     * @throws Throwable
     */
    Tree parse(CharBuffer cs, String className) throws Throwable;
}
