package com.api.pfaProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> allUsers(){
        return userRepository.findAll();
    }


    public User Register(@RequestBody User user) {
        return userRepository.save(user);
    }
    public User Login(@RequestBody User user) {
        String email = user.getEmail().trim();
        String hashedEnteredPassword = hashPassword(user.getPassword());
        System.out.println("Hashed Entered Password: " + hashedEnteredPassword);

        User usera = userRepository.findByEmail(email);
        if (usera != null) {
            System.out.println("Hashed Password from DB: " + usera.getPassword());
            if (hashedEnteredPassword.equals(usera.getPassword())) {
                return usera;
            } else {
                System.out.println("Passwords do not match");
            }
        } else {
            System.out.println("User not found");
        }
        return null;
    }




    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null; // Handle error
        }
    }
}
