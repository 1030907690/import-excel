package com.springboot.sample.service;

import com.springboot.sample.bean.ResultVo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ValidateService {

    public ResultVo doValidate(MultipartFile [] files) {
        ResultVo result = validateFile(files);
        return result;
    }

    private ResultVo validateFile(MultipartFile [] files){
        ResultVo result = new ResultVo();
        result.setSuccess(Boolean.TRUE);
        if (files.length != 2){
            result.fail("只能上传2个文件!");
        }
        for (MultipartFile file : files) {
            if (!file.getOriginalFilename().contains(".xlsx")){
                result.fail("只能上传xlsx格式!");
            }
        }
        return result;
    }
}
