package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadService implements IUploadFileService {
    @Value("${hostName}")
    String HOST;
    @Value("${pathurl}")
    String UPLOAD_DIR;

    @Override
    public List<String> saveImage(MultipartFile[] files) {
        List<String> images = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                try {
                    String uploadsDir = UPLOAD_DIR;
                    if (!new File(uploadsDir).exists()) {
                        new File(uploadsDir).mkdir();
                    }
                    String tagFile = file.getOriginalFilename();
                    String splitTag = tagFile.substring(tagFile.lastIndexOf("."));
                    String name = tagFile.substring(0,tagFile.lastIndexOf("."));
                    String fullName = name + LocalDateTime.now() + splitTag;
                    String filePath = uploadsDir + fullName;
                    File dest = new File(filePath);
                    file.transferTo(dest);
                    images.add(HOST+"/api/images/"+fullName);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return images;
    }

//    @Override
//    public String saveImage(String imageBase64, String imageName) {
//        byte[] imageByte= Base64.getDecoder().decode(imageBase64);
//        return saveImage(imageByte,imageName);
//    }
//
//    public String saveImage(byte[] imageByte, String imageName) {
//        try {
//            String uploadsDir = UPLOAD_DIR;
//            if (!new File(uploadsDir).exists()) {
//                new File(uploadsDir).mkdir();
//            }
//            String tagFile = imageName;
//            String splitTag = tagFile.substring(tagFile.lastIndexOf("."));
//            String name = tagFile.substring(0,tagFile.lastIndexOf("."));
//            String fullName = name + LocalDateTime.now() + splitTag;
//            new FileOutputStream(uploadsDir + fullName).write(imageByte);
//            return HOST+"/upload/"+fullName;
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new ServerException("Cannot save image");
//        }
//    }



    @Override
    public byte[] urlImage(String url) throws IOException {
        try {
            BufferedImage bImage = ImageIO.read(new File(UPLOAD_DIR+url));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos );
            byte [] data = bos.toByteArray();
            return data;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean remove(String url) {
        try {
            File fileToDelete = new File(UPLOAD_DIR+url);
            boolean success = fileToDelete.delete();
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
