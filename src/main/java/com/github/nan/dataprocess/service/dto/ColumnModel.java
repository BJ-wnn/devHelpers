package com.github.nan.dataprocess.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.Arrays;

/**
 * @author NanNan Wang
 * @date 2023/12/21
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ColumnModel {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "javaType")
    private String javaType = "String";

    // 只有数组才使用这个属性哈，可能需要对字符串进一步处理
    @XmlAttribute(name = "separator")
    private String separator;

//    private String itemJavaType;


    public String getName() {
        return name;
    }

    public String getJavaType() {
        return javaType;
    }

    public String getSeparator() {
        return separator;
    }

    public Object processData(String data) {
        switch (javaType) {
            case "String":
                return processString(data);
            case "Collection":
                return processCollection(data);
            // Add more cases as needed
            default:
                throw new IllegalArgumentException("Unsupported javaType: " + javaType);
        }
    }

    private String processString(String data) {
        return data.trim();
    }

    private Object processCollection(String data) {
        if (separator != null && !separator.isEmpty()) {
            String[] elements = data.split(separator);
            return elements;
        } else {
            return Arrays.asList(data);
        }
    }

}