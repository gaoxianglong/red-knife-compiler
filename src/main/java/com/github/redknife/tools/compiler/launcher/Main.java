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
package com.github.redknife.tools.compiler.launcher;

import com.github.redknife.tools.compiler.exceptions.ParameterParsingErrorException;
import com.github.redknife.tools.compiler.utils.Constants;
import com.github.redknife.tools.compiler.utils.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * red-knife-compiler启动主函数
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/13 10:56 上午
 */
public class Main {
    /**
     * 源码目录地址
     */
    private static String in;
    /**
     * 是否开启调试模式
     */
    private static boolean isDebug;
    /**
     * 中间代码输出路径地址,缺省为操作系统的临时目录
     */
    private static String out = Constants.OUTPUT_PATH;
    /**
     * 是否编译后执行
     */
    private static boolean execute;
    private static Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * 启动编译器
     *
     * @param args
     * @throws Throwable
     */
    private void start(String[] args) throws Throwable {
        if (Objects.isNull(args) || args.length < 1) {
            throw new ParameterParsingErrorException("缺少相关入参(--in <value> " +
                    "--out <value>)");
        }
        if (!parseParam(args)) System.exit(0);
        Objects.requireNonNull(in, "入参--in <value>不允许为空");
        var context = new Context.Builder(in).out(out).isDebug(isDebug).execute(execute).build();
        Information.print(context);
        new com.github.redknife.tools.compiler.core.Main(context).compile();//执行编译
    }

    /**
     * 入参解析
     *
     * @param args
     * @throws ParameterParsingErrorException
     */
    private boolean parseParam(String[] args) throws ParameterParsingErrorException {
        boolean result = true;
        loop:
        for (int i = 0; i < args.length; i++) {
            String temp = args[i];
            switch (temp) {
                case "--in":
                    in = args[++i];
                    break;
                case "--out":
                    out = args[++i];
                    break;
                case "-d":
                case "-debug":
                case "--debug":
                    isDebug = true;
                    break;
                case "-e":
                case "--execute":
                case "-execute":
                    execute = true;
                    break;
                case "-v":
                case "-version":
                case "--version":
                    System.out.println(String.format("version:%s", Constants.VERSION));
                    result = false;
                    break loop;
                case "-h":
                case "-help":
                case "--help":
                    System.out.println(String.format("操作和入参:\n" +
                            "\t-h -help --help               打印使用规则\n" +
                            "\t-d -debug --debug             开启调试信息, 缺省关闭\n" +
                            "\t-e -execute --execute         编译结束是否立即运行, 缺省不运行\n" +
                            "\t--in           <value>        源代码目录地址\n" +
                            "\t--out          <value>        中间代码的输出目录地址, 缺省为操作系统临时目录下\n" +
                            "\t--version      <value>        输出当前版本号"));
                    result = false;
                    break loop;
                default:
                    throw new ParameterParsingErrorException(String.format("无效入参(%s)", temp));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            new Main().start(args);
        } catch (Throwable e) {
            log.error("{}", Constants.EXCEPTION_PREFIX, e);
        }
    }
}