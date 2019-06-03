package com.homework.model;

import lombok.Data;

@Data
public class Customer {

    private int id;
    private String name;
    private String address;

    @Override
    public String toString(){
        return this.name;
    }
}
