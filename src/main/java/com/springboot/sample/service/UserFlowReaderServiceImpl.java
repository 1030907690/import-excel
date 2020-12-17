package com.springboot.sample.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.springboot.sample.bean.UserFlowCallA;
import com.springboot.sample.bean.UserFlowFour;
import com.springboot.sample.bean.UserFlowMeiQia;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class UserFlowReaderServiceImpl {

    String filePath = "D:\\work\\excel\\temp\\用户全流程服务跟踪表模板 12.16（终）.xlsx";
    File file = new File(filePath);

    public void readerUserFlowMeiQiaExcel() {
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(0);
        importParams.setHeadRows(2);
        importParams.setStartSheetIndex(0);
        importParams.setSheetNum(1);
        List<UserFlowMeiQia> userObjects = ExcelImportUtil.importExcel(file, UserFlowMeiQia.class, importParams);
        System.out.println(userObjects.size());
    }

    public void readerUserFlowFourExcel() {
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(0);
        importParams.setHeadRows(2);
        importParams.setStartSheetIndex(1);
        importParams.setSheetNum(1);
        List<UserFlowFour> userObjects = ExcelImportUtil.importExcel(file, UserFlowFour.class, importParams);
        System.out.println(userObjects.size());
    }


    public void readerUserFlowCallAExcel() {
        ImportParams importParams = new ImportParams();
        importParams.setTitleRows(0);
        importParams.setHeadRows(2);
        importParams.setStartSheetIndex(2);
        importParams.setSheetNum(1);
        List<UserFlowCallA> userObjects = ExcelImportUtil.importExcel(file, UserFlowCallA.class, importParams);
        log.info("size " + userObjects);
    }


    public static void main(String[] args) {
        UserFlowReaderServiceImpl userFlowServiceImpl = new UserFlowReaderServiceImpl();
        userFlowServiceImpl.readerUserFlowCallAExcel();
    }
}
