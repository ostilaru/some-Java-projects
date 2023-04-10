package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUploadController {

    @PostMapping("/upload")
    public String up(String nickname, MultipartFile photo, HttpServletRequest request) throws IOException{
        System.out.println(nickname);
        // 获取图片原始名称
        System.out.println(photo.getOriginalFilename());
        // 取文件类型
        System.out.println(photo.getContentType());
        System.out.println(System.getProperty("user.dir"));

        // web服务器的运行目录
        String path = request.getServletContext().getRealPath("/upload");
        System.out.println(path);
        saveFile(photo, path);
        return "success";

    }

    public void saveFile(MultipartFile photo, String path) throws IOException {
        // 判断存储目录是否存在，如果不存在则创建
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }

        File file = new File(path + photo.getOriginalFilename());
        // 保存文件
        photo.transferTo(file);
    }
}
