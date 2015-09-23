package com.uncc.vms.controller;

import com.uncc.vms.domain.Event;
import com.uncc.vms.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sachin on 9/15/2015.
 */

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public String createPost(@RequestBody Event event) {

        String permalink = eventService.createPost(event);

        return permalink;
    }
}
