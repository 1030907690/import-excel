package com.springboot.sample.controller;

import com.springboot.sample.bean.*;
import com.springboot.sample.service.CustomerServiceReaderServiceImpl;
import com.springboot.sample.service.MatchingServiceImpl;
import com.springboot.sample.service.ProcessFileService;
import com.springboot.sample.service.UserFlowReaderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
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
    /**
     * 上传文件
     */
    @RequestMapping("/import")
    public String importFile(@RequestParam("userFile") MultipartFile userFile, @RequestParam("customerFile") MultipartFile customerFile) {
        try {
            String[] filePaths = processFileService.uploadFile(userFile, customerFile);
            Map<String, List<CustomerService>> group = customerServiceReaderServiceImpl.group(customerServiceReaderServiceImpl.readerCustomerExcel(filePaths[1]));
            // 美洽数据
            List<UserFlowMeiQia> userFlowMeiQiaList = userFlowReaderServiceImpl.readerUserFlowMeiQiaExcel(filePaths[0]);
            List<UserFlowMeiQiaWrapper> userFlowMeiQiaWrapperList = matchingService.meiQiaMatch(userFlowMeiQiaList, group);

            // 400数据
            List<UserFlowFour> userFlowFourList = userFlowReaderServiceImpl.readerUserFlowFourExcel(filePaths[0]);
            List<UserFlowFourWrapper> userFlowFourWrapperList = matchingService.fourMatch(userFlowFourList,group);

            // 电话A客
            List<UserFlowCallA> userFlowCallAList = userFlowReaderServiceImpl.readerUserFlowCallAExcel(filePaths[0]);
            List<UserFlowCallAWrapper> userFlowCallAWrapperList = matchingService.callAMatch(userFlowCallAList, group);


            //待处理
            List<?> waitHandlerList = matchingService.waitHandler(userFlowMeiQiaWrapperList, userFlowFourWrapperList, userFlowCallAWrapperList);


            log.info("1111");
            return "index";
        } catch (Exception e) {
            System.out.println("异常");
            return "index";
        }

    }



}
