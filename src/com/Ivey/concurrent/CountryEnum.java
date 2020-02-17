package com.Ivey.concurrent;

/**
 * @Description 枚举类，六国名称
 * @Author IveyLv
 * @Date 2019/11/21 11:27
 * @Version 1.0
 */
public enum CountryEnum {

    ONE(1, "齐"),
    TWO(2, "楚"),
    THREE(3, "燕"),
    FOUR(4, "赵"),
    FIVE(5, "魏"),
    SIX(6, "韩");

    private Integer code;
    private String country;

    CountryEnum(Integer code, String country) {
        this.code = code;
        this.country = country;
    }

    public Integer getCode() {
        return code;
    }

    public String getCountry() {
        return country;
    }

    public static CountryEnum forEachCountryEnum(int index) {
        CountryEnum[] countryEnums = CountryEnum.values();
        for (CountryEnum element : countryEnums) {
            if (element.getCode() == index) {
                return element;
            }
        }
        return null;
    }
}
