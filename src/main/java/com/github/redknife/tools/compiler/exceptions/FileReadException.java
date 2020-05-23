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
package com.github.redknife.tools.compiler.exceptions;

/**
 * 源码读取异常信息类
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/14 10:54 上午
 */
public class FileReadException extends ParseException {
    private static final long serialVersionUID = 5288309245872559198L;

    public FileReadException() {
        super();
    }

    public FileReadException(String str) {
        super(str);
    }

    public FileReadException(Throwable throwable) {
        super(throwable);
    }

    public FileReadException(String str, Throwable throwable) {
        super(str, throwable);
    }
}
