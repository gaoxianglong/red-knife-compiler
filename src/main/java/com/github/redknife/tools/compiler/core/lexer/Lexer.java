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

import com.github.redknife.tools.compiler.exceptions.ParseException;

/**
 * red-knife-compiler词法分析器接口
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/14 4:08 下午
 */
public interface Lexer {
    /**
     * 读取下一个Token
     *
     * @return
     * @throws ParseException
     */
    Token nextToken() throws ParseException;

    /**
     * 读取上一个Token，回溯用
     *
     * @param pos
     */
    void prevToken(int pos);

    /**
     * 读取上一个符号
     */
    void prevChar();

    /**
     * 读取下一个符号
     */
    void nextChar();
}
