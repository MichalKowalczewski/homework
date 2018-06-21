package com.cognifide.homework.model;

import lombok.Data;

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
