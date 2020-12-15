package com.springboot.sample.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.springboot.sample.bean.CustomerService;
import com.springboot.sample.bean.UserFourPo;
import com.springboot.sample.bean.UserMeiQiaPo;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reader {

    public static List<UserMeiQiaPo> meiQia() {
        File file = new File("D:\\work\\excel\\temp\\用户全流程服务跟踪表（日报）2.xlsx");
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(0);
        importParams.setHeadRows(2);
        importParams.setSheetNum(1);
        List<UserMeiQiaPo> userObjects = ExcelImportUtil.importExcel(file, UserMeiQiaPo.class, importParams);

       /* for (UserMeiQiaPo userObject : userObjects) {
            System.out.println(userObject.getDate() + "---" + userObject.getGuestId());
        }*/
        return userObjects;
    }


    public static List<UserFourPo> four() {
        File file = new File("D:\\work\\excel\\temp\\用户全流程服务跟踪表（日报）3.xlsx");
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(0);
        importParams.setHeadRows(2);
        importParams.setSheetNum(1);
        List<UserFourPo> userObjects = ExcelImportUtil.importExcel(file, UserFourPo.class, importParams);
        return userObjects;
    }

    public static List<CustomerService> customerService() {
        File file = new File("D:\\work\\excel\\temp\\顾问服务客户数据表201214.xlsx");
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(0);
        importParams.setHeadRows(1);
        importParams.setSheetNum(1);
        List<CustomerService> userObjects = ExcelImportUtil.importExcel(file, CustomerService.class, importParams);
        return userObjects;
    }

    public static List<Map<String, CustomerService>> transferMap(List<CustomerService> customerServices) {
        List<Map<String, CustomerService>> result = new ArrayList<>();
        Map<String, CustomerService> resultGuest = new HashMap<>();
        Map<String, CustomerService> resultMobile = new HashMap<>();
        for (CustomerService customerService : customerServices) {
            if (StringUtils.isNotBlank(customerService.getGuestId())) {
                resultGuest.put(customerService.getGuestId(), customerService);
            }
            if (StringUtils.isNotBlank(customerService.getMobile())) {
                resultMobile.put(customerService.getMobile(), customerService);
            }
        }
        result.add(resultGuest);
        result.add(resultMobile);
        return result;
    }



    public static void main(String[] args) {
        List<CustomerService> customerServices = customerService();
        List<Map<String, CustomerService>> maps = transferMap(customerServices);
        Map<String, CustomerService> resultGuest = maps.get(0); // 访客id
        Map<String, CustomerService> resultMobile = maps.get(1); // 手机号


        List<UserMeiQiaPo> userMeiQiaPos = meiQia();
        int meiQiaMatchLength = 0;
        for (UserMeiQiaPo userMeiQiaPo : userMeiQiaPos) {
            CustomerService customerService = resultGuest.getOrDefault(userMeiQiaPo.getGuestId(), null);
            if (null != customerService){
                meiQiaMatchLength++;
            }
        }
        System.out.println("匹配到的" +meiQiaMatchLength);


        List<UserFourPo> four = four();
        int fourMatchLength = 0;
        for (UserFourPo userFourPo : four) {
            CustomerService customerService = resultMobile.getOrDefault(userFourPo.getMobile(), null);
            if (null != customerService){
                fourMatchLength++;
            }
        }
        System.out.println("匹配到的" +fourMatchLength);
    }

}
