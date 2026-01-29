package com.castellarin.autorepuestos.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import java.util.Map;

@Service
@AllArgsConstructor
public class ImageService {

    private final Cloudinary cloudinary;

    public Map uploadProductsImage(String base64Image, String partNumber) {

        cloudinary.url().imageTag(partNumber+".jpg", ObjectUtils.asMap("alt",partNumber));

        Map customizingUpload= ObjectUtils.asMap(
                "public_id","products/"+partNumber,
                "use_filename", true,
                "unique_filename", false,
                "resource_type","auto"
        );

        try {
            return cloudinary.uploader().upload(base64Image, customizingUpload);
        } catch (IOException e) {
            throw new RuntimeException("Error uploading image");
        }

    }

}
