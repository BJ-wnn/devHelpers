package com.github.nan.dataprocess.task;

import com.github.nan.dataprocess.req.DataProcessReqVO;
import com.github.nan.dataprocess.service.PropertyAccessor;
import com.github.nan.dataprocess.utils.ThreadSafeIncrementalSequenceGenerator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.zip.GZIPInputStream;

/**
 * @author NanNan Wang
 * @date 2023/12/21
 */
public class JsonFileProcessingTask implements Callable<Void> {

    private BlockingQueue<File> fileQueue;

    private DataProcessReqVO reqVO;

    private PropertyAccessor propertyAccessor;

    public JsonFileProcessingTask(BlockingQueue<File> fileQueue, DataProcessReqVO reqVO, PropertyAccessor propertyAccessor) {
        this.fileQueue = fileQueue;
        this.reqVO = reqVO;
        this.propertyAccessor = propertyAccessor;
    }


    @Override
    public Void call() throws Exception {

        while (true) {
            File file = fileQueue.poll();
            if (file == null) {
                // 队列为空，任务结束
                break;
            }
//            生成json文件
            final List<File> jsonFiles = extractGzToJson(file);

        }
        return null;
    }

    /**
     * 使用gz文件加工成json明文文件
     *
     * @param gzFile
     * @return
     */
    private List<File> extractGzToJson(File gzFile) {
        List<File> jsonFilesList = new ArrayList<>(10);

        File jsonFile = generateTargetFile();
        try(FileInputStream fileInputStream = new FileInputStream(gzFile);
            GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
            InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream,propertyAccessor.getSourceFileCharset());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {

            String line;
            StringBuilder currentOutput = new StringBuilder();
            int currentBatchSize = 0;


            while ((line = bufferedReader.readLine()) != null) {
                // 处理每一行配置文件内容，内容以配置的分隔符处理，并且增加了limit。why? 担心数据源的数据是有问题的无法处理呢，比如少个字段啥的。当然了 如果数据错 那也没啥办法。看业务需求
                final String jsonStr = propertyAccessor.getDataModel().toJsonStr(Arrays.asList(line.split(propertyAccessor.getSourceFileContentSeparator(), propertyAccessor.getDataModel().countFields())));
System.out.println(jsonStr);
                currentOutput.append(jsonStr).append(System.lineSeparator());
                currentBatchSize++;

            }

            // 处理最后一部分内容
            if (currentOutput.length() > 0) {

            }

        }catch (IOException e) {
            throw new RuntimeException("加工gz文件生成json明文文件出错！");
        }
        
        return null;
    }



    private File generateTargetFile() {
        // 创建File对象

        String timePlaceHolder  = null;
        if("F".equals(this.propertyAccessor.getProcessType())) {
//          全量接口；入参数：yyyyMM  文件内容的日期 yyyyMMddHH  dd是月末最后一天，HH是00
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMM");
            final LocalDateTime localDateTime = YearMonth.parse(this.reqVO.getPeriod(), dateFormat).atEndOfMonth().atStartOfDay();
            timePlaceHolder = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
        } else if("P".equals(this.propertyAccessor.getProcessType())) {
//          日增量接口;入参数：yyyyMMdd 文件名称中的日期 yyyyMMddHH  HH是00
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDateTime localDateTime = LocalDate.parse(this.reqVO.getPeriod(), dateFormat).atStartOfDay();;
            timePlaceHolder = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
        } else if("P_1".equals(this.propertyAccessor.getProcessType())){
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
            timePlaceHolder = LocalDate.parse(this.reqVO.getPeriod(), dateFormat).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        } else {
            throw new RuntimeException("无法正确解析!检查传入时间或者配置文件！");
        }

        String fileName = MessageFormat.format(this.propertyAccessor.getTargetFileName(),timePlaceHolder, ThreadSafeIncrementalSequenceGenerator.generateIncrementalSequence());
        final Path jsonFolderPath = getTargetRootPath().resolve("json");
        final Path jsonFilePath = jsonFolderPath.resolve(fileName+".txt");
        try {
            Files.createDirectories(jsonFolderPath);
            if(!jsonFilePath.toFile().exists()) {
                Files.createFile(jsonFilePath);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonFilePath.toFile();
    }


    /**
     * 输出文件目录处理一下，把占位符处理好
     *
     * @return
     */
    private Path getTargetRootPath() {
        return Paths.get(MessageFormat.format(this.propertyAccessor.getTargetFolderPath(),this.reqVO.getProvinceId(),this.reqVO.getPeriod()));
    }

    private void appendToJsonFile(File file, String content) {
        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), propertyAccessor.getTargetFileCharset(), StandardOpenOption.APPEND)) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException("写入Json文件发生错误！");
        }
    }
}
