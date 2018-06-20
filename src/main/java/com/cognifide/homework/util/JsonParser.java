package com.cognifide.homework.util;

import com.cognifide.homework.model.Book;
import com.cognifide.homework.model.IndustryIdentifier;
import com.cognifide.homework.model.Item;
import com.cognifide.homework.model.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class JsonParser {
    List<SimpleDateFormat> dateFormats = new LinkedList<>();
    private Map<String,Book> booksMap = new HashMap<String, Book>();

    public Map<String, Book> getBooksMap() {
        return booksMap;
    }

    @PostConstruct
    public void parseJson() throws IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();

        Gson gson = new Gson();
        SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
        dateFormats.add(dateFormatDate);
        dateFormats.add(dateFormatYear);

        Response response = gson.fromJson(new FileReader("C:\\Users\\Alberon\\Downloads\\Cognifide - Java Homework 2018\\books.json"), Response.class);
        Item[] items = response.getItems();

        for (int i = 0; i<items.length; i++) {
            Book book = new Book();
            String isbn = null;

            for (int j = 0; j<items[i].getVolumeInfo().getIndustryIdentifiers().length; j++) {
                if (items[i].getVolumeInfo().getIndustryIdentifiers()[j].getType().equals("ISBN_13")) {
                    isbn = items[i].getVolumeInfo().getIndustryIdentifiers()[j].getIdentifier();
                    book.setValues(items[i], isbn);
                    booksMap.put(isbn, book);
                    continue;
                }
            }
        }
    }

    public long parseStringToTimestamp(String dateString){
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
