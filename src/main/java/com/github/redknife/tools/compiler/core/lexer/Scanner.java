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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * red-knife-compiler词法分析器实现类
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/14 4:14 下午
 */
public class Scanner implements Lexer {
    /**
     * 源文件内容
     */
    private char[] codes;
    /**
     * 当前符号
     */
    private char ch;
    /**
     * 结束符号
     */
    public final byte EOI = 0x1A;
    /**
     * 换行符
     */
    public final byte LF = 0xA;
    /**
     * 回车符
     */
    public final byte CR = 0xD;
    /**
     * 符号表
     */
    private SymbolTable symbolTable;
    /**
     * 所有保留字符号串的总长度,当词素的符号串长度>此值时，意味着这是一个标识符
     */
    private int maxKey;
    /**
     * 反向索引表,Attribute.flag对应TokenKin序数
     */
    private Map<Integer, Integer> indexs;
    /**
     * 词素缓存
     */
    private char[] sbuf;
    /**
     * 符号索引
     */
    private int index;
    private static Logger log = LoggerFactory.getLogger(Scanner.class);

    protected Scanner(char[] cs) {
        Objects.requireNonNull(cs);
        codes = cs;
        codes = Arrays.copyOf(codes, codes.length + 1);
        codes[codes.length - 1] = EOI;
        symbolTable = new SymbolTable();
        indexs = new ConcurrentHashMap<>();
    }

    /**
     * 相关初始化操作,优先将保留字添加至符号表中
     *
     * @return
     */
    protected Scanner init() {
        Stream.of(Token.TokenKind.values()).forEach(tokenKind -> {
            var name = tokenKind.name;
            if (Objects.nonNull(name)) {
                var attribute = symbolTable.getAttribute(name.toCharArray());
                var flag = attribute.flag;
                indexs.put(flag, tokenKind.ordinal());
                maxKey = maxKey < flag ? flag : maxKey;
            }
        });
        log.debug("maxKey:{}", maxKey);
        return this;
    }


    @Override
    public Token nextToken() throws ParseException {
        var pos = index;//记录每一个Token的起始位
        Token result = null;
        loop:
        do {
            nextChar();
            switch (ch) {
                //@formatter:off
                case ' ': case '\t': case LF: case CR:
                    break;
                case 'A': case 'B': case 'C': case 'D': case 'E':
                case 'F': case 'G': case 'H': case 'I': case 'J':
                case 'K': case 'L': case 'M': case 'N': case 'O':
                case 'P': case 'Q': case 'R': case 'S': case 'T':
                case 'U': case 'V': case 'W': case 'X': case 'Y':
                case 'Z':
                case 'a': case 'b': case 'c': case 'd': case 'e':
                case 'f': case 'g': case 'h': case 'i': case 'j':
                case 'k': case 'l': case 'm': case 'n': case 'o':
                case 'p': case 'q': case 'r': case 's': case 't':
                case 'u': case 'v': case 'w': case 'x': case 'y':
                case 'z':
                case '$': case '_':
                    result = scanIdent(pos);
                    break loop;
                case '0': case '1': case '2': case '3': case '4':
                case '5': case '6': case '7': case '8': case '9':
                    result = scanNumber(pos);
                    break loop;
                case '\"':
                    result = scanString(pos);
                    break loop;
                case '[': case ']': case '(': case ')':case '.':
                case ',': case '{': case '}': case ';':
                    //@formatter:on
                    try {
                        addMorpheme();
                        result = getToken(pos);
                    } finally {
                        sbuf = null;
                    }
                    break loop;
                default:
                    var token = scanOperator(pos);
                    if (Objects.nonNull(token)) {
                        result = token;
                    }
                    break loop;
            }
        } while (ch != EOI);
        return result;
    }

    /**
     * 读取一个完整的字符串
     *
     * @param pos
     * @return
     * @throws ParseException
     */
    private Token scanString(int pos) throws ParseException {
        do {
            addMorpheme();
            nextChar();
        } while (ch != '\"' && ch != CR && ch != LF && ch != EOI);
        if (ch != '\"') throw new ParseException(String.format("未结束的字符串文字:%s", new String(sbuf)));
        addMorpheme();
        try {
            return getToken(Token.TokenKind.STRINGLITERAL, pos);
        } finally {
            sbuf = null;
        }
    }

    /**
     * 读取一个完整的运算符
     *
     * @param pos
     * @return
     */
    private Token scanOperator(int pos) {
        if (ch == EOI) return null;
        while (true) {
            switch (ch) {
                //@formatter:off
                case '!': case '%': case '&': case '*':
                case '?': case '+': case '-': case ':':
                case '<': case '=': case '>': case '^':
                case '|': case '~': case '@': case '/':
                    break;
                //@formatter:on
                default:
                    try {
                        return getToken(pos);
                    } finally {
                        prevChar();
                        sbuf = null;
                    }
            }
            addMorpheme();
            var attribute = symbolTable.getAttribute(sbuf);
            var tokenKind = getTokenKin(attribute.flag);
            if (tokenKind == Token.TokenKind.IDENTIFIER) {
                try {
                    sbuf = Arrays.copyOf(sbuf, sbuf.length - 1);
                    return getToken(pos);
                } finally {
                    prevChar();
                    sbuf = null;
                }
            }
            nextChar();
        }
    }

    /**
     * 读取一个完整的数字字面值
     *
     * @param pos
     * @return
     */
    private Token scanNumber(int pos) {
        while (true) {
            switch (ch) {
                //@formatter:off
                case '0': case '1': case '2': case '3': case '4':
                case '5': case '6': case '7': case '8': case '9':
                    break;
                //@formatter:on
                default:
                    try {
                        return getToken(Token.TokenKind.INTLITERAL, pos);
                    } finally {
                        prevChar();
                        sbuf = null;
                    }
            }
            addMorpheme();
            nextChar();
        }
    }

    /**
     * 读取一个完整的标识符
     *
     * @param pos
     * @return
     */
    private Token scanIdent(int pos) {
        while (true) {
            switch (ch) {
                //@formatter:off
                case 'A': case 'B': case 'C': case 'D': case 'E':
                case 'F': case 'G': case 'H': case 'I': case 'J':
                case 'K': case 'L': case 'M': case 'N': case 'O':
                case 'P': case 'Q': case 'R': case 'S': case 'T':
                case 'U': case 'V': case 'W': case 'X': case 'Y':
                case 'Z':
                case 'a': case 'b': case 'c': case 'd': case 'e':
                case 'f': case 'g': case 'h': case 'i': case 'j':
                case 'k': case 'l': case 'm': case 'n': case 'o':
                case 'p': case 'q': case 'r': case 's': case 't':
                case 'u': case 'v': case 'w': case 'x': case 'y':
                case 'z':
                case '$': case '_':
                case '0': case '1': case '2': case '3': case '4':
                case '5': case '6': case '7': case '8': case '9':
                    break;
                //@formatter:on
                default:
                    try {
                        return getToken(pos);
                    } finally {
                        prevChar();//回溯上一个符号
                        sbuf = null;
                    }
            }
            addMorpheme();
            nextChar();
        }
    }

    /**
     * 组装词素
     */
    private void addMorpheme() {
        sbuf = Objects.isNull(sbuf) ? new char[1] : Arrays.copyOf(sbuf, sbuf.length + 1);
        sbuf[sbuf.length - 1] = ch;
    }

    /**
     * 获取Token序列
     *
     * @param tokenKind
     * @param pos
     * @return
     */
    private Token getToken(Token.TokenKind tokenKind, int pos) {
        var attribute = symbolTable.getAttribute(sbuf);
        //根据属性对象的flag字段从反向索引表中获取出TokenKin的序数，再根据序数获取出对应的TokenKin
        return new Token(attribute, Objects.nonNull(tokenKind) ? tokenKind :
                getTokenKin(attribute.flag), pos);
    }

    private Token getToken(int pos) {
        return getToken(null, pos);
    }

    /**
     * 获取TokenKind
     *
     * @return
     */
    private Token.TokenKind getTokenKin(int flag) {
        return flag <= maxKey ? Token.TokenKind.values()[indexs.get(flag)] : Token.TokenKind.IDENTIFIER;
    }

    @Override
    public void prevToken(int pos) {
        ch = codes[index = pos];
    }

    @Override
    public void prevChar() {
        ch = codes[--index];
    }

    @Override
    public void nextChar() {
        if (index < codes.length) {
            ch = codes[index++];
        }
    }

    protected char[] getCodes() {
        return codes;
    }

    protected void setCodes(char[] cs) {
        codes = cs;
        codes = Arrays.copyOf(codes, codes.length + 1);
        codes[codes.length - 1] = EOI;
    }

    protected char getCh() {
        return ch;
    }

    protected void setCh(char ch) {
        this.ch = ch;
    }

    protected char[] getSbuf() {
        return sbuf;
    }

    protected void setSbuf(char[] sbuf) {
        this.sbuf = sbuf;
    }

    protected int getIndex() {
        return index;
    }

    protected void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Scanner{" +
                "codes=" + Arrays.toString(codes) +
                ", ch=" + ch +
                ", EOI=" + EOI +
                ", LF=" + LF +
                ", CR=" + CR +
                ", symbolTable=" + symbolTable +
                ", maxKey=" + maxKey +
                ", indexs=" + indexs +
                ", sbuf=" + Arrays.toString(sbuf) +
                ", index=" + index +
                '}';
    }

    public static void main(String[] agrs) throws ParseException {
        String code = "String str = \"Hello World\";" +
                "int v1 = 100;" +
                "boolean v2 = true;" +
                "int v3=(1+2)*3;\n" +
                "\r\t\nv++;";
        Scanner scanner = new Scanner(code.toCharArray()).init();
        while (true) {
            var token = scanner.nextToken();
            if (Objects.isNull(token)) break;
            log.info(token.toString());
        }
    }
}
