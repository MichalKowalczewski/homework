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

}
