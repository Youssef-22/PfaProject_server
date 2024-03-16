package com.api.pfaProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/allusers")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>(userService.allUsers(), HttpStatus.OK);
    }

    @PostMapping(value = "/Register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public User Register(@RequestParam String email,
                         @RequestParam String password,
                         @RequestParam String firstname,
                         @RequestParam String lastname,
                         @RequestParam String username,
                         @RequestPart MultipartFile profilePicture) {
        User user = new User();
        user.setEmail(email);

        String profilePictureBase64 = convertFileToBase64(profilePicture);
        user.setProfilePictureUrl(profilePictureBase64);
        String hashedPassword = hashPassword(password);
        user.setPassword(hashedPassword);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setUsername(username);

        return userService.Register(user);
    }


    @PostMapping("/Login")
    public User Login(@RequestBody User user) {
        return userService.Login(user);
    }


    private String convertFileToBase64(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            return Base64.getEncoder().encodeToString(fileBytes);
        } catch (IOException e) {
            // Handle exception appropriately
            e.printStackTrace();
            return null;
        }
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
