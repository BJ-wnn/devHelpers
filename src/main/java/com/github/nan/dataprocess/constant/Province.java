package com.github.nan.dataprocess.constant;

/**
 * 省份常量
 */
public enum Province {
    NON("10000","全国"),
    BJ("10100","北京"),
    SH("10200","上海"),
    TJ("10300","天津"),
    CQ("10400","重庆"),
    GZ("10500","贵州"),
    HB("10600","湖北") ,
    SN("10700","陕西"),
    HE("10800","河北"),
    HA("10900","河南"),
    AH("11000","安徽"),
    FJ("11100","福建"),
    QH("11200","青海"),
    GS("11300","甘肃"),
    ZJ("11400","浙江"),
    HI("11500","海南"),
    HL("11600","黑龙江"),
    JS("11700","江苏"),
    JL("11800","吉林"),
    NX("11900","宁夏"),
    SD("12000","山东"),
    SX("12100","山西"),
    XJ("12200","新疆"),
    GD("12300","广东"),
    LN("12400","辽宁"),
    GX("12500","广西"),
    HN("12600","湖南"),
    JX("12700","江西"),
    IM("12800","内蒙古"),
    YN("12900","云南"),
    SC("13000","四川"),
    XZ("13100","西藏")
    ;

    private String code;
    private String name;


    private Province(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 传入省份编码，判断是否是合法的编码
     *
     * @param code
     * @return
     */
    public static boolean isValidProvinceCode(String code) {
        for (Province province : Province.values()) {
            if (province.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }
}
