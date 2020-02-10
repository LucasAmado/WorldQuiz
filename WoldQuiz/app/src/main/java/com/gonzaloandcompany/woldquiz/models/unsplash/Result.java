package com.gonzaloandcompany.woldquiz.models.unsplash;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {

    private String id;
    private String createdAt;
    private String updatedAt;
    private String promotedAt;
    private Integer width;
    private Integer height;
    private String color;
    private Object description;
    private String altDescription;
    private Urls urls;
    private Links links;
    private List<Object> categories = null;
    private Integer likes;
    private Boolean likedByUser;
    private List<Object> currentUserCollections = null;
    private User user;
    private List<Tag> tags = null;
}
