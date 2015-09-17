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
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired(required = true)
    private UserService userService;

    @RequestMapping(value = "/checkUser", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public JSONObject validateUser(@RequestBody User user) {
        System.out.println("validateUser " + user.getEmail());
        Document userDoc = userService.validateUser(user.getEmail(), user.getPassword());
        JSONObject response = new JSONObject();
        if (userDoc == null)
            response.put("status", "false");
        else
            response.put("status", "true");
        System.out.println("response = " + response.toString());
        return response;
    }
}
