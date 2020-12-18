package com.springboot.sample.service;

import com.springboot.sample.bean.CustomerService;
import com.springboot.sample.bean.MatchResult;
import com.springboot.sample.bean.UserFlowMeiQia;
import com.springboot.sample.common.type.CustomerOriginEnum;
import com.springboot.sample.common.type.MatchResultCauseEnum;
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
    public void meiQiaMatch(List<UserFlowMeiQia> userFlowMeiQiaList, Map<String, List<CustomerService>> group) {
        List<CustomerService> customerServiceList = group.getOrDefault(CustomerOriginEnum.MEI_QIA_ONLINE.getCode(), new ArrayList<>());
        if (null != userFlowMeiQiaList) {
            for (UserFlowMeiQia userFlowMeiQia : userFlowMeiQiaList) {
                MatchResult matchResult = matchGuestId(userFlowMeiQia.getGuestId(), customerServiceList);
                if (MatchResultCauseEnum.SUCCESS == matchResult.getMatchResultCauseEnum()) {
                    //TODO 匹配成功
                    CustomerService customerService = matchResult.getCustomerServices().get(0);
                } else if (MatchResultCauseEnum.DUPLICATE == matchResult.getMatchResultCauseEnum()) {
                    //TODO 重复
                }
            }
        }
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
                //TODO 有重复
                matchResult.setMatchResultCauseEnum(MatchResultCauseEnum.DUPLICATE);
            } else if (count == 0) {
                //TODO 没有这个访客
                matchResult.setMatchResultCauseEnum(MatchResultCauseEnum.NOT_FOUND);
            } else if (count == 1) {
                //TODO 匹配成功
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
                //TODO 有重复
                matchResult.setMatchResultCauseEnum(MatchResultCauseEnum.DUPLICATE);
            } else if (count == 0) {
                //TODO 没有这个手机号
                matchResult.setMatchResultCauseEnum(MatchResultCauseEnum.NOT_FOUND);
            } else if (count == 1) {
                //TODO 匹配成功
                matchResult.setMatchResultCauseEnum(MatchResultCauseEnum.SUCCESS);
            }
        }
        return matchResult;
    }

    public static void main(String[] args) {
        MatchingServiceImpl matchingService = new MatchingServiceImpl();
        matchingService.userFlowReaderServiceImpl = new UserFlowReaderServiceImpl();
        List<UserFlowMeiQia> userFlowMeiQiaList = matchingService.userFlowReaderServiceImpl.readerUserFlowMeiQiaExcel(matchingService.userFlowReaderServiceImpl.filePath);
        matchingService.customerServiceReaderServiceImpl = new CustomerServiceReaderServiceImpl();
        CustomerServiceReaderServiceImpl customerServiceImpl = matchingService.customerServiceReaderServiceImpl;
        Map<String, List<CustomerService>> group = customerServiceImpl.group(customerServiceImpl.readerCustomerExcel(customerServiceImpl.filePath));
        matchingService.meiQiaMatch(userFlowMeiQiaList, group);
    }
}