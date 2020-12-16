package com.springboot.sample.bean;


import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class UserFlowMeiQia {

    @Excel(name = "序号")
    private String id;

    @Excel(name = "投放部分_日期", importFormat = "yyyy/MM/dd")
    private String date;

    @Excel(name = "对话ID")
    private String talkId;

    @Excel(name = "访客ID")
    private String guestId;

    @Excel(name = "有效对话")
    private String effectiveTalk;

    @Excel(name = "遗漏对话")
    private String missingDialogue;

    @Excel(name = "对话分级")
    private String dialogueGrading;

    @Excel(name = "小结")
    private String summary;

    @Excel(name = "客服姓名")
    private String customerName;

    @Excel(name = "小结时间")
    private String summaryOfTime;


    @Excel(name = "地理位置")
    private String position;

    @Excel(name = "姓名")
    private String name;

    @Excel(name = "年龄")
    private String age;

    @Excel(name = "性别")
    private String sex;

    @Excel(name = "电话")
    private String mobile;

    @Excel(name = "QQ")
    private String qq;

    @Excel(name = "微信")
    private String weChat;

    @Excel(name = "微博")
    private String sina;

    @Excel(name = "地址")
    private String address;

    @Excel(name = "邮箱")
    private String email;

    @Excel(name = "联系人")
    private String contact;

    @Excel(name = "备注")
    private String remarks;

    @Excel(name = "客服工号")
    private String customerServiceNo;

    @Excel(name = "客服昵称")
    private String customerServiceNickName;

    @Excel(name = "客服真实姓名")
    private String customerServiceRealName;

    @Excel(name = "客服邮箱")
    private String customerServiceEmail;


    @Excel(name = "顾客标签")
    private String customerTag;

    @Excel(name = "对话标签一级")
    private String talkTagOneLevel;

    @Excel(name = "对话标签二级")
    private String talkTagTwoLevel;

    @Excel(name = "对话标签三级")
    private String talkTagThreeLevel;

    @Excel(name = "线索")
    private String clue;

    @Excel(name = "关键词")
    private String keyWord;

    @Excel(name = "来源")
    private String origin;

    @Excel(name = "来源取值")
    private String originValue;

    @Excel(name = "来源URL")
    private String originUrl;

    @Excel(name = "着陆页标题")
    private String landingPageTitle;

    @Excel(name = "着陆页URL")
    private String landingPageUrl;

    @Excel(name = "对话页标题")
    private String talkPageTitle;

    @Excel(name = "对话页URL")
    private String talkPageUrl;
    @Excel(name = "搜索引擎")
    private String searchEngine;

    @Excel(name = "操作系统")
    private String operationSystem;

    @Excel(name = "浏览器")
    private String browser;
    @Excel(name = "IP地址")
    private String ip;

    @Excel(name = "接入响应时长")
    private String accessResponseTime;

    @Excel(name = "首次响应时长")
    private String firstResponseTime;

    @Excel(name = "对话持续时长")
    private String talkContinueTime;

    @Excel(name = "对话创建时间")
    private String talkCreateTime;

    @Excel(name = "对话结束时间")
    private String talkEndTime;

    @Excel(name = "顾客消息数")
    private String customerMessageCount;

    @Excel(name = "客服消息数")
    private String customerServiceMessageCount;

    @Excel(name = "评价")
    private String comment;

    @Excel(name = "评价内容")
    private String commentContent;

    @Excel(name = "平台类型")
    private String platformType;

    @Excel(name = "自定义渠道(utm_source)")
    private String customChannel;

    @Excel(name = "utm_medium")
    private String utmMedium;

    @Excel(name = "utm_term")
    private String utmTerm;

    @Excel(name = "utm_content")
    private String utmContent;

    @Excel(name = "utm_campaign")
    private String utmCampaign;

    @Excel(name = "h")
    private String h;

    @Excel(name = "顾问服务部分_是否有效咨询")
    private String isConsultationEffective;

    @Excel(name = "对话结果（下拉选择）")
    private String dialogueResults;

    @Excel(name = "是否推送用户或顾问微信")
    private String isPush;
    @Excel(name = "是否成功添加微信")
    private String isAddWeChat;



}
