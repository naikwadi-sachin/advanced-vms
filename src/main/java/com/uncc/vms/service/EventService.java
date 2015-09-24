package com.uncc.vms.service;

import com.uncc.vms.domain.Event;
import org.bson.Document;

/**
 * Created by sachin on 9/23/2015.
 */
public interface EventService {

    String createPost(Event event);

    Document findByPermalink(String permalink);

}