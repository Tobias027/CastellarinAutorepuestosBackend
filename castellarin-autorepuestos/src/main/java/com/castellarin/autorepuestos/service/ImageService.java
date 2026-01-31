package com.castellarin.autorepuestos.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@AllArgsConstructor
public class ImageService {

    private final Cloudinary cloudinary;

    public Map uploadProductsImage(MultipartFile file, String partNumber) {

        Map customizingUpload= ObjectUtils.asMap(
                "public_id","products/"+partNumber.replace(" ", "_"),
                "use_filename", true,
                "unique_filename", false,
                "resource_type","image"
        );

        try {
            return cloudinary.uploader().upload(file.getBytes(), customizingUpload);
        } catch (IOException e) {
            throw new RuntimeException("Error uploading image");
        }

    }

}
