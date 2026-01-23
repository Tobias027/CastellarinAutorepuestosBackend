package com.castellarin.autorepuestos.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class ImageService {

    public String uploadProductsImage(String base64Image, String productName) {

        Path path = Paths.get(System.getProperty("user.home"), "uploads", "products");

        try{
            if (!Files.exists(path)){
                Files.createDirectories(path);
            }

            String[] parts = base64Image.split(",");
            String cleanBase64 = (parts.length > 1) ? parts[1] : parts[0];
            String uniqueFileName = productName.replace(" ", "_")+"_"+UUID.randomUUID().toString()+".jpg";
            Path filePath = path.resolve(uniqueFileName);
            byte[] decodeBytes = Base64.getDecoder().decode(cleanBase64);
            Files.write(filePath, decodeBytes);
            return "/products/"+uniqueFileName;
        } catch(IOException e){
            throw new RuntimeException("Error while uploading image", e);
        } catch (IllegalArgumentException e) {
        throw new RuntimeException("El formato Base64 es inválido", e);
        }
    }

}
