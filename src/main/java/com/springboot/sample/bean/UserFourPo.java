package com.springboot.sample.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class UserFourPo {
    @Excel(name = "投放部分_日期",importFormat = "yyyy/MM/dd")
    String date;

    @Excel(name = "手机号")
    String mobile;

}
