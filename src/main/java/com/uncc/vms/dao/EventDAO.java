package com.uncc.vms.dao;

import com.mongodb.client.MongoCollection;
import com.uncc.vms.domain.Event;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * Created by sachin on 9/23/2015.
 */
@Repository
public class EventDAO {
    private MongoCollection<Document> postCollection;

    @Autowired
    public EventDAO(DB db) {
        this.postCollection = db.getVmsDatabase().getCollection("posts");
    }

    public String addPost(Event event) {
        String permalink = event.getName().replaceAll("\\s", "_"); // whitespace becomes _
        permalink = permalink.replaceAll("\\W", ""); // get rid of non alphanumeric
        permalink = permalink.toLowerCase();
        Document document = new Document();
        document.append("name", event.getName());
        document.append("description", event.getDescription());
        document.append("date", event.getDate());
        document.append("state", event.getState());
        document.append("city", event.getCity());
        document.append("userId", event.getUserId());
        document.append("creteDate", new java.util.Date());
        document.append("comments", new ArrayList());

        try {
            postCollection.insertOne(document);
        } catch (Exception e) {
            return null;
        }
        return permalink;
    }

}
