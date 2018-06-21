package com.cognifide.homework.controller;

import com.cognifide.homework.model.Book;
import com.cognifide.homework.model.Rating;
import com.cognifide.homework.util.JsonParser;
import com.cognifide.homework.exceptions.ResourceNotFoundException;
import com.cognifide.homework.util.RatingComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@RestController
public class ApiController {

    @Autowired
    JsonParser jsonParser;

    @GetMapping("/api/book/{isbn}")
    public Book findByIsbn(@PathVariable("isbn") String isbn, HttpServletResponse response) throws IOException, ParseException {
        if (jsonParser.getBooksMap().containsKey(isbn)) {
            return jsonParser.getBooksMap().get(isbn);
        }
        else {
            response.sendRedirect("/api/book/nonexisting");
            return null;
        }

    }

    @GetMapping("/api/book/nonexisting")
    public void nonExisting(){
        throw new ResourceNotFoundException("No results found");
    }
    
    @GetMapping("/api/category/{category}/books")
    public List<Book> findByCategory(@PathVariable("category") String category){
        List<Book> books = new LinkedList<>();
        for (Map.Entry<String, Book> entry: jsonParser.getBooksMap().entrySet()) {
            if (entry.getValue().getCategories() != null) {
                for (String cat : entry.getValue().getCategories()){
                    if (cat.equals(category))
                        books.add(entry.getValue());
                }
            }
        }
        return books;
    }

    @GetMapping(value = "/api/rating")
    public TreeSet<Rating> getAuthorsByRating(){
        TreeSet<Rating> ratings = new TreeSet<>(new RatingComparator());
        for (Map.Entry<String, Book> entry: jsonParser.getBooksMap().entrySet()){
            if (entry.getValue().getAverageRating() != null){
                for (String author : entry.getValue().getAuthors()){
                  Rating rating = new Rating();
                  rating.setAuthor(author);
                  rating.setAverageRating(entry.getValue().getAverageRating());
                  ratings.add(rating);
                }
            }
        }
        return ratings;
    }


}
