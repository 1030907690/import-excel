package com.springboot.sample.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.springboot.sample.bean.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CustomerServiceReaderServiceImpl {

    String filePath = "D:\\work\\excel\\temp\\顾问服务客户数据表201215.xlsx";
    File file = new File(filePath);


    public List<CustomerService> readerCustomerAExcel() {
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(0);
        importParams.setHeadRows(2);
        importParams.setStartSheetIndex(0);
        importParams.setSheetNum(1);
        List<CustomerService> objects = ExcelImportUtil.importExcel(file, CustomerService.class, importParams);
        log.info("size " + objects.size());
        return objects;
    }


    public Map<String, List<CustomerService>> group(List<CustomerService> objects) {
        Map<String, List<CustomerService>> result = new HashMap<>();
        objects.forEach((v -> {
            List<CustomerService> customerServiceList = result.getOrDefault(v.getCustomerOrigin(), null);
            if (null == customerServiceList) {
                customerServiceList = new ArrayList<>();
                customerServiceList.add(v);
                result.put(v.getCustomerOrigin(), customerServiceList);
            } else {
                customerServiceList.add(v);
            }
        }));
        log.info("分组size:" + result.size());
        return result;
    }

    public static void main(String[] args) {
        CustomerServiceReaderServiceImpl customerServiceImpl = new CustomerServiceReaderServiceImpl();
        customerServiceImpl.group(customerServiceImpl.readerCustomerAExcel());
    }
}
