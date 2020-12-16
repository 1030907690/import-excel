package com.springboot.sample.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class CustomerServicePo {

    @Excel(name = "序号")
    private String id;

    @Excel(name = "首次接触时间",importFormat = "yyyy/MM/dd")
    String date;


    @Excel(name = "客户电话")
    String mobile;


    @Excel(name = "访客ID")
    String guestId;




}