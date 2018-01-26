package com.gtja.shiee.topic.common.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;

@Document
public class Topic {
    @Id
    private String id;

    private String name;

    private Integer available;

    private String parentId;

    private String parentIds;

    private Integer orderType;

    private Integer pageType;

    private Integer secNav;

    private String postShowTypes;

    private Integer levelId;

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getSecNav() {
        return secNav;
    }

    public void setSecNav(Integer secNav) {
        this.secNav = secNav;
    }

    public String getPostShowTypes() {
        return postShowTypes;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public void setPostShowTypes(String postShowTypes) {
        this.postShowTypes = postShowTypes;
    }

    public boolean isRoot() {
        return this.levelId == 1;
    }

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }
}