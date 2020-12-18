package com.springboot.sample.common.type;

import java.util.Arrays;
import java.util.Optional;


/***
 * 客户来源
 * */
public enum CustomerOriginEnum {

    MEI_QIA_ONLINE("美洽", "美洽在线"),
    FOUR_HUNDRED("400", "400"),
    CALL_A("电话A客", "电话A客");

    private String code;
    private String name;

    CustomerOriginEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CustomerOriginEnum convert(Integer code) {
        Optional<CustomerOriginEnum> type = Arrays.stream(values()).filter(v -> v.code.equals(code)).findFirst();
        return type.orElse(null);
    }


}