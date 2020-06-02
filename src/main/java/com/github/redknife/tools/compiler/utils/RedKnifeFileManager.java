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

import com.github.redknife.tools.compiler.exceptions.FileReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * 文件管理类
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/13 5:52 下午
 */
public class RedKnifeFileManager {
    private static Logger log = LoggerFactory.getLogger(RedKnifeFileManager.class);

    /**
     * 将源文件内容解析到ByteBuffer中
     *
     * @param in
     * @return
     * @throws Throwable
     */
    private static ByteBuffer makeByteBuffer(InputStream in) throws Throwable {
        Objects.requireNonNull(in);
        int limit = in.available() <= 1024 ? 1024 : in.available();
        var result = ByteBufferCache.get(limit);
        //将文件内容读入到ByteBuffer中，并设置其起始位置
        result.position(in.read(result.array(), 0, limit));
        return result.flip();//复位position,limit=position,position=0
    }

    static class ByteBufferCache {
        static ByteBuffer cached;

        static ByteBuffer get(int capacity) {
            capacity = capacity < 20480 ? 20480 : capacity;
            ByteBuffer result = null;
            if (Objects.nonNull(cached) && cached.capacity() >= capacity) {
                log.debug("before cache hashcode:{}", System.identityHashCode(cached));
                result = cached.clear();
                log.debug("after cache hashcode:{}", System.identityHashCode(result));
            } else {
                result = ByteBuffer.allocate(capacity + capacity >> 1);
            }
            cached = null;
            return result;
        }

        static void put(ByteBuffer x) {
            cached = x;
        }
    }

    /**
     * 将源码读取到char[]中
     *
     * @param file
     * @return
     * @throws Throwable
     */
    public static CharBuffer getCharContent(File file) throws Throwable {
        InputStream in = new FileInputStream(file);
        if (0 >= in.available()) {
            throw new FileReadException(String.format("%s中不存在任何内容", file.getName()));
        }
        var bb = makeByteBuffer(in);
        ByteBufferCache.put(bb);
        return Charset.forName(Constants.FILE_ENCODING).decode(bb);//解码后直接返回
    }

    /**
     * 将CharBuffer转换为char[]
     *
     * @param cb
     * @return
     */
    public static char[] toArray(CharBuffer cb) {
        var src = cb.hasArray() ? ((CharBuffer) cb.compact().flip()).array() :
                cb.toString().toCharArray();
        var result = new char[src.length + 3];
        System.arraycopy(src, 0, result, 1, src.length);
        result[0] = '{';
        result[result.length - 2] = '\n';
        result[result.length - 1] = '}';
        return result;
    }
}
