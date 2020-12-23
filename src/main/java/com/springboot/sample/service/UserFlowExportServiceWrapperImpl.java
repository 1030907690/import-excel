package com.springboot.sample.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.alibaba.fastjson.JSONObject;
import com.springboot.sample.bean.CustomerService;
import com.springboot.sample.bean.UserFlowCallAWrapper;
import com.springboot.sample.bean.UserFlowFourWrapper;
import com.springboot.sample.bean.UserFlowMeiQiaWrapper;
import com.springboot.sample.common.type.MatchResultCauseEnum;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserFlowExportServiceWrapperImpl {

    @Resource
    private UserFlowExportServiceImpl userFlowExportServiceImpl;
    private String templateUrl = "D:\\work\\excel\\temp\\用户全流程服务跟踪表模板 12.16（终）template.xlsx";

    @Value("${excel.file.path.prefix}")
    private String excelFilePathPrefix;

    @Value("${excel.download.url.prefix}")
    private String excelDownloadUrlPrefix;

    public String userFlowExportExcel(String templateUrl, List<UserFlowMeiQiaWrapper> userFlowMeiQiaWrapperList, List<UserFlowFourWrapper> userFlowFourWrapperList, List<UserFlowCallAWrapper> userFlowCallAWrapperList, List<CustomerService> waitHandlerList) throws IOException {
        Workbook workbook = userFlowExportServiceImpl.userFlowExportExcel(templateUrl, userFlowMeiQiaWrapperList, userFlowFourWrapperList, userFlowCallAWrapperList, waitHandlerList);
        File file = new File(excelFilePathPrefix, "out");
        if (!file.exists()) {
            file.mkdir();
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formatDateTime = now.format(formatter);
        String fileName = formatDateTime + ".xlsx";
        String filePath = file.getPath() + File.separator + fileName;
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
        return excelDownloadUrlPrefix + fileName;
    }

    private void waitHandlerSheetSetPropertyValues(List<Map<String, Object>> waitHandlerListMap, List<CustomerService> waitHandlerList) {
        for (CustomerService waitHandler : waitHandlerList) {
            Map<String, Object> object = JSONObject.parseObject(JSONObject.toJSONString(waitHandler), Map.class);
            waitHandlerListMap.add(object);
        }
    }

    private void callASheetSetPropertyValues(List<Map<String, Object>> callAListMap, List<UserFlowCallAWrapper> userFlowCallAWrapperList) {
        for (UserFlowCallAWrapper userFlowCallAWrapper : userFlowCallAWrapperList) {
            if (MatchResultCauseEnum.SUCCESS == userFlowCallAWrapper.getMatchResult().getMatchResultCauseEnum()) {
                Map<String, Object> object = JSONObject.parseObject(JSONObject.toJSONString(userFlowCallAWrapper), Map.class);
                callAListMap.add(object);
            }
        }
    }

    private void fourSheetSetPropertyValues(List<Map<String, Object>> fourListMap, List<UserFlowFourWrapper> userFlowFourWrapperList) {
        for (UserFlowFourWrapper userFlowFourWrapper : userFlowFourWrapperList) {
            if (MatchResultCauseEnum.SUCCESS == userFlowFourWrapper.getMatchResult().getMatchResultCauseEnum()) {
                Map<String, Object> object = JSONObject.parseObject(JSONObject.toJSONString(userFlowFourWrapper), Map.class);
                fourListMap.add(object);
            }
        }
    }

    private void meiQiaSheetSetPropertyValues(List<Map<String, Object>> meiQiaListMap, List<UserFlowMeiQiaWrapper> userFlowMeiQiaWrapperList) {
        for (UserFlowMeiQiaWrapper userFlowMeiQiaWrapper : userFlowMeiQiaWrapperList) {
            if (MatchResultCauseEnum.SUCCESS == userFlowMeiQiaWrapper.getMatchResult().getMatchResultCauseEnum()) {
                Map<String, Object> object = JSONObject.parseObject(JSONObject.toJSONString(userFlowMeiQiaWrapper), Map.class);
                meiQiaListMap.add(object);
            }
        }
    }


    public static void main(String[] args) {
        UserFlowExportServiceWrapperImpl userFlowExportServiceImpl = new UserFlowExportServiceWrapperImpl();
        try {
            userFlowExportServiceImpl.userFlowExportExcel(userFlowExportServiceImpl.templateUrl, null, null, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}