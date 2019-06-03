package com.homework.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApiModel(value="Book Model", description = "This is data model of a book")
public @Data
class Book extends ResourceSupport {
    private String isbn;
    private String title;
    private String subtitle;
    private String publisher;
    @ApiModelProperty(value = "date when book has been published in epoch format")
    private Long publishedDate;
    private String description;
    private Integer pageCount;
    private String thumbnailUlr;
    private String language;
    private String previewLink;
    private Double averageRating;

    @ApiModelProperty(value = "list of the book authors")
    private String[] authors;
    private String[] categories;

    public Book(Item item, String isbn){
        this.isbn = isbn;
        this.title = item.getVolumeInfo().getTitle();
        this.subtitle = item.getVolumeInfo().getSubtitle();
        this.publisher = item.getVolumeInfo().getPublisher();
        if (item.getVolumeInfo().getPublishedDate() != null){
            String date = item.getVolumeInfo().getPublishedDate();
            this.publishedDate = parseStringToTimestamp(date);
        }
        this.description = item.getVolumeInfo().getDescription();
        this.pageCount = item.getVolumeInfo().getPageCount();
        if (item.getVolumeInfo().getImageLinks() != null) {
            this.thumbnailUlr = item.getVolumeInfo().getImageLinks().getThumbnail();
        }
        this.language = item.getVolumeInfo().getLanguage();
        this.previewLink = item.getVolumeInfo().getPreviewLink();
        this.averageRating = item.getVolumeInfo().getAverageRating();
        if (item.getVolumeInfo().getAuthors() != null){
            this.authors = item.getVolumeInfo().getAuthors();
        }
        if (item.getVolumeInfo().getCategories() != null){
            this.categories = item.getVolumeInfo().getCategories();
        }
    }

    public long parseStringToTimestamp(String dateString){
        SimpleDateFormat[] dateFormats = new SimpleDateFormat[]{new SimpleDateFormat("yyyy"), new SimpleDateFormat("yyyy-MM-dd")};
        long timestamp = 0;
        for (SimpleDateFormat df: dateFormats) {
            try {
                Date date = df.parse(dateString);
                timestamp = date.getTime();
                break;
            }
            catch (ParseException pe){
                pe.getMessage();
            }
        }
        return timestamp;
    }


}



