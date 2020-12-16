package com.springboot.sample.bean;


import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class UserFlowFour {

    @Excel(name = "序号")
    private String id;

    @Excel(name = "投放部分_日期", importFormat = "yyyy/MM/dd")
    private String date;

    @Excel(name = "预约人姓名")
    private String appointmentName;

    @Excel(name = "手机号")
    private String mobile;

    @Excel(name = "预约人微信号")
    private String appointmentWeChat;

    @Excel(name = "索赔类型")
    private String claimType;




}
