package com.uncc.vms.service;

import com.uncc.vms.dao.EventDAO;
import com.uncc.vms.domain.Event;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sachin on 9/23/2015.
 */
@Service("eventService")
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDAO eventDAO;

    @Override
    public String createPost(Event event) {
        return eventDAO.addPost(event);
    }

    @Override
    public Document findByPermalink(String permalink) {
        return eventDAO.findByPermalink(permalink);
    }

    @Override
    public List<Document> customPostSearch(Event event) {
        return eventDAO.customPostSearch(event);
    }

}
