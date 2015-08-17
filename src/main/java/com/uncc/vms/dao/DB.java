package com.uncc.vms.dao;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.uncc.vms.config.Settings;
import org.springframework.stereotype.Repository;

/**
 * Created by sachin on 8/8/2015.
 */
@Repository
public class DB {
    final MongoClient mongoClient;
    final MongoDatabase vmsDatabase;

    public DB(){
        this.mongoClient = new MongoClient(new MongoClientURI(Settings.mongoURIString));
        this.vmsDatabase = mongoClient.getDatabase("vms");
    }

    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getVmsDatabase() {
        return vmsDatabase;
    }
}
