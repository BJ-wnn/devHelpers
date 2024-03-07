package com.github.nan.dataprocess.service.impl;

import com.github.nan.dataprocess.ProgramOptions;
import com.github.nan.dataprocess.service.PropertyAccessor;
import com.github.nan.dataprocess.service.XmlConfigReader;
import lombok.extern.slf4j.Slf4j;
import com.github.nan.dataprocess.service.dto.DataTransformationSettingsDTO;
import com.github.nan.dataprocess.utils.XmlParserUtil;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author NanNan Wang
 * @date 2023/12/21
 */
@Slf4j
public class XmlConfigReaderImpl implements XmlConfigReader {

    private ProgramOptions programOptions;


    public XmlConfigReaderImpl(ProgramOptions programOptions) {
        this.programOptions = programOptions;
    }

    @Override
    public PropertyAccessor loadConfiguration(String id) {
//        获取配置文件
        final Path filePath = getConfigFilePath(id);
//        配置文件转成实体类
        final DataTransformationSettingsDTO dto = XmlParserUtil.parseXmlFile(filePath, DataTransformationSettingsDTO.class);
//        我还想做一些校验哈 毕竟谁都不能完全相信
        dto.validate();
        return dto;
    }


    /**
     * 判断配置文件是否存在
     *
     * @param id
     * @return
     */
    private Path getConfigFilePath(String id) {
        String filePath = programOptions.getConfigFolderPath() + id + ".xml";
        // 使用 Paths 获取 Path 对象
        Path path = Paths.get(filePath);
        // 使用 Files 类的 exists 方法判断文件是否存在
        boolean fileExists = Files.exists(path);
        if (fileExists) {
            return path;
        } else {
            throw new RuntimeException("配置信息错误：找不到配置文件：" + filePath);
        }
    }
}
