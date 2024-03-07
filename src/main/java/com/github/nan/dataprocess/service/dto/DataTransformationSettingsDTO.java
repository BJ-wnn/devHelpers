package com.github.nan.dataprocess.service.dto;

import com.github.nan.dataprocess.service.PropertyAccessor;
import org.apache.commons.lang3.ObjectUtils;

import javax.xml.bind.annotation.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author NanNan Wang
 * @date 2023/12/21
 */
@XmlRootElement(name = "dataprocess")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataTransformationSettingsDTO implements PropertyAccessor {

    @XmlAttribute(name = "id")
    private String id;  //数据加工程序编码 一般是数字；

    @XmlAttribute(name = "type")
    private String type;    // 一般指数据加工的类型，全量/增量； 日/月接口； 增量-P 全量-F

    @XmlAttribute(name = "isProvince")
    private boolean eProvince;  // 是否是省份 非必需

    @XmlElement(name = "source")
    private SourceFileSettings source;

    @XmlElement(name = "target")
    private TargetFileSettings target;

    @XmlElement(name = "data")
    private DataModel dataModel;


    public void validate() {
        if(ObjectUtils.isEmpty(source) || ObjectUtils.isEmpty(target) || ObjectUtils.isEmpty(dataModel)) {
            throw new RuntimeException("配置文件缺少必要的配置信息，阅读配置文档，检查配置文件配置。");
        }
//        源文件配置信息检查
        source.validate();
//        输出文件配置检查
        target.validate();
//        元数据结构检查
        dataModel.validate();
    }


    @Override
    public String getProcessId() {
        return this.id;
    }

    @Override
    public String getProcessType() {
        return this.type;
    }

    @Override
    public String getSourceFolderPath() {
        return this.source.getPath();
    }

    @Override
    public Charset getSourceFileCharset() {
        return ObjectUtils.isEmpty(this.source.getCharSet()) ? StandardCharsets.UTF_8 : Charset.forName(this.source.getCharSet());
    }

    @Override
    public String getSourceFileContentSeparator() {
        return this.source.getSeparator();
    }

    @Override
    public String getSourceFileExtension() {
        return ObjectUtils.isEmpty(this.source.getExtension()) ? ".gz" : this.source.getExtension();
    }

    @Override
    public String getTargetFolderPath() {
        return this.target.getPath();
    }

    @Override
    public String getTargetFileName() {
        return this.target.getName();
    }

    @Override
    public Charset getTargetFileCharset() {
        return ObjectUtils.isEmpty(this.target.getCharSet()) ? StandardCharsets.UTF_8 : Charset.forName(this.target.getCharSet());
    }

    private final long CAPACITY_1MB = 1048576;

    @Override
    public long getTargetFileMaxSize() {
        return (this.target.getMaxSize() <= 0) ? 10 * CAPACITY_1MB : this.target.getMaxSize() * CAPACITY_1MB;
    }

    @Override
    public DataModel getDataModel() {
        return this.dataModel;
    }

}
