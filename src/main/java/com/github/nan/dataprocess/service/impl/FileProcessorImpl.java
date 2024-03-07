package com.github.nan.dataprocess.service.impl;


import com.github.nan.dataprocess.service.FileProcessor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NanNan Wang
 * @date 2023/12/21
 */
public class FileProcessorImpl implements FileProcessor {

    @Override
    public List<File> getFilesToProcess(String directoryPath, String fileExtension) {
        List<File> resultFiles = new ArrayList<>();

        File directory = new File(directoryPath);

        // 判断是否是一个文件夹目录，或者 文件夹是否存在
        if (!directory.exists() || !directory.isDirectory()) {
            throw new RuntimeException("指定路径 " + directoryPath+"不存在，检查传入的帐期或者配置文件中的配置路径！");
        }

        // 文件下所有的文件
        File[] files = directory.listFiles();

        if(null == files) {
            throw new RuntimeException("指定路径 " + directoryPath+"下不存在任何文件，检查源文件是否已经放到配置的路径！");
        }

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(fileExtension)) {
                resultFiles.add(file);
            }
        }

        if(resultFiles.size() < 1 ) {
            throw new RuntimeException("指定路径 " + directoryPath+"下不存在任何"+fileExtension +"文件！");
        }

        return resultFiles;
    }
}