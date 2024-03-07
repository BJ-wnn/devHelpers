package com.github.nan.dataprocess.service;

public interface XmlConfigReader {

    /**
     * 加载接口配置文件，文件类型是 .xml
     *
     * 接口配置文件的名称是 [id].xml
     * 存放路径 看 app.properties配置文件 config.folder.path=  ，默认是同级目录 ./config
     *
     * @param id 加工的接口编号，业务保证唯一性
     */
    PropertyAccessor loadConfiguration(String id);
}
