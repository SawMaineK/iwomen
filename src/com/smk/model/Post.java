package com.smk.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

@Expose
private Integer id;
@Expose
private String title;
@SerializedName("title_mm")
@Expose
private String titleMm;
@SerializedName("category_id")
@Expose
private Integer categoryId;
@SerializedName("user_id")
@Expose
private Integer userId;
@SerializedName("author_id")
@Expose
private Integer authorId;
@SerializedName("created_at")
@Expose
private String createdAt;
@Expose
private List<Content> contents = new ArrayList<Content>();
@Expose
private List<Photo> photos = new ArrayList<Photo>();
@Expose
private Category category;
@Expose
private Author author;
@Expose
private User user;

/**
* 
* @return
* The id
*/
public Integer getId() {
return id;
}

/**
* 
* @param id
* The id
*/
public void setId(Integer id) {
this.id = id;
}

/**
* 
* @return
* The title
*/
public String getTitle() {
return title;
}

/**
* 
* @param title
* The title
*/
public void setTitle(String title) {
this.title = title;
}

/**
* 
* @return
* The titleMm
*/
public String getTitleMm() {
return titleMm;
}

/**
* 
* @param titleMm
* The title_mm
*/
public void setTitleMm(String titleMm) {
this.titleMm = titleMm;
}

/**
* 
* @return
* The categoryId
*/
public Integer getCategoryId() {
return categoryId;
}

/**
* 
* @param categoryId
* The category_id
*/
public void setCategoryId(Integer categoryId) {
this.categoryId = categoryId;
}

/**
* 
* @return
* The userId
*/
public Integer getUserId() {
return userId;
}

/**
* 
* @param userId
* The user_id
*/
public void setUserId(Integer userId) {
this.userId = userId;
}

/**
* 
* @return
* The authorId
*/
public Integer getAuthorId() {
return authorId;
}

/**
* 
* @param authorId
* The author_id
*/
public void setAuthorId(Integer authorId) {
this.authorId = authorId;
}

/**
* 
* @return
* The createdAt
*/
public String getCreatedAt() {
return createdAt;
}

/**
* 
* @param createdAt
* The created_at
*/
public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

/**
* 
* @return
* The contents
*/
public List<Content> getContents() {
return contents;
}

/**
* 
* @param contents
* The contents
*/
public void setContents(List<Content> contents) {
this.contents = contents;
}

/**
* 
* @return
* The photos
*/
public List<Photo> getPhotos() {
return photos;
}

/**
* 
* @param photos
* The photos
*/
public void setPhotos(List<Photo> photos) {
this.photos = photos;
}

/**
* 
* @return
* The category
*/
public Category getCategory() {
return category;
}

/**
* 
* @param category
* The category
*/
public void setCategory(Category category) {
this.category = category;
}

/**
* 
* @return
* The author
*/
public Author getAuthor() {
return author;
}

/**
* 
* @param author
* The author
*/
public void setAuthor(Author author) {
this.author = author;
}

/**
* 
* @return
* The user
*/
public User getUser() {
return user;
}

/**
* 
* @param user
* The user
*/
public void setUser(User user) {
this.user = user;
}
}