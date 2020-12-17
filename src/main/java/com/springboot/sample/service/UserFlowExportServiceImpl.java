package com.springboot.sample.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class UserFlowExportServiceImpl {

    private String templateUrl = "D:\\work\\excel\\temp\\用户全流程服务跟踪表模板 12.16（终）template.xlsx";

    public void userFlowExportExcel() throws IOException {
        TemplateExportParams params = new TemplateExportParams(templateUrl);
        params.setSheetNum(new Integer[]{0,1,2,3});
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Map<String, Object> lm = new HashMap<>();
            lm.put("id", i + 1  );

            lm.put("date", "2020/12/1"+i);
            listMap.add(lm);
        }
        map.put("meiQiaList", listMap);


        List<Map<String, Object>>  fourListMap = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Map<String, Object> lm = new HashMap<>();
            lm.put("id", i + 2  );
            lm.put("date", "2020/12/1"+i);
            fourListMap.add(lm);
        }
        map.put("fourList", fourListMap);

        Workbook workbook = ExcelExportUtil.exportExcel(params, map);
        File file = new File(System.getProperty("user.dir"), "excel");
        if (!file.exists()) {
            file.mkdir();
        }
        FileOutputStream fos = new FileOutputStream(file.getPath() + File.separator + "xxx.xlsx");
        workbook.write(fos);
        fos.close();
    }


    public static void main(String[] args) {
        UserFlowExportServiceImpl userFlowExportServiceImpl = new UserFlowExportServiceImpl();
        try {
            userFlowExportServiceImpl.userFlowExportExcel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}