package com.springboot.sample.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.springboot.sample.bean.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Slf4j
@Service
public class CustomerServiceImpl {

    String filePath = "D:\\work\\excel\\temp\\顾问服务客户数据表201215.xlsx";
    File file = new File(filePath);



    public void readerCustomerAExcel() {
        ImportParams importParams = new ImportParams();
        importParams.setNeedSave(true);
        importParams.setTitleRows(0);
        importParams.setHeadRows(2);
        importParams.setStartSheetIndex(0);
        importParams.setSheetNum(1);
        List<CustomerService> objects = ExcelImportUtil.importExcel(file, CustomerService.class, importParams);
        log.info("size " + objects);

    }
    public static void main(String[] args) {
        CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();
        customerServiceImpl.readerCustomerAExcel();
    }
}
