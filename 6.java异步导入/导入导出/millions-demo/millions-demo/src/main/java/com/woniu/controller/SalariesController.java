package com.woniu.controller;

import com.woniu.service.ExportService;
import com.woniu.service.ImportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  蜗牛哥
 */

@RestController
public class SalariesController {

    @Resource
    private ExportService exportService;

    @Resource
    private ImportService importService;


    @GetMapping("export1")
    public void exportExcel1(HttpServletResponse response) throws IOException {
        exportService.exportExcel1(response);
    }


    @GetMapping("export2")
    public void exportExcel2(HttpServletResponse response) throws IOException {
        exportService.exportExcel2(response);
    }


    @GetMapping("export3")
    public void exportExcel3(HttpServletResponse response) throws IOException {
        exportService.exportExcel3(response);
    }

    /**
     * java异步导出实战
     * @param response
     * @throws IOException
     */
    @GetMapping("export4")
    public void exportExcel4(HttpServletResponse response) throws IOException, InterruptedException {
        exportService.exportExcel4(response);
    }



    /**
     * java异步导入实战
     * @param file
     * @throws IOException
     */
    @PostMapping("import")
    public void importExcel(MultipartFile file) throws IOException {
//        importService.importExcel(file);
        importService.importExcelAsync(file);
    }

}
