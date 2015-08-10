package com.uncc.vms.controller;

import com.uncc.vms.domain.User;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sachin on 8/1/2015.
 */
@RestController
public class LoginController {

    @RequestMapping(value = "/checkUser", method = RequestMethod.POST, headers = {"Content-type=application/json"})
    public JSONObject validateUser(@RequestBody User user){
        System.out.println("validateUser " + user.getEmail());
        JSONObject response = new JSONObject();
        response.put("status","true");
        System.out.println("response = " + response.toJSONString());
        return response;
    }
}
