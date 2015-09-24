package com.uncc.vms.domain;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by sachin on 9/23/2015.
 */
public class Event {
    private int userId;
    @NotEmpty
    private String name;
    private String permalink;
    @NotEmpty
    private String description;
    @NotEmpty
    private String date;
    @NotEmpty
    private String city;
    @NotEmpty
    private String state;
    private String createdDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }
}
