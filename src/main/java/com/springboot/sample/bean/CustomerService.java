package com.springboot.sample.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class CustomerService {

    @Excel(name = "案件基本情况_序号")
    private String id;

    @Excel(name = "首次接触时间", importFormat = "yyyy/MM/dd")
    String date;

    @Excel(name = "最新跟进时间", importFormat = "yyyy/MM/dd")
    String latestFollowTime;

    @Excel(name = "最新更新内容")
    String latestUpdateContent;

    @Excel(name = "顾问")
    String adviser;

    @Excel(name = "客户名称")
    String customerName;

    @Excel(name = "微信号")
    String weChat;

    @Excel(name = "客户电话")
    String mobile;

    @Excel(name = "案件详情")
    String caseDetail;

    @Excel(name = "联系工具")
    String contactTools;

    @Excel(name = "客户来源")
    String customerOrigin;

    @Excel(name = "所属地区")
    String region;

    @Excel(name = "产品形态")
    String productForm;

    @Excel(name = "产品名称")
    String productName;

    @Excel(name = "争议标的")
    String dispute;

    @Excel(name = "案件状态")
    String caseStatus;

    @Excel(name = "下次跟进时间")
    String nextFollowTime;

    @Excel(name = "已成交_协议签署日期")
    String signDate;

    @Excel(name = "基础费用")
    String baseCost;

    @Excel(name = "代理费")
    String proxyCost;

    @Excel(name = "跟进中_跟进中案件状态")
    String caseStatusFollow;

    @Excel(name = "转化中_转化中案件状态")
    String caseStatusTransformation;


    @Excel(name = "专家律师评议_专家评议结果")
    String expertEvaluationResults;

    @Excel(name = "律师评议结果")
    String lawyerReview;

    @Excel(name = "失效_判定失效时状态")
    String isFailureState;

    @Excel(name = "失效原因")
    String failureReason;

    @Excel(name = "访客ID")
    String guestId;
}