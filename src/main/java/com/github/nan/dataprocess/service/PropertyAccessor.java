package com.github.nan.dataprocess.service;


import com.github.nan.dataprocess.service.dto.DataModel;

import java.nio.charset.Charset;

/**
 * 主要用于定义读取配置文件的某些配置信息。
 *
 * 这些配置都是数据加工时可能用得到的，设计一个接口封装获取，用起来简单点
 *
 */
public interface PropertyAccessor{


    /**
     * 获取加工程序编码（id）
     * @return
     */
    String getProcessId();

    /**
     * 获取加工程序类型
     *
     * 支撑的类型
     * 增量、日接口-P
     * 全量、月接口-F
     *
     * @return
     */
    String getProcessType();

//  ************************** 数据来源的一些信息  **************************

    /**
     * 获取源文件的目录地址
     * @return
     */
    String getSourceFolderPath();


    /**
     * 获取源文件的字符编码
     * @return
     */
    Charset getSourceFileCharset();


    /**
     * 获取源文件内容的分隔符
     * @return
     */
    String getSourceFileContentSeparator();


    /**
     * 获取源文件的扩展名
     * @return
     */
    String  getSourceFileExtension();


//    ************************** 输出（结果）文件的一些配置   **************************

    /**
     * 获取输出文件的目录地址
     * @return
     */
    String getTargetFolderPath();


    /**
     * 获取输出文件名称，如果有占位符，业务侧保证文件名是有一定的规则的
     *
     * 比如： KH_2000_[时间]_P_[序号]
     * @return
     */
    String getTargetFileName();

    /**
     * 获取输出文件的字符编码
     * @return
     */
    Charset getTargetFileCharset();


    /**
     * 获取输出文件的最大字节数，也就是控制输出文件的大小，单位是 KB
     * @return
     */
    long getTargetFileMaxSize();

//    ************************** 元数据的一些配置 也就是要json处理的数据   **************************


    /**
     * 获取 元数据结构配置
     * @return
     */
    DataModel getDataModel();

}
