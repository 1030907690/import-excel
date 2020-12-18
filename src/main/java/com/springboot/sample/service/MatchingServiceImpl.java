package com.springboot.sample.service;

import com.springboot.sample.bean.*;
import com.springboot.sample.common.type.CustomerOriginEnum;
import com.springboot.sample.common.type.MatchResultCauseEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MatchingServiceImpl {

    @Resource
    private UserFlowReaderServiceImpl userFlowReaderServiceImpl;

    @Resource
    private CustomerServiceReaderServiceImpl customerServiceReaderServiceImpl;


    /***
     * zzq
     * 美洽在线匹配
     * 2020年12月18日16:26:45
     * */
    public List<UserFlowMeiQiaWrapper> meiQiaMatch(List<UserFlowMeiQia> userFlowMeiQiaList, Map<String, List<CustomerService>> group) {
        List<UserFlowMeiQiaWrapper> userFlowMeiQiaWrapperList = new ArrayList<>();
        List<CustomerService> customerServiceList = group.getOrDefault(CustomerOriginEnum.MEI_QIA_ONLINE.getCode(), new ArrayList<>());
        if (null != userFlowMeiQiaList) {
            for (UserFlowMeiQia userFlowMeiQia : userFlowMeiQiaList) {
                UserFlowMeiQiaWrapper userFlowMeiQiaWrapper = new UserFlowMeiQiaWrapper();
                MatchResult matchResult = matchGuestId(userFlowMeiQia.getGuestId(), customerServiceList);
                BeanUtils.copyProperties(userFlowMeiQia, userFlowMeiQiaWrapper);
                userFlowMeiQiaWrapper.setMatchResult(matchResult);
                userFlowMeiQiaWrapperList.add(userFlowMeiQiaWrapper);
            }
        }
        return userFlowMeiQiaWrapperList;
    }


    /***
     * zzq
     * 400匹配
     * 2020年12月18日16:26:45
     * */
    public List<UserFlowFourWrapper> fourMatch(List<UserFlowFour> userFlowFourList, Map<String, List<CustomerService>> group) {
        List<UserFlowFourWrapper> userFlowMeiQiaWrapperList = new ArrayList<>();
        List<CustomerService> customerServiceList = group.getOrDefault(CustomerOriginEnum.FOUR_HUNDRED.getCode(), new ArrayList<>());
        if (null != userFlowFourList) {
            for (UserFlowFour userFlowFour : userFlowFourList) {
                UserFlowFourWrapper userFlowFourWrapper = new UserFlowFourWrapper();
                MatchResult matchResult = matchMobile(userFlowFour.getMobile(), customerServiceList);
                BeanUtils.copyProperties(userFlowFour, userFlowFourWrapper);
                userFlowFourWrapper.setMatchResult(matchResult);
                userFlowMeiQiaWrapperList.add(userFlowFourWrapper);
            }
        }
        return userFlowMeiQiaWrapperList;
    }


    /***
     * zzq
     * 400匹配
     * 2020年12月18日16:26:45
     * */
    public List<UserFlowCallAWrapper> callAMatch(List<UserFlowCallA> userFlowCallAList, Map<String, List<CustomerService>> group) {
        List<UserFlowCallAWrapper> userFlowMeiQiaWrapperList = new ArrayList<>();
        List<CustomerService> customerServiceList = group.getOrDefault(CustomerOriginEnum.CALL_A.getCode(), new ArrayList<>());
        if (null != userFlowCallAList) {
            for (UserFlowCallA userFlowFourA : userFlowCallAList) {
                UserFlowCallAWrapper userFlowCallAWrapper = new UserFlowCallAWrapper();
                MatchResult matchResult = matchMobile(userFlowFourA.getMobile(), customerServiceList);
                BeanUtils.copyProperties(userFlowFourA, userFlowCallAWrapper);
                userFlowCallAWrapper.setMatchResult(matchResult);
                userFlowMeiQiaWrapperList.add(userFlowCallAWrapper);
            }
        }
        return userFlowMeiQiaWrapperList;
    }


    public MatchResult matchGuestId(String guestId, List<CustomerService> customerServiceList) {
        MatchResult matchResult = new MatchResult();
        matchResult.setMatchResultCauseEnum(MatchResultCauseEnum.NOT_FOUND);
        matchResult.setCustomerServices(new ArrayList<>());
        if (null != null) {
            int count = 0;
            for (CustomerService customerService : customerServiceList) {
                if (guestId.equals(customerService.getGuestId())) {
                    count++;
                    matchResult.getCustomerServices().add(customerService);
                }
            }
            if (count > 1) {
                //  有重复
                matchResult.setMatchResultCauseEnum(MatchResultCauseEnum.DUPLICATE);
            } else if (count == 0) {
                //  没有这个访客
                matchResult.setMatchResultCauseEnum(MatchResultCauseEnum.NOT_FOUND);
            } else if (count == 1) {
                //  匹配成功
                matchResult.setMatchResultCauseEnum(MatchResultCauseEnum.SUCCESS);
            }
        }
        return matchResult;
    }

    public MatchResult matchMobile(String mobile, List<CustomerService> customerServiceList) {
        MatchResult matchResult = new MatchResult();
        matchResult.setMatchResultCauseEnum(MatchResultCauseEnum.NOT_FOUND);
        matchResult.setCustomerServices(new ArrayList<>());
        if (null != null) {
            int count = 0;
            for (CustomerService customerService : customerServiceList) {
                if (mobile.equals(customerService.getMobile())) {
                    count++;
                    matchResult.getCustomerServices().add(customerService);
                }
            }
            if (count > 1) {
                //  有重复
                matchResult.setMatchResultCauseEnum(MatchResultCauseEnum.DUPLICATE);
            } else if (count == 0) {
                //  没有这个手机号
                matchResult.setMatchResultCauseEnum(MatchResultCauseEnum.NOT_FOUND);
            } else if (count == 1) {
                //  匹配成功
                matchResult.setMatchResultCauseEnum(MatchResultCauseEnum.SUCCESS);
            }
        }
        return matchResult;
    }


    /***
     * 待处理
     * */
    public void waitHandler(List<UserFlowMeiQiaWrapper> userFlowMeiQiaWrapperList, List<UserFlowFourWrapper> userFlowFourWrapperList, List<UserFlowCallAWrapper> userFlowCallAWrapperList) {
        List<Object> waitHandler = new ArrayList<>();
        for (UserFlowMeiQiaWrapper userFlowMeiQiaWrapper : userFlowMeiQiaWrapperList) {
             if (MatchResultCauseEnum.DUPLICATE == userFlowMeiQiaWrapper.getMatchResult().getMatchResultCauseEnum()){

             }
        }

    }


    public static void main(String[] args) {
        MatchingServiceImpl matchingService = new MatchingServiceImpl();
        matchingService.userFlowReaderServiceImpl = new UserFlowReaderServiceImpl();
        List<UserFlowMeiQia> userFlowMeiQiaList = matchingService.userFlowReaderServiceImpl.readerUserFlowMeiQiaExcel(matchingService.userFlowReaderServiceImpl.filePath);
        matchingService.customerServiceReaderServiceImpl = new CustomerServiceReaderServiceImpl();
        CustomerServiceReaderServiceImpl customerServiceImpl = matchingService.customerServiceReaderServiceImpl;
        Map<String, List<CustomerService>> group = customerServiceImpl.group(customerServiceImpl.readerCustomerExcel(customerServiceImpl.filePath));
        List<UserFlowMeiQiaWrapper> userFlowMeiQiaWrapperList = matchingService.meiQiaMatch(userFlowMeiQiaList, group);


    }
}