package com.springboot.sample.bean;

import com.springboot.sample.common.type.MatchResultCauseEnum;
import lombok.Data;

import java.util.List;

@Data
public class MatchResult {

    private MatchResultCauseEnum matchResultCauseEnum;

    private List<CustomerService> customerServices;


}
