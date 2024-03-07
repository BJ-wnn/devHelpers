package com.github.nan.dataprocess.service.dto;

import com.alibaba.fastjson.JSON;
import com.github.nan.dataprocess.service.DataProcess;
import org.apache.commons.lang3.ObjectUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NanNan Wang
 * @date 2023/12/21
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DataModel implements DataProcess {


    @XmlElement(name = "column")
    private List<ColumnModel> columns;

    @XmlElement(name = "object")
    private List<ObjectModel> objects;

    public void validate() {
        if(ObjectUtils.isEmpty(columns) && ObjectUtils.isEmpty(objects)) {
            throw new RuntimeException("配置文件中，元数据结构信息无配置信息，查看说明，检查配置文件内容！");
        }
    }


    @Override
    public String toJsonStr(List<String> inputString) {
        Map<String,Object> resultMap = new HashMap<>(inputString.size());
        //        inputString的索引
        int index = 0;
        for (ColumnModel column : this.columns) {
            String javaType = column.getJavaType();
            resultMap.put(column.getName(), column.processData(inputString.get(index++)));
        }
        for(ObjectModel objectModel : this.objects) {
            Map<String,Object> innerMap = new HashMap<>(objectModel.getColumns().size());
            for(ColumnModel innerColumn : objectModel.getColumns()) {
                innerMap.put(innerColumn.getName(),innerColumn.processData(inputString.get(index++)));
            }
            resultMap.put(objectModel.getName(),innerMap);
        }
        return JSON.toJSONString(resultMap);
    }



    public List<ColumnModel> getColumns() {
        return columns;
    }

    public List<ObjectModel> getObjects() {
        return objects;
    }


    private Integer cachedFieldCount; //缓存一下

    /**
     * 模型的字段数目。
     *
     * 考虑了下，目前我要在多线程情况下调用，所以要考虑一下线程安全的问题。
     *
     * @return
     */
    public int countFields() {

        if (cachedFieldCount == null) {
            synchronized (this) {
                if (cachedFieldCount == null) {
                    int fieldCount = 0;

                    if (columns != null) {
                        fieldCount += columns.size();
                    }

                    if (objects != null) {
                        for (ObjectModel objectModel : objects) {
                            fieldCount += objectModel.getColumns().size();
                        }
                    }

                    cachedFieldCount = fieldCount; // Cache the result
                }
            }
        }

        return cachedFieldCount;
    }
}
