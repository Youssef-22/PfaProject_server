package com.api.pfaProject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;


/* this is just to create the branch */

@Document(collection = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;


    private String profilePictureUrl;

//    public User(String firstName, String lastName, String username, String email, String password, MultipartFile file) {
//    }
}
