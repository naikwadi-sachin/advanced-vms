package com.uncc.vms.dao;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by sachin on 8/5/2015.
 */
@Repository
public class UserDAO {
    private MongoCollection<Document> userCollection;
    private final ThreadLocal<Random> random = new ThreadLocal<Random>();

    @Autowired
    public UserDAO(DB db) {
        this.userCollection = db.getVmsDatabase().getCollection("users");
    }

    public boolean addUser(String email, String password) {
        String passwordHash = makePasswordHash(password, Integer.toString(getRandom().nextInt()));
        Document user = new Document();
        user.append("_id", email).append("password", passwordHash);

        try {
            userCollection.insertOne(user);
            return true;
        } catch (MongoWriteException e) {
            if (e.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
                System.out.println("username already in use = " + email);
                return false;
            }
            throw e;
        }
    }

    public boolean deleteUser(String email) {
        Document user = new Document();
        try {
            userCollection.deleteOne(user);
            return true;
        } catch (MongoException e) {
            throw e;
        }

    }

    public Document validateLogin(String email, String password) {
        Document user = userCollection.find(eq("_id", email)).first();
        if (user == null)
            return null;

        String hashedAndSalted = user.getString("password");

        String salt = hashedAndSalted.split(",")[1];

        if (!hashedAndSalted.equals(makePasswordHash(password, salt))) {
            System.out.println("submitted password does not match");
            return null;
        }
        return user;
    }

    private String makePasswordHash(String password, String salt) {
        try {
            String saltedAndHashed = password + "," + salt;
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(saltedAndHashed.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            byte hashedBytes[] = (new String(digest.digest(), "UTF-8")).getBytes();
            return encoder.encode(hashedBytes) + "," + salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 is not available", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 unavailable?  Not a chance", e);
        }
    }

    private Random getRandom() {
        Random result = random.get();
        if (result == null) {
            result = new Random();
            random.set(result);
        }
        return result;
    }
}
