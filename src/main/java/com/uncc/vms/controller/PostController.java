package com.uncc.vms.controller;

import com.mongodb.client.MongoCollection;
import com.uncc.vms.domain.Event;
import com.uncc.vms.service.EventService;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        JSONObject result = new JSONObject();
        String permalink = eventService.createPost(event);
        result.put("permalink", permalink);
        return result.toString();
    }

    @RequestMapping(value = "/findByPermalink/{permalink}", method = RequestMethod.GET)
    public String findByPostName(@PathVariable String permalink) {
        JSONObject result = new JSONObject();
        System.out.println("permalink1 = " + permalink);
        Document post = eventService.findByPermalink(permalink);
        System.out.println("permalink = " + permalink);
        if (post == null) {
            result.put("permalink", permalink);
            result.put("status", "not found");
            return result.toString();
        }
        return post.toString();
    }

    @RequestMapping(value = "/findByDate/{limit}", method = RequestMethod.GET)
    public String findByDate(@PathVariable int limit) {
        JSONArray result = new JSONArray();
        System.out.println("limit = " + limit);
        List<Document> collection = eventService.findByDateDescending(limit);
        if (collection == null) {
            result.put(new JSONObject().put("status", "not found"));
        }
        else {
            for(Document document : collection)
                result.put(document);
        }

        return result.toString();
    }
}
