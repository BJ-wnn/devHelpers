package com.github.nan.dataprocess.service;

import com.github.nan.dataprocess.req.DataProcessReqVO;
import com.github.nan.dataprocess.service.impl.GzFileWorker;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author NanNan Wang
 * @date 2023/12/21
 */
@Slf4j
public class GzipJsonProcessor {

    private CommandLineParser commandLineParser;    //用于参数数据（main方法传入的数组 args） 转换为 一个实体类

    private XmlConfigReader xmlConfigReader;    //读取配置文件

    private FileProcessor fileProcessor;    // 主要获取源文件

    private GzFileWorker gzFileWorker;

    public GzipJsonProcessor(CommandLineParser commandLineParser, XmlConfigReader xmlConfigReader, FileProcessor fileProcessor, GzFileWorker gzFileWorker) {
        this.commandLineParser = commandLineParser;
        this.xmlConfigReader = xmlConfigReader;
        this.fileProcessor = fileProcessor;
        this.gzFileWorker = gzFileWorker;
    }

    public void processData(String[] args) {
//        1. 传入的参数数组转换成实体类（这步可以不做）
        log.info("开始数据加工程序，解析入参...");
        final DataProcessReqVO reqVO = commandLineParser.convert(args);
        log.info("程序使用的参数是：" + reqVO.toString());

//        2.读取配置文件
        log.info("开始读取配置文件...");
        final PropertyAccessor propertyAccessor = xmlConfigReader.loadConfiguration(reqVO.getProcessId());

        log.info("获取要处理的源文件...");
//        3.获取要加工的源文件； 源文件路径支持替符号 例如/dat/91110/{0}/{1}。当然了 如果业务人员没有使用替换符号，那也不影响哈。
        final List<File> gzFiles = fileProcessor.getFilesToProcess(
                MessageFormat.format(propertyAccessor.getSourceFolderPath(),reqVO.getProvinceId(),reqVO.getPeriod()),
                propertyAccessor.getSourceFileExtension());
        log.info("一共要加工" + gzFiles.size() + "个文件！");

//        4.处理成json文件
        gzFileWorker.processFileContent(gzFiles,reqVO,propertyAccessor);

//        5.处理成zip文件


    }

    ////        结果文件夹目录
//        Path targetFilesFolder = Paths.get(MessageFormat.format(propertyAccessor.getTargetFolderPath(),reqVO.getProvinceId(),reqVO.getPeriod()));


}