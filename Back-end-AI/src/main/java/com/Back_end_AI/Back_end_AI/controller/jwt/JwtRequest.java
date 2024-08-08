package com.Back_end_AI.Back_end_AI.controller.jwt;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String username;
    private String password;
    private String email;
    private String url; // הוסף את שדה ה-URL
    private String usernameDB; // הוסף את שדה ה-usernameDB
    private String passwordDB; // הוסף את שדה ה-passwordDB

    // Default constructor for JSON Parsing
    public JwtRequest() {
    }

    public JwtRequest(String username, String password, String email, String url, String usernameDB, String passwordDB) {
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.setUrl(url);
        this.setUsernameDB(usernameDB);
        this.setPasswordDB(passwordDB);
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

    public String getUrl() { // הוסף את getter ל-URL
        return this.url;
    }

    public void setUrl(String url) { // הוסף את setter ל-URL
        this.url = url;
    }

    public String getUsernameDB() { // הוסף את getter ל-usernameDB
        return this.usernameDB;
    }

    public void setUsernameDB(String usernameDB) { // הוסף את setter ל-usernameDB
        this.usernameDB = usernameDB;
    }

    public String getPasswordDB() { // הוסף את getter ל-passwordDB
        return this.passwordDB;
    }

    public void setPasswordDB(String passwordDB) { // הוסף את setter ל-passwordDB
        this.passwordDB = passwordDB;
    }
}
