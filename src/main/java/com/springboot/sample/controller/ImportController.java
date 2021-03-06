package com.springboot.sample.controller;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.springboot.sample.bean.*;
import com.springboot.sample.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

@Slf4j
@Controller
public class ImportController {

    @Resource
    private MatchingServiceImpl matchingService;

    @Resource
    private UserFlowReaderServiceImpl userFlowReaderServiceImpl;

    @Resource
    private CustomerServiceReaderServiceImpl customerServiceReaderServiceImpl;

    @Resource
    private ProcessFileService processFileService;

    @Resource
    private ExecutorService executorService;


    @Resource
    private ParseDataService parseDataService;

    @Resource
    private UserFlowExportServiceImpl userFlowExportServiceImpl;

    @Value(value = "${excel.template}")
    private String excelTemplate;

    @Resource
    private ValidateService validateService;

    @Resource
    private UserFlowExportServiceWrapperImpl userFlowExportServiceWrapperImpl;

    @RequestMapping("/importVue")
    @ResponseBody
    public ResultVo importFileVue(@RequestParam("files") MultipartFile[] files) {
        ResultVo result = validateService.doValidate(files);
        if (result.getSuccess()) {
            String[] filePaths = new String[0];
            try {
                filePaths = processFileService.uploadFile(files[0], files[1]);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (filePaths.length == 2) {
                Map<String, List<CustomerService>> group = customerServiceReaderServiceImpl.group(customerServiceReaderServiceImpl.readerCustomerExcel(filePaths[1]));

                Object[] parseDataObject = parseDataService.doParseData(filePaths[0], group);
                // 美洽数据
                List<UserFlowMeiQiaWrapper> userFlowMeiQiaWrapperList = (List<UserFlowMeiQiaWrapper>) parseDataObject[0];
                // 400数据
                List<UserFlowFourWrapper> userFlowFourWrapperList = (List<UserFlowFourWrapper>) parseDataObject[1];

                // 电话A客数据
                List<UserFlowCallAWrapper> userFlowCallAWrapperList = (List<UserFlowCallAWrapper>) parseDataObject[2];
                //待处理
                List<CustomerService> waitHandlerList = matchingService.waitHandler(userFlowMeiQiaWrapperList, userFlowFourWrapperList, userFlowCallAWrapperList);

                try {
                    String downloadUrl = userFlowExportServiceWrapperImpl.userFlowExportExcel(excelTemplate, userFlowMeiQiaWrapperList, userFlowFourWrapperList, userFlowCallAWrapperList, waitHandlerList);
                    result.setSuccess(Boolean.TRUE);
                    result.setData(downloadUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                result.fail("上传文件失败!");
            }
        }
        return result;
    }

    /**
     * 上传文件
     */
    @RequestMapping("/import")
    public void importFile(@RequestParam("userFile") MultipartFile userFile, @RequestParam("customerFile") MultipartFile customerFile, HttpServletResponse response) {
        try {
            log.info("userFile  " + userFile.getOriginalFilename() + "  customerFile " + customerFile.getOriginalFilename());
            String[] filePaths = processFileService.uploadFile(userFile, customerFile);
            Map<String, List<CustomerService>> group = customerServiceReaderServiceImpl.group(customerServiceReaderServiceImpl.readerCustomerExcel(filePaths[1]));

            Object[] parseDataObject = parseDataService.doParseData(filePaths[0], group);
            // 美洽数据
            List<UserFlowMeiQiaWrapper> userFlowMeiQiaWrapperList = (List<UserFlowMeiQiaWrapper>) parseDataObject[0];
            // 400数据
            List<UserFlowFourWrapper> userFlowFourWrapperList = (List<UserFlowFourWrapper>) parseDataObject[1];

            // 电话A客数据
            List<UserFlowCallAWrapper> userFlowCallAWrapperList = (List<UserFlowCallAWrapper>) parseDataObject[2];
            //待处理
            List<CustomerService> waitHandlerList = matchingService.waitHandler(userFlowMeiQiaWrapperList, userFlowFourWrapperList, userFlowCallAWrapperList);
            Workbook workbook = userFlowExportServiceImpl.userFlowExportExcel(excelTemplate, userFlowMeiQiaWrapperList, userFlowFourWrapperList, userFlowCallAWrapperList, waitHandlerList);

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String formatDateTime = now.format(formatter);
            response.setHeader("Content-Disposition", "attachment;filename=" + formatDateTime + ".xlsx");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(response.getOutputStream());
            workbook.write(bufferedOutPut);
            bufferedOutPut.flush();
            bufferedOutPut.close();
            log.info("完成");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
