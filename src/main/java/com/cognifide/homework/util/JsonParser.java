package com.cognifide.homework.util;

import com.cognifide.homework.model.Book;
import com.cognifide.homework.model.Item;
import com.cognifide.homework.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@PropertySource("classpath:application.properties")
public class JsonParser {
    @Value("${parser.path}")
    private String path;

    List<SimpleDateFormat> dateFormats = new LinkedList<>();
    private Map<String,Book> booksMap = new HashMap<>();

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

        Response response = gson.fromJson(new FileReader(path), Response.class);
        Item[] items = response.getItems();

        for (int i = 0; i<items.length; i++) {
            Book book;
            String isbn = null;

            for (int j = 0; j<items[i].getVolumeInfo().getIndustryIdentifiers().length; j++) {
                if (items[i].getVolumeInfo().getIndustryIdentifiers()[j].getType().equals("ISBN_13")) {
                    isbn = items[i].getVolumeInfo().getIndustryIdentifiers()[j].getIdentifier();
                    book = new Book(items[i], isbn);
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
