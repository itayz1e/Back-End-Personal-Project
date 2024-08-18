package com.Back_end_AI.Back_end_AI.controller.jwt;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;
    private String email;

    // Default constructor for JSON Parsing
    public JwtRequest() {
    }

    public JwtRequest(String username, String password, String email, String url, String usernameDB, String passwordDB) {
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
