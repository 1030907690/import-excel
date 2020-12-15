package com.springboot.sample.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class UserMeiQiaPo {


    @Excel(name = "投放部分_日期",importFormat = "yyyy/MM/dd")
    String date;

    @Excel(name = "对话ID")
    String talkId;

    @Excel(name = "访客ID")
    String guestId;
}
