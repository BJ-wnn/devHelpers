package com.github.nan.dataprocess.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author NanNan Wang
 * @date 2023/12/25
 */
public class ThreadSafeIncrementalSequenceGenerator {


    private static AtomicInteger counter = new AtomicInteger(1);


    public static String generateIncrementalSequence() {
        // 使用String.format格式化数字，保持固定位数
        String formattedValue = String.format("%06d", counter.getAndIncrement());

        return formattedValue;
    }
}
