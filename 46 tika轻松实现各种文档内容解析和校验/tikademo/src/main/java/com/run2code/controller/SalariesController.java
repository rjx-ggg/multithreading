package com.run2code.controller;

import com.run2code.service.FileTypeCheck;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 薪资控制器，模拟导入导出的入口
 *
 * @author woniu
 */
@RestController
@Slf4j
public class SalariesController {

    @Autowired
    private Tika tika;

    /**
     * Apache tika
     * 轻松实现各种文档内容解析和校验
     * @throws Exception
     */
    @GetMapping("getTika")
    public void getTika() throws Exception {
        Path path = Paths.get("E:\\import-export-millions-master\\import-export-millions-master\\src\\main\\resources\\",
                "application.yml");
        log.info("内容为：{}", tika.parseToString(path.toFile()));
    }

    /**
     * 文件校验
     * @param multipartFile
     * @throws IOException
     */
    @PostMapping("import")
    public void importExcel(MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        FileTypeCheck.getFileTypeByFileName(fileName, multipartFile);
    }


}
