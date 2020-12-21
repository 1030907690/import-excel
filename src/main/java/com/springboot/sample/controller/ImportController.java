package com.springboot.sample.controller;

import cn.afterturn.easypoi.excel.annotation.Excel;
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


    /**
     * 上传文件
     */
    @RequestMapping("/import")
    public String importFile(@RequestParam("userFile") MultipartFile userFile, @RequestParam("customerFile") MultipartFile customerFile) {
        try {
            String[] filePaths = processFileService.uploadFile(userFile, customerFile);
            Map<String, List<CustomerService>> group = customerServiceReaderServiceImpl.group(customerServiceReaderServiceImpl.readerCustomerExcel(filePaths[1]));

            Object[] parseDataObject = parseData(filePaths[0], group);
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


    private Object[] parseData(String filePath, Map<String, List<CustomerService>> group) {
        Object [] result = new Object[]{new ArrayList<UserFlowMeiQiaWrapper>(),new ArrayList<UserFlowFourWrapper>(),new ArrayList<UserFlowCallAWrapper>()};
        FutureTask<List<UserFlowMeiQiaWrapper>> futureTaskMeiQia = new FutureTask<>(new Callable() {
            @Override
            public List<UserFlowMeiQiaWrapper> call() throws Exception {
                List<UserFlowMeiQia> userFlowMeiQiaList = userFlowReaderServiceImpl.readerUserFlowMeiQiaExcel(filePath);
                List<UserFlowMeiQiaWrapper> userFlowMeiQiaWrapperList = matchingService.meiQiaMatch(userFlowMeiQiaList, group);
                return userFlowMeiQiaWrapperList;
            }
        });


        FutureTask<List<UserFlowFourWrapper>> futureTaskFour = new FutureTask<>(new Callable<List<UserFlowFourWrapper>>() {
            @Override
            public List<UserFlowFourWrapper> call() throws Exception {
                List<UserFlowFour> userFlowFourList = userFlowReaderServiceImpl.readerUserFlowFourExcel(filePath);
                List<UserFlowFourWrapper> userFlowFourWrapperList = matchingService.fourMatch(userFlowFourList, group);
                return userFlowFourWrapperList;
            }
        });


        FutureTask<List<UserFlowCallAWrapper>> futureTaskCallA = new FutureTask<>(new Callable<List<UserFlowCallAWrapper>>() {
            @Override
            public List<UserFlowCallAWrapper> call() throws Exception {
                List<UserFlowCallA> userFlowCallAList = userFlowReaderServiceImpl.readerUserFlowCallAExcel(filePath);
                List<UserFlowCallAWrapper> userFlowCallAWrapperList = matchingService.callAMatch(userFlowCallAList, group);
                return userFlowCallAWrapperList;
            }
        });

        executorService.execute(futureTaskMeiQia);
        executorService.execute(futureTaskFour);
        executorService.execute(futureTaskCallA);
        try {
            result[0] = futureTaskMeiQia.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            result[1] = futureTaskFour.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            result[2] = futureTaskCallA.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }


}
