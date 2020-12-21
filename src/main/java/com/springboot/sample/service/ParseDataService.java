package com.springboot.sample.service;

import com.springboot.sample.bean.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

@Service
public class ParseDataService {

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

    /***
     * 解析匹配数据，美洽、400、电话A客
     * */
    public Object[] doParseData(String filePath, Map<String, List<CustomerService>> group) {
        Object [] result = new Object[]{new ArrayList<UserFlowMeiQiaWrapper>(),new ArrayList<UserFlowFourWrapper>(),new ArrayList<UserFlowCallAWrapper>()};
        FutureTask<List<UserFlowMeiQiaWrapper>> futureTaskMeiQia = new FutureTask<>(new Callable<List<UserFlowMeiQiaWrapper>>() {
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
