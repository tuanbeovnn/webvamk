package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.exception.ServerException;
import com.vamkthesis.web.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class UploadService implements IUploadFileService {
    @Value("${hostName}")
    String HOST;
    @Value("${pathurl}")
    String UPLOAD_DIR;

    /**
     * @TuanNguyen
     * @param files
     * @param scaledWidth
     * @param scaledHeight
     * @return
     */
    @Override
    public List<String> saveImage(MultipartFile[] files, int scaledWidth, int scaledHeight) {
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
                    int tagFileSub = tagFile.lastIndexOf(".");
                    String name = tagFile.substring(0,tagFileSub > 10 ? 10 : tagFileSub);
                    String fullName = name +  LocalDateTime.now() + splitTag;
                    String filePath = uploadsDir + fullName;
                    File dest = new File(filePath);
                    file.transferTo(dest);
                    images.add(HOST+"/api/images/"+fullName);
                    UploadService.resize(filePath, scaledWidth, scaledHeight);
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return images;
    }

    public static void resize(String inputImagePath, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);

        // creates output image
        BufferedImage outputImage = new BufferedImage(scaledWidth,
                scaledHeight, inputImage.getType());

        // scales the input image to the output image
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();

        // extracts extension of output file
        String formatName = inputImagePath.substring(inputImagePath
                .lastIndexOf(".") + 1);

        // writes to output file
        ImageIO.write(outputImage, formatName, new File(inputImagePath));
    }

    @Override
    public String saveImage(String imageBase64, String imageName) {
        byte[] imageByte= Base64.getDecoder().decode(imageBase64);
        return saveImage(imageByte,imageName);
    }

    public String saveImage(byte[] imageByte, String imageName) {
        try {
            String uploadsDir = UPLOAD_DIR;
            if (!new File(uploadsDir).exists()) {
                new File(uploadsDir).mkdir();
            }
            String tagFile = imageName;
            String splitTag = tagFile.substring(tagFile.lastIndexOf("."));
            String name = tagFile.substring(0,tagFile.lastIndexOf("."));
            String fullName = name + LocalDateTime.now() + splitTag;
            new FileOutputStream(uploadsDir + fullName).write(imageByte);
            return HOST+"/upload/"+fullName;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerException("Cannot save image");
        }
    }



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
