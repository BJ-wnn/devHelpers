package com.github.nan.dataprocess.service;


import com.github.nan.dataprocess.req.DataProcessReqVO;

public interface CommandLineParser {

    /**
     * 主要用于参数转换成请求实体类，并且进行一定的参数校验，没啥特别的作用，只是为了传递时好用点。
     *
     * @param args main方法启动时传入的参数
     * @return
     */
    DataProcessReqVO convert(String[] args);
}
