package com.github.nan.dataprocess.service.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * @author NanNan Wang
 * @date 2023/12/21
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ObjectModel {

    @XmlAttribute
    private String name;

    @XmlElement(name = "column")
    private List<ColumnModel> columns;
//    private List<ObjectConfig> objects;   //递归的话先不考虑了 业务没需求 后面扩展
}
