package com.ecomerce.backend.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import com.ecomerce.backend.error.FileStorageException;
import com.ecomerce.backend.model.entity.Product;
import com.ecomerce.backend.model.entity.ProductImages;
import com.ecomerce.backend.repository.ImageRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FileUploadService {

    Logger log = LoggerFactory.getLogger(FileStorageException.class);

    @Autowired
    private ImageRepo imageRepo; 

    @Value("${upload.dir}")
    private String uploadDir;
    
    public String storeFile(Product product, MultipartFile file) {
        try {
            
            // Resolve the upload directory (resources/static/images)
            File staticImagesDir = ResourceUtils.getFile(uploadDir);
            if (!staticImagesDir.exists()) {
                staticImagesDir.mkdirs(); // Create directory if it doesn't exist
            }
            
            // Get the file name and save the file
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path uploadPath = Paths.get(staticImagesDir.getAbsolutePath(), fileName);
            Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

            ProductImages image = new ProductImages();
            image.setProduct(product);
            image.setImageURL("/images/" + fileName);
            imageRepo.save(image);

            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Could not store the file. Please try again!", e);
        }
		
	}

    public String storeImage(MultipartFile file) {
        try {
            
            // Resolve the upload directory (resources/static/images)
            File staticImagesDir = ResourceUtils.getFile(uploadDir);
            if (!staticImagesDir.exists()) {
                staticImagesDir.mkdirs(); // Create directory if it doesn't exist
            }
            
            // Get the file name and save the file
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path uploadPath = Paths.get(staticImagesDir.getAbsolutePath(), fileName);
            Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Could not store the file. Please try again!", e);
        }
		
	}
    
}
