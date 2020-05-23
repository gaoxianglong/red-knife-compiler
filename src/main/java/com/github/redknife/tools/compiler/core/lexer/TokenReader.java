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
import com.github.redknife.tools.compiler.utils.RedKnifeFileManager;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Objects;

/**
 * Token读取操作类
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/14 4:11 下午
 */
public class TokenReader {
    private Scanner lexer;
    public TokenReader init(CharBuffer cs) {
        lexer = new Scanner(RedKnifeFileManager.toArray(cs)).init();
        return this;
    }

    public Token nextToken() throws ParseException {
        return lexer.nextToken();
    }

    public void prevToken(int pos) {
        lexer.prevToken(pos);
    }

    /**
     * 获取错误代码片段
     *
     * @param begin
     * @param end
     * @return
     */
    public String getErrorCode(int begin, int end) {
        if (end < begin) {
            return null;
        }
        var source = lexer.getCodes();
        var target = new char[end - begin];
        System.arraycopy(source, begin, target, 0, target.length);
        return new String(target);
    }

    /**
     * 返回源码内容
     *
     * @return
     */
    public char[] getCodes() {
        return lexer.getCodes();
    }

    /**
     * 重置词法分析器相关属性
     *
     * @param cb
     */
    public void reset(CharBuffer cb) {
        Objects.requireNonNull(lexer);
        lexer.setCodes(RedKnifeFileManager.toArray(cb));
        lexer.setIndex(0);
        lexer.setCh('\u0000');
        lexer.setSbuf(null);
    }
}