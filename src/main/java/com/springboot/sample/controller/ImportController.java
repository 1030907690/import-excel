package com.springboot.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
public class ImportController {

    /**
     * 用户上传头像
     */
    @RequestMapping("/import")
    public String importFile(HttpServletRequest request, @RequestParam("file") MultipartFile image) {
        Integer userId = 0;
        String username = "";
        try {
            if (userId != null  && image != null && !image.isEmpty()) {
                String name = image.getOriginalFilename();
                int index = name != null ? name.lastIndexOf(".") : -1;
                String type = index > 0 ? name.substring(index) : "";
                String path = "excel/" + userId + "_" + System.currentTimeMillis() + type;
                File file = new File(System.getProperty("user.dir"), path);
                if (!file.getParentFile().exists() && file.getParentFile().mkdirs()) {
                    System.out.printf("创建头像保存目录：" + file.getAbsolutePath());
                }
                image.transferTo(file);
                System.out.printf("上传成功");
                return "index";
            } else {
                System.out.printf("其他");
                return "index";
            }
        } catch (Exception e) {
            System.out.printf("异常");
            return "index";
        }
    }
}
