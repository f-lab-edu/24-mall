package com.temp.sample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "user")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    private Long id;
    private String email;
    private String password;
    private String phoneNumber ;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static User createUser(String email, String password, String phoneNumber) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.phoneNumber = phoneNumber;
        return user;
    }

    public static User createMockUser(Long id,String email, String password, String phoneNumber) {
        User user = new User();
        user.id = id;
        user.email = email;
        user.password = password;
        user.phoneNumber = phoneNumber;
        return user;
    }
}
