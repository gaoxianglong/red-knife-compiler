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
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/28 5:29 下午
 */
public class FileWriteException extends ParseException {
    private static final long serialVersionUID = 4936523924498484744L;

    public FileWriteException() {
        super();
    }

    public FileWriteException(String str) {
        super(str);
    }

    public FileWriteException(Throwable throwable) {
        super(throwable);
    }

    public FileWriteException(String str, Throwable throwable) {
        super(str, throwable);
    }
}
