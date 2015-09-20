package com.uncc.vms.domain;

import java.io.Serializable;

/**
 * Created by sachin on 8/7/2015.
 */
public class User implements Serializable {
    private String email;
    private String password;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
