package com.vamkthesis.web.api;


import com.vamkthesis.web.service.impl.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UploadApi {
    @Autowired
    private UploadService imageService;

        @RequestMapping(value = "/uploadfile", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity handleFileUpload(@RequestParam("files") MultipartFile[] files) {
        List<String> listImg = imageService.saveImage(files);
        if (listImg != null) {
            return ResponseEntity.ok(listImg);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @RequestMapping(value = "/images/{filename}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity getImg(@PathVariable String filename) throws IOException {
        byte[] data = imageService.urlImage(filename);
        if (data != null) {
            return ResponseEntity.ok(data);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @RequestMapping(value = "/images/{filename}", method = RequestMethod.DELETE)
    public ResponseEntity removeImg(@PathVariable String filename) throws IOException {
        boolean flag = imageService.remove(filename);
        if (flag) {
            return ResponseEntity.ok("Success");
        } else {
            return ResponseEntity.ok("Failed");
        }
    }

}
