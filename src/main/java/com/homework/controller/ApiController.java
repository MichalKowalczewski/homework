package com.homework.controller;

import com.homework.model.Book;
import com.homework.model.Rating;
import com.homework.util.JsonParser;
import com.homework.exceptions.ResourceNotFoundException;
import com.homework.util.RatingComparator;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@RestController
@Api(value = "BooksControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    @Autowired
    JsonParser jsonParser;

    @GetMapping("/api/book/{isbn}")
    @ApiOperation("Gets the books by ISBN")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "OK", response = Book.class)})
    public Book findByIsbn(
            @PathVariable("isbn") String isbn, HttpServletResponse response) throws IOException, ParseException {
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
    public List<Book> findByCategory(@PathVariable("category") @ApiParam(value = "Category of the books you want to be provided") String category){
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
                  Rating rating = new Rating(author, entry.getValue().getAverageRating());
                  ratings.add(rating);
                }
            }
        }
        return ratings;
    }


}
