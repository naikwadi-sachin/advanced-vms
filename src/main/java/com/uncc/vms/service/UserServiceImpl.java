package com.uncc.vms.service;

import com.uncc.vms.dao.UserDAO;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sachin on 8/7/2015.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public boolean createUser(String email, String password) {
        return userDAO.addUser(email, password);
    }

    @Override
    public Document validateUser(String email, String password) {
        return userDAO.validateLogin(email, password);
    }
}
