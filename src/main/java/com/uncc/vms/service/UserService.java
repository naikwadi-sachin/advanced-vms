package com.uncc.vms.service;

import org.bson.Document;

/**
 * Created by sachin on 8/10/2015.
 */
public interface UserService {
    boolean createUser(String email, String password);

    Document validateUser(String email, String password);
}
