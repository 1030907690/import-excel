package com.springboot.sample.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

@Service
@Slf4j
public class ProcessFileService {

    @Resource
    private ExecutorService executorService;

    public String[] uploadFile(MultipartFile userFile, MultipartFile customerFile) throws ExecutionException, InterruptedException {
        FutureTask<String> userFlow = new FutureTask(new Callable() {
            @Override
            public String call() throws Exception {
                return saveFile(userFile, "user_flow");
            }
        });
        FutureTask<String> customerService = new FutureTask(new Callable() {
            @Override
            public String call() throws Exception {
                return saveFile(customerFile, "customer_service");
            }
        });
        executorService.execute(userFlow);
        executorService.execute(customerService);
        String userFlowRes = userFlow.get();
        String customerServiceRes = customerService.get();
        return new String[]{userFlowRes, customerServiceRes};
    }

    private String saveFile(MultipartFile customerFile, String fileType) {
        try {
            if (fileType != null && customerFile != null && !customerFile.isEmpty()) {
                String name = customerFile.getOriginalFilename();
                int index = name != null ? name.lastIndexOf(".") : -1;
                String type = index > 0 ? name.substring(index) : "";
                String path = "excel/" + fileType + "_" + System.currentTimeMillis() + type;
                File file = new File(System.getProperty("user.dir"), path);
                if (!file.getParentFile().exists() && file.getParentFile().mkdirs()) {
                    log.info("创建头像保存目录：" + file.getAbsolutePath());
                }
                customerFile.transferTo(file);
                log.info("上传成功 "+ file.getPath());
                return file.getPath();
            } else {
                log.info("文件其他异常");
            }
        } catch (Exception e) {
            log.info("异常");
        }
        return null;
    }

}
