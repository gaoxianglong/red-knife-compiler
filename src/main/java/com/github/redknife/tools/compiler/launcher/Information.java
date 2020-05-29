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

import com.github.redknife.tools.compiler.utils.CapacityConvert;
import com.github.redknife.tools.compiler.utils.Constants;
import com.github.redknife.tools.compiler.utils.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * 启动时需要输出的各种资源信息
 *
 * @author gao_xianglong@sina.com
 * @version 0.1-SNAPSHOT
 * @date created in 2020/5/13 4:07 下午
 */
public class Information {
    private static Logger log = LoggerFactory.getLogger("");

    protected static void print(Context context) {
        RedKnifeBanner.printBanner();
        log.info("Copyright (C) 2020-2030 {}", Constants.COPYRIGHT);
        log.info("Ignite documentation: {}", Constants.IGNITE_DOCUMENTATION);
        log.info("OS: {}", Constants.OS_NAME);
        log.info(String.format("JVM information: %s %s %s %s", Constants.JAVA_RUNTIME_TIME,
                Constants.JAVA_RUNTIME_VERSION, Constants.JAVA_VM_VENDOR, Constants.JAVA_VM_NAME));
        log.info(String.format("Initial heap size is %s (usedsize=%s, maxsize=%s)",
                getCapacityUnit(Constants.INIT_HEAP), getCapacityUnit(Constants.USE_HEAP),
                getCapacityUnit(Constants.MAX_HEAP)));
        log.info("Param: --sourcecode-path:{}, --output-path:{}", context.getIn(), context.getOut());
        log.info("Pid: {}", Constants.PID);

    }

    /**
     * 根据传入的byte来自动判断容量单位
     *
     * @param size
     * @return
     */
    private static String getCapacityUnit(long size) {
        String unit = null;
        BigDecimal bigDecimal = new BigDecimal(size);
        final double B = bigDecimal.doubleValue();
        final double KB = bigDecimal.divide(new BigDecimal(CapacityConvert.KB.value), 2, BigDecimal.ROUND_DOWN).doubleValue();
        final double MB = bigDecimal.divide(new BigDecimal(CapacityConvert.MB.value), 2, BigDecimal.ROUND_DOWN).doubleValue();
        final double GB = bigDecimal.divide(new BigDecimal(CapacityConvert.GB.value), 2, BigDecimal.ROUND_DOWN).doubleValue();
        if ((long) GB > 0) {
            unit = String.format("%sGB", GB);
        } else if ((long) MB > 0) {
            unit = String.format("%sMB", MB);
        } else if ((long) KB > 0) {
            unit = String.format("%sKB", KB);
        } else if ((long) B > 0) {
            unit = String.format("%sB", B);
        }
        return unit;
    }
}
