package com.castellarin.autorepuestos.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@Service
public class ImageService {

    private String basePath = "/home/uploads";

    public String uploadProductsImage(String base64Image, String productName) {
        String productsPath = basePath + "/products/";
        File uploadDir = new File(productsPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String cleanBase64 = base64Image.split(",")[1];
        String uniqueFileName = productName+UUID.randomUUID().toString() + ".jpg";
        String finalPath = productsPath + uniqueFileName;

        try{
            byte[] decodedByte = Base64.getDecoder().decode(cleanBase64);

            try(FileOutputStream fos = new FileOutputStream(finalPath)){
                fos.write(decodedByte);
            }

            return "/products/"+uniqueFileName;

        } catch(IOException e){
            throw new RuntimeException("Error while uploading image", e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Base64 invalid input", e);
        }
    }
}
