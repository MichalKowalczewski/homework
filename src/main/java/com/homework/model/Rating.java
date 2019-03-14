package com.homework.model;

import lombok.Data;


public @Data
class Rating {
    private String author;
    private Double averageRating;

    public Rating(String author, Double averageRating){
        this.author = author;
        this.averageRating = averageRating;
    }
}
