package com.api.pfaProject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    //creating imad branch
    public String storeFile(MultipartFile file) throws Exception {
        // Generate a unique file name
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // Define the file path where the uploaded file will be stored
        Path filePath = Paths.get("uploads").resolve(fileName).toAbsolutePath();

        try {
            // Copy the input stream of the file to the destination file
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return fileName; // Return the file name or URL to be saved in the database
        } catch (IOException ex) {
            throw new Exception("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
}
