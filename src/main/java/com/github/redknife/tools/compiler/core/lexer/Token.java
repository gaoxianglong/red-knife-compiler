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

import java.util.Arrays;

/**
 * 词法单元，每一个Token都对应着一个词素
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/14 4:12 下午
 */
public class Token {
    /**
     * 每个Token对应的属性值
     */
    public Attribute attribute;
    /**
     * Token类型
     */
    public TokenKind tokenKind;
    /**
     * Token位置，回溯用
     */
    public int pos;
    /**
     * 词素长度
     */
    public int length;

    public Token(Attribute attribute, TokenKind tokenKind, int pos) {
        this.attribute = attribute;
        this.tokenKind = tokenKind;
        this.pos = pos;
        this.length = attribute.morpheme.length;
    }

    @Override
    public String toString() {
        return "Token{" +
                "attribute=" + attribute +
                ", tokenKind=" + tokenKind +
                ", pos=" + pos +
                '}';
    }

    public enum TokenKind {
        //@formatter:off
        IDENTIFIER, INT("int"), INTLITERAL("0-9"), STRINGLITERAL("string"),
        TRUE("true"), FALSE("false"), BOOL("bool"), IF("if"),
        ELSE("else"), FOR("for"), LPAREN("("), RPAREN(")"),
        RBRACE("}"), LBRACKET("["), RBRACKET("]"), LBRACE("{"),
        COMMA(","), DOT("."), EQ("="), NULL("null"),
        GT(">"), LT("<"), BANG("!"), TILDE("~"),
        QUES("?"), COLON(":"), EQEQ("=="), LTEQ("<="),
        GTEQ(">="), BANGEQ("!="), AMPAMP("&&"), BARBAR("||"),
        PLUSPLUS("++"), SUBSUB("--"), PLUS("+"), SUB("-"),
        STAR("*"), SLASH("/"), AMP("&"), BAR("|"),
        CARET("^"), PERCENT("%"), LTLT("<<"), GTGT(">>"),
        GTGTGT(">>>"), PLUSEQ("+="), SUBEQ("-="), STAREQ("*="),
        SLASHEQ("/="), AMPEQ("&="), BAREQ("|="), CARETEQ("^="),
        PERCENTEQ("%="), LTLTEQ("<<="), GTGTEQ(">>="), GTGTGTEQ(">>>="),
        MONKEYS_AT("@"), RETURN("return"), SEMI(";"), VOID("void"),
        CHARS("chars"), UNKNOWN();
        //@formatter:on
        public String name;

        TokenKind() {
        }

        TokenKind(String name) {
            this.name = name;
        }
    }

    public static class Attribute {
        /**
         * 词素
         */
        public char[] morpheme;
        /**
         * 保留字标记
         */
        public int flag;

        public Attribute(char[] morpheme, int flag) {
            this.morpheme = morpheme;
            this.flag = flag;
        }

        @Override
        public String toString() {
            return String.format("morpheme:%s, flag:%s", new String(morpheme), flag);
        }
    }

    /**
     * 对词素进行封装
     */
    static class Chars {
        public char[] morpheme;

        public Chars(char[] morpheme) {
            this.morpheme = morpheme;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Chars)) return false;
            Chars chars = (Chars) o;
            return Arrays.equals(morpheme, chars.morpheme);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(morpheme);
        }
    }
}