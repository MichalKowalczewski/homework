package com.cognifide.homework.util;

import com.cognifide.homework.model.Book;
import com.cognifide.homework.model.IndustryIdentifier;
import com.cognifide.homework.model.Item;
import com.cognifide.homework.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        SimpleDateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatYear = new SimpleDateFormat("yyyy");
        dateFormats.add(dateFormatDate);
        dateFormats.add(dateFormatYear);

        Response response = mapper.readValue(new FileReader(path), Response.class);

        Item[] items = response.getItems();

        for (Item item : items){
            Book book;
            String isbn = null;

            for (IndustryIdentifier industryIdentifier : item.getVolumeInfo().getIndustryIdentifiers()){
                if (industryIdentifier.getType().equals("ISBN_13")) {
                    isbn = industryIdentifier.getIdentifier();
                    book = new Book(item, isbn);
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
