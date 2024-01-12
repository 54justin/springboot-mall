package springboot.mall.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User {

    private Integer userId;

    //@JsonProperty("e_mail")   //回傳給前端改為e_mail
    private String email;

    @JsonIgnore
    private String password;
    private Date createdDate;
    private Date lastModifiedDate;
    
    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return this.lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    
}
