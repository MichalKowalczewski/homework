package com.homework.util;

import com.homework.model.Rating;

import java.util.Comparator;

public class RatingComparator implements Comparator<Rating> {
    @Override
    public int compare(Rating o1, Rating o2) {
        if (o1.getAverageRating() < o2.getAverageRating())
            return 1;
        else
            return -1;
    }
}
