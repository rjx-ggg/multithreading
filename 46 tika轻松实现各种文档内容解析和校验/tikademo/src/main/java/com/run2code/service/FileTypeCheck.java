package com.run2code.service;

import com.run2code.constant.FileTypeContant;
import com.run2code.exception.ZTBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileTypeCheck {


    public static void getFileTypeByFileName(final String fileName, MultipartFile mFile) {
        Tika defaultTika = new Tika();
        String fileType;
        final File file = new File(fileName);
        try {
            FileUtils.copyInputStreamToFile(mFile.getInputStream(), file);
            fileType = defaultTika.detect(file);
            if (!FileTypeContant.FILE_TYPE_XLS.equals(fileType) && !FileTypeContant.FILE_TYPE_XLSX.equals(fileType)
                    && !FileTypeContant.FILE_TYPE_DOC.equals(fileType) && !FileTypeContant.FILE_TYPE_DOCX.equals(fileType) && !FileTypeContant.FILE_TYPE_PDF.equals(fileType)
                    && !FileTypeContant.FILE_TYPE_JSON.equals(fileType) && !FileTypeContant.FILE_TYPE_XML.equals(fileType)
                    && !FileTypeContant.FILE_TYPE_SVG.equals(fileType) && !FileTypeContant.FILE_TYPE_PNG.equals(fileType)) {
                throw new ZTBusinessException("请上传正确的文件类型!!!");
            }
        } catch (IOException e) {
            log.error("getFileTypeByFileName exception:", e);
        } finally {
            if (file.exists()) {
                file.delete();
            }
        }
    }

}
