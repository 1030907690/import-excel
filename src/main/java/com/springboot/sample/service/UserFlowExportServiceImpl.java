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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class UserFlowExportServiceImpl {

    private String templateUrl = "D:\\work\\excel\\temp\\用户全流程服务跟踪表模板 12.16（终）template.xlsx";

    public Workbook userFlowExportExcel(String templateUrl, List<UserFlowMeiQiaWrapper> userFlowMeiQiaWrapperList, List<UserFlowFourWrapper> userFlowFourWrapperList, List<UserFlowCallAWrapper> userFlowCallAWrapperList, List<CustomerService> waitHandlerList) throws IOException {
        TemplateExportParams params = new TemplateExportParams(templateUrl);
        params.setSheetNum(new Integer[]{0, 1, 2, 3});
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> meiQiaListMap = new ArrayList<>();
        meiQiaSheetSetPropertyValues(meiQiaListMap, userFlowMeiQiaWrapperList);
        map.put("meiQiaList", meiQiaListMap);

        List<Map<String, Object>> fourListMap = new ArrayList<>();
        fourSheetSetPropertyValues(fourListMap, userFlowFourWrapperList);
        map.put("fourList", fourListMap);


        List<Map<String, Object>> callAListMap = new ArrayList<>();
        callASheetSetPropertyValues(callAListMap, userFlowCallAWrapperList);
        map.put("callAList", callAListMap);

        List<Map<String, Object>> waitHandlerListMap = new ArrayList<>();
        waitHandlerSheetSetPropertyValues(waitHandlerListMap, waitHandlerList);
        map.put("waitHandlerList", waitHandlerListMap);

        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
       /* File file = new File(System.getProperty("user.dir"), "out");
        if (!file.exists()) {
            file.mkdir();
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formatDateTime = now.format(formatter);
        String filePath = file.getPath() + File.separator + formatDateTime + ".xlsx";
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();*/
        return workbook;
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
        UserFlowExportServiceImpl userFlowExportServiceImpl = new UserFlowExportServiceImpl();
        try {
            userFlowExportServiceImpl.userFlowExportExcel(userFlowExportServiceImpl.templateUrl, null, null, null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}