package com.springboot.sample.bean;

import lombok.Data;

/**
 * @author pn20120162
 */
@Data
public class ResultVo {

    private Integer code;

    private Boolean success;

    private String data;

    private String description;

    public ResultVo fail(String description){
        this.description = description;
        this.success = Boolean.FALSE;
        return this;
    }
}
