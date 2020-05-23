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

/**
 * 上下文信息传递类
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/13 4:29 下午
 */
public class Context {
    private String in;
    private String out;
    private String execute;

    private Context(Builder builder) {
        this.in = builder.in;
        this.out = builder.out;
        this.execute = builder.execute;
    }

    public static class Builder {
        private String in;
        private String out;
        private String execute;

        public Builder(String in) {
            this.in = in;
        }

        public Builder out(String out) {
            this.out = out;
            return this;
        }

        public Builder execute(String execute) {
            this.execute = execute;
            return this;
        }

        public Context build() {
            return new Context(this);
        }
    }

    public String getIn() {
        return in;
    }

    public String getOut() {
        return out;
    }

    public String getExecute() {
        return execute;
    }

    @Override
    public String toString() {
        return "Context{" +
                "in='" + in + '\'' +
                ", out='" + out + '\'' +
                ", execute='" + execute + '\'' +
                '}';
    }
}
