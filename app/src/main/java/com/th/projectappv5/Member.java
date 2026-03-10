package com.th.projectappv5;

import java.io.Serializable;
public class Member implements Serializable {
    private String id;
    private String names;
    private String tel;
    private String email;
    public Member(String names, String tel, String email) {
        this.id = id;
        this.names = names;
        this.tel = tel;
        this.email = email;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNames() {
        return names;
    }
    public void setNames(String names) {
        this.names = names;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}