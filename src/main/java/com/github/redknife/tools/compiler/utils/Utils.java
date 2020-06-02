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
package com.github.redknife.tools.compiler.utils;

import java.util.Objects;

/**
 * 基础工具类
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/16 1:44 上午
 */
public class Utils {
    private static StringBuffer strBuffer;

    /**
     * 返回错误代码段落
     *
     * @param begin
     * @param end
     * @param codes
     * @return
     */
    public static String getErrorParagraph(int begin, int end, char[] codes) {
        Objects.requireNonNull(codes);
        strBuffer = Objects.isNull(strBuffer) ? new StringBuffer() : strBuffer;
        try {
            for (int i = begin; i <= end; i++) {
                strBuffer.append(codes[i]);
            }
            return strBuffer.toString();
        } finally {
            strBuffer.delete(0, strBuffer.length());
        }
    }

    /**
     * 判断当前字符是否是中文字符
     *
     * @param cs
     * @return
     */
    public static final boolean isChinese(char cs) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(cs);
        if (block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || block == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || block == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || block == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || block == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
}
