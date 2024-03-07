package com.github.nan.dataprocess.service.dto;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author NanNan Wang
 * @date 2023/12/21
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceFileSettings {

    @XmlAttribute(name = "path")
    private String path;    //路径
    @XmlElement(name = "charSet")
    private String charSet; //文件编码
    @XmlElement(name = "contentSeparator")
    private String separator;   //文件内容分隔符
    @XmlElement(name = "extension")
    private String extension;   //文件扩展名 例如.gz


    public void validate() {
        if(ObjectUtils.isEmpty(path) || ObjectUtils.isEmpty(separator)) {
            throw new RuntimeException("配置文件源文件信息配置错误，查看配置文件和说明！");
        }
    }
}
