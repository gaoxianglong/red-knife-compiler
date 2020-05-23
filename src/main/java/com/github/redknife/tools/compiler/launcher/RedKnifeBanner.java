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

import com.github.redknife.tools.compiler.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * 启动时需要输出的Banner信息
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/13 3:55 下午
 */
public class RedKnifeBanner {
    private static String BANNER[] = {"   ___         ____ __     _ ___   " ,
            "  / _ \\___ ___/ / //_/__  (_) _/__ " ,
            " / , _/ -_) _  / ,< / _ \\/ / _/ -_)" ,
            "/_/|_|\\__/\\_,_/_/|_/_//_/_/_/ \\__/ "};
    private static Logger log = LoggerFactory.getLogger(RedKnifeBanner.class);

    /**
     * 输出Banner信息
     */
    protected static void printBanner() {
        final String LINE = Constants.LINE;
        final AtomicInteger NUM = new AtomicInteger(0);
        StringBuffer bannerBuf = new StringBuffer();
        bannerBuf.append(String.format("Welcome to %s", LINE));
        Stream.of(BANNER).forEach(x -> {
            bannerBuf.append(NUM.incrementAndGet() >= BANNER.length ? x : String.format("%s%s", x, LINE));
        });
        bannerBuf.append(String.format("\tversion: %s%s",
                Constants.VERSION, LINE));
        log.info(bannerBuf.toString());
    }
}
