package com.springboot.sample.bean;


import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class UserFlowCallAWrapper extends UserFlowCallA {
    private MatchResult matchResult;


}
