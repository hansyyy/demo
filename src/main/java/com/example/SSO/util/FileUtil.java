package com.example.SSO.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author HanSiyue
 * @Date 2019/8/17 下午12:21
 */
public class FileUtil {

    public static String upload(MultipartFile file, String path) {
        try {
            String fileName = FileNameUtil.getFileName(file.getOriginalFilename());

            String realPath = path + "/" + fileName;

            File dest = new File(realPath);

            //判断文件父目录是否存在
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdir();
            }
            //保存文件
            file.transferTo(dest);
            return fileName;
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }



}
