package com.springboot.sample.controller;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.springboot.sample.bean.*;
import com.springboot.sample.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

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

    /**
     * 上传文件
     */
    @RequestMapping("/import")
    public String importFile(@RequestParam("userFile") MultipartFile userFile, @RequestParam("customerFile") MultipartFile customerFile) {
        try {
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

            log.info("1111");
            return "index";
        } catch (Exception e) {
            System.out.println("异常");
            return "index";
        }

    }


}
