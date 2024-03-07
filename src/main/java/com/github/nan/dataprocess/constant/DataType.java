package com.github.nan.dataprocess.constant;

/**
 * 字段类型的常量，主要是和xml文件一一映射哈，最好忽略大小写，业务人员常常忽略大小写...
 *
 */
public enum DataType {

    String,
    Collection;


    /**
     * todo 其实可以写个Map缓存下，但是这里没必要，先这样吧
     *
     * @param type
     * @return
     */
    public static DataType ofValue(String type) {
        for (DataType dataType : DataType.values()) {
            if (dataType.name().equalsIgnoreCase(type)) {
                return dataType;
            }
        }
        throw new IllegalArgumentException("Unsupported data type: " + type);
    }
}
