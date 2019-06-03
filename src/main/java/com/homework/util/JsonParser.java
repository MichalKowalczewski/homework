package com.homework.util;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.homework.model.Book;
import com.homework.model.IndustryIdentifier;
import com.homework.model.Item;
import com.homework.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.util.*;

@Component
@PropertySource("classpath:application.properties")
public class JsonParser {
    @Value("${parser.path}")
    private String path;
    @Value("${parser.source}")
    private String source;
    @Value("${parser.url}")
    private String url;

    private Map<String,Book> booksMap = new HashMap<>();
    public Map<String, Book> getBooksMap() {
        return booksMap;
    }
    ObjectMapper mapper = new ObjectMapper();



    @PostConstruct
    public void parseJson() throws IOException, ParseException {

        Response response = null;
        switch (source.toLowerCase()){
            case "file": {
                response = mapper.readValue(new FileReader(path), Response.class);
                break;
            }
            case "url": {
                response = mapper.readValue(new URL(url), Response.class);
                break;
            }
            default:
                throw new IOException("wrong source");
        }

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

    public void processNewJson(Object obj){
        try{
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true); //żeby było ładnie
            mapper.writeValue(new File(obj.toString()+"file.json"), obj);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void modifyJson(Object obj, String path){
        try{
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.writeValue(new File(path+"file.json"), obj);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
