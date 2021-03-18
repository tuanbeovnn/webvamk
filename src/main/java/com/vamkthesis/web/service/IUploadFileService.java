package com.vamkthesis.web.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUploadFileService {
    List<String> saveImage(MultipartFile[] files, int scaledWidth, int scaledHeight);
    String saveImage(String imageBase64, String imageName);
    byte[] urlImage(String url) throws IOException;
    boolean remove(String url);
}
