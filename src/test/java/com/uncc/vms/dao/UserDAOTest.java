package com.uncc.vms.dao;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by sachin on 8/16/2015.
 */

public class UserDAOTest {

    private UserDAO userDAO = new UserDAO(new DB());

    @Test
    public void createUserTest() {
        String email = "test@gmail.com";
        String password = "test";

        assertEquals(true, userDAO.addUser(email, password));
        assertNotNull(userDAO.validateLogin(email, password));
        assertNull(userDAO.validateLogin("test@gmail.com", "test12"));
        assertNull(userDAO.validateLogin("test1@gmail.com", "test"));
        assertEquals(true, userDAO.deleteUser(email));
    }
}
