package com.springboot.sample.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.springboot.sample.bean.CustomerServicePo;
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

    public static List<CustomerServicePo> customerService() {
        File file = new File("D:\\work\\excel\\temp\\顾问服务客户数据表201214.xlsx");
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(0);
        importParams.setHeadRows(1);
        importParams.setSheetNum(1);
        List<CustomerServicePo> userObjects = ExcelImportUtil.importExcel(file, CustomerServicePo.class, importParams);
        return userObjects;
    }

    public static List<Map<String, CustomerServicePo>> transferMap(List<CustomerServicePo> customerServicePos) {
        List<Map<String, CustomerServicePo>> result = new ArrayList<>();
        Map<String, CustomerServicePo> resultGuest = new HashMap<>();
        Map<String, CustomerServicePo> resultMobile = new HashMap<>();
        for (CustomerServicePo customerServicePo : customerServicePos) {
            if (StringUtils.isNotBlank(customerServicePo.getGuestId())) {
                resultGuest.put(customerServicePo.getGuestId(), customerServicePo);
            }
            if (StringUtils.isNotBlank(customerServicePo.getMobile())) {
                resultMobile.put(customerServicePo.getMobile(), customerServicePo);
            }
        }
        result.add(resultGuest);
        result.add(resultMobile);
        return result;
    }



    public static void main(String[] args) {
        List<CustomerServicePo> customerServicePos = customerService();
        List<Map<String, CustomerServicePo>> maps = transferMap(customerServicePos);
        Map<String, CustomerServicePo> resultGuest = maps.get(0); // 访客id
        Map<String, CustomerServicePo> resultMobile = maps.get(1); // 手机号


        List<UserMeiQiaPo> userMeiQiaPos = meiQia();
        int meiQiaMatchLength = 0;
        for (UserMeiQiaPo userMeiQiaPo : userMeiQiaPos) {
            CustomerServicePo customerServicePo = resultGuest.getOrDefault(userMeiQiaPo.getGuestId(), null);
            if (null != customerServicePo){
                meiQiaMatchLength++;
            }
        }
        System.out.println("匹配到的" +meiQiaMatchLength);


        List<UserFourPo> four = four();
        int fourMatchLength = 0;
        for (UserFourPo userFourPo : four) {
            CustomerServicePo customerServicePo = resultMobile.getOrDefault(userFourPo.getMobile(), null);
            if (null != customerServicePo){
                fourMatchLength++;
            }
        }
        System.out.println("匹配到的" +fourMatchLength);
    }

}
