package com.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
public @Data
class  VolumeInfo{

    private String title;
    private String subtitle;
    private String publisher;
    private String publishedDate;
    private String description;
    private Integer pageCount;
    private String language;
    private String previewLink;
    private Double averageRating;
    private String[] authors;
    private String[] categories;
    private IndustryIdentifier[] industryIdentifiers;
    private ImageLinks imageLinks;

}
