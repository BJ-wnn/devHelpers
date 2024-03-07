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
public class TargetFileSettings {

    @XmlAttribute(name = "path")
    private String path;    //文件路径
    @XmlElement(name = "name")
    private String name;    //文件名称
    @XmlElement(name = "charSet")
    private String charSet; //文件编码
    @XmlElement(name = "maxSize")
    private long maxSize = 0;   //文件大小限制


    public void validate() {
        if(ObjectUtils.isEmpty(path) || ObjectUtils.isEmpty(name)) {
            throw new RuntimeException("配置文件中，输出（结果）文件信息配置错误，查看说明，检查配置文件内容！");
        }
    }

}
