package com.Back_end_AI.Back_end_AI.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 255)
    @NotNull
    @Size(max = 255)
    private String username;

    @Column(nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    private String password;

    @Column(nullable = false, length = 255)
    @NotNull
    @Size(max = 255)
    private String email;

    @Column(length = 255)
    @Size(max = 255)
    private String url;

    @Column(name = "usernameDB", length = 255)
    @Size(max = 255)
    private String usernameDB;

    @Column(name = "passwordDB", length = 255)
    @Size(max = 255)
    private String passwordDB;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsernameDB() {
        return usernameDB;
    }

    public void setUsernameDB(String usernameDB) {
        this.usernameDB = usernameDB;
    }

    public String getPasswordDB() {
        return passwordDB;
    }

    public void setPasswordDB(String passwordDB) {
        this.passwordDB = passwordDB;
    }
}
