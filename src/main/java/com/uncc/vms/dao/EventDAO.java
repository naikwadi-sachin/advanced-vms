package com.uncc.vms.dao;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.uncc.vms.domain.Event;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

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
        document.append("permalink", permalink);
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

    public Document findByPermalink(String permalink) {

        Document document = postCollection.find(eq("permalink", permalink)).first();

        return document;
    }

    public List<Document> findByDateDescending(int limit) {
        List<Document> posts = new ArrayList<Document>();
        MongoCursor<Document> cursor = postCollection.find().sort(new Document().append("date", -1)).limit(limit).iterator();
        try {
            while (cursor.hasNext())
                posts.add(cursor.next());
        } finally {
            cursor.close();
        }
        System.out.println("posts size= " + posts.size());
        return posts;
    }
}

