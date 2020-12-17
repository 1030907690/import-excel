package com.springboot.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@Controller
public class ImportController {

    /**
     * 用户上传头像
     */
    @RequestMapping("/import")
    public String importFile(@RequestParam("userFile") MultipartFile userFile, @RequestParam("customerFile") MultipartFile customerFile) {

        try {


            FutureTask<String> userFlow = new FutureTask(new Callable() {
                @Override
                public String call() throws Exception {
                    return saveFile(userFile,0);
                }
            });


            FutureTask<String> customerService = new FutureTask(new Callable() {
                @Override
                public String call() throws Exception {
                    return saveFile(customerFile,1);
                }
            });


            String userFlowRes = userFlow.get();
            String customerServiceRes = customerService.get();
            return "index";
        } catch (Exception e) {
            System.out.println("异常");
            return "index";
        }

    }


    private String saveFile(MultipartFile customerFile, Integer fileType) {
        try {
            if (fileType != null && customerFile != null && !customerFile.isEmpty()) {
                String name = customerFile.getOriginalFilename();
                int index = name != null ? name.lastIndexOf(".") : -1;
                String type = index > 0 ? name.substring(index) : "";
                String path = "excel/" + fileType + "_" + System.currentTimeMillis() + type;
                File file = new File(System.getProperty("user.dir"), path);
                if (!file.getParentFile().exists() && file.getParentFile().mkdirs()) {
                    System.out.println("创建头像保存目录：" + file.getAbsolutePath());
                }
                customerFile.transferTo(file);
                System.out.println("上传成功");
                return file.getAbsolutePath();
            } else {
                System.out.println("文件其他异常");
            }
        } catch (Exception e) {
            System.out.println("异常");
        }
        return null;
    }
}
