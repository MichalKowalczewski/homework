package com.cognifide.homework.controller;

import com.cognifide.homework.model.Book;
import com.cognifide.homework.util.JsonParser;
import com.cognifide.homework.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ApiController {

    @Autowired
    JsonParser jsonParser;

    @GetMapping("/api/book/{isbn}")
    public Book findByIsbn(@PathVariable("isbn") String isbn, HttpServletResponse response) throws IOException, ParseException {
        if (jsonParser.getBooksMap().containsKey(isbn))
            return jsonParser.getBooksMap().get(isbn);
        else
            throw new ResourceNotFoundException();
    }
    
    @GetMapping("/api/category/{category}/books")
    public List<Book> findByCategory(@PathVariable("category") String category){
        StringBuilder stringBuilder = new StringBuilder();
        List<Book> books = new ArrayList<>();
        for (Map.Entry<String, Book> entry: jsonParser.getBooksMap().entrySet()) {
            if (entry.getValue().getCategories() != null) {
                for (int i = 0; i < entry.getValue().getCategories().length; i++) {
                    if (entry.getValue().getCategories()[i].equals(category))
                        books.add(entry.getValue());
                }
            }
        }
        return books;
    }

}
