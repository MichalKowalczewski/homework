package com.homework.model;

import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

@Data
public class BookOrder extends ResourceSupport {

    private Book bookOrdered;
    private LocalDateTime orderDate;

    public BookOrder(Book book) {
        bookOrdered = book;
        orderDate = LocalDateTime.now();
    }
}
