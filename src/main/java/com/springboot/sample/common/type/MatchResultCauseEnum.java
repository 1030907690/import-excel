package com.springboot.sample.common.type;

import java.util.Arrays;
import java.util.Optional;

/**
 * zzq
 * 2020年12月18日15:34:03
 * 匹配失败原因类型
 * */
public enum MatchResultCauseEnum {

    SUCCESS("SUCCESS", ""),
    NOT_FOUND("NOT_FOUND", ""),
    DUPLICATE("DUPLICATE", "数据重复");


    private String code;
    private String name;

    MatchResultCauseEnum(String code, String name) {
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

    public static MatchResultCauseEnum convert(String code) {
        Optional<MatchResultCauseEnum> type = Arrays.stream(values()).filter(v -> v.code.equals(code)).findFirst();
        return type.orElse(null);
    }
}
