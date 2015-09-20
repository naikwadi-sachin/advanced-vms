package com.uncc.vms.controller;

import com.uncc.vms.domain.User;
import com.uncc.vms.service.UserService;
import org.bson.Document;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sachin on 8/1/2015.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired(required = true)
    private UserService userService;

    @RequestMapping(value = "/validate", method = RequestMethod.POST, consumes = "application/json")
    public String validateUser(@RequestBody User user) {
        System.out.println("validateUser " + user.getEmail());
        Document userDoc = userService.validateUser(user.getEmail(), user.getPassword());
        JSONObject response = new JSONObject();
        boolean result;
        if (userDoc == null)
            result = false;
        else
            result = true;
        response.put("status", result);
        System.out.println("response = " + response.toString());
        return response.toString();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public String addUser(@RequestBody User user) {
        System.out.println();
        boolean result = userService.createUser(user.getEmail(), user.getPassword());
        JSONObject response = new JSONObject();
        response.put("status", result);
        System.out.println("respomse " + response.toString());
        return response.toString();
    }
}
