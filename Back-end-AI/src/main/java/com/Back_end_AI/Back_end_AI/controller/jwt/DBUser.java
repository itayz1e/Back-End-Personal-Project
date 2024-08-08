package com.Back_end_AI.Back_end_AI.controller.jwt;

import com.google.common.base.MoreObjects;
import org.springframework.data.domain.Persistable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class DBUser implements Serializable, Persistable<Long> {

    private static final long serialVersionUID = -5554304839188669754L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(length = 255)
    private String url; // הוספת עמודה חדשה

    @Column(length = 255)
    private String usernameDB; // הוספת עמודה חדשה

    @Column(length = 255)
    private String passwordDB; // הוספת עמודה חדשה

    protected DBUser() {}

    @Transient
    public static String hashPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    @Transient
    public boolean isNew() {
        return null == getId();
    }

    public Long getId() {
        return id;
    }

    protected void setId(final Long id) {
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", getId())
                .add("username", username)
                .add("email", email)
                .add("url", url) // הוספת פרמטרים חדשים להדפסה
                .add("usernameDB", usernameDB)
                .add("passwordDB", passwordDB)
                .toString();
    }

    public static final class UserBuilder {
        protected Long id;
        private String username;
        private String password;
        private String email;
        private String url;
        private String usernameDB;
        private String passwordDB;

        private UserBuilder() {}

        public static UserBuilder anUser() {
            return new UserBuilder();
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder url(String url) {
            this.url = url;
            return this;
        }

        public UserBuilder usernameDB(String usernameDB) {
            this.usernameDB = usernameDB;
            return this;
        }

        public UserBuilder passwordDB(String passwordDB) {
            this.passwordDB = passwordDB;
            return this;
        }

        public UserBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public DBUser build() {
            DBUser user = new DBUser();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setUrl(url);
            user.setUsernameDB(usernameDB);
            user.setPasswordDB(passwordDB);
            user.setId(id);
            return user;
        }
    }
}
