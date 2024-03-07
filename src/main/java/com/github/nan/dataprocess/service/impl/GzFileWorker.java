package com.github.nan.dataprocess.service.impl;

import com.github.nan.dataprocess.req.DataProcessReqVO;
import com.github.nan.dataprocess.service.PropertyAccessor;
import com.github.nan.dataprocess.task.JsonFileProcessingTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/**
 * 处理.gz(也就是业务说的默认文件)， 使用多线程进行处理（业务对速度有一定的要求）
 *
 * @author NanNan Wang
 * @date 2023/12/21
 *
 **/
public class GzFileWorker {

    private int maxThreadNum;

    public void processFileContent(List<File> files, DataProcessReqVO reqVO, PropertyAccessor propertyAccessor) {
        // 要用多线程，使用线程安全队列
        BlockingQueue<File> fileQueue = new LinkedBlockingQueue<>(files);

        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(maxThreadNum);

        try {
            List<Callable<Void>> tasks = new ArrayList<>();

            // 创建文件处理任务
            for (int i = 0; i < maxThreadNum; i++) {
                tasks.add(new JsonFileProcessingTask(fileQueue,reqVO,propertyAccessor));
            }

            // 提交任务并获取 Future 列表
            List<Future<Void>> futures = executorService.invokeAll(tasks);

            // 等待所有任务完成
            for (Future<Void> future : futures) {
                future.get(); // 获取结果，阻塞直到任务完成
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭线程池
            executorService.shutdown();
        }
    }
}
