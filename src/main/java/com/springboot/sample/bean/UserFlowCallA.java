package com.springboot.sample.bean;


import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class UserFlowCallA {

    @Excel(name = "序号")
    private String id;

    @Excel(name = "电销部分_日期", importFormat = "yyyy/MM/dd")
    private String date;

    @Excel(name = "姓名")
    private String name;


    @Excel(name = "手机号")
    private String mobile;



}
