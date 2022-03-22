package com.example.imagepicker;

import java.io.Serializable;

public class createBookClass implements Serializable {
    String name,author;
    int year,pages;
    long id ;
    long id_category;
    private boolean isFavourite;
    private  byte[] images;

    public byte[] getImages() {
        return images;
    }
    public void setImages(byte[] images) {
        this.images = images;
    }
    public boolean isFavourite() {
        return isFavourite;
    }
    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
    public createBookClass( String name, String author, int year, int pages, long id_category, boolean b) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.pages = pages;
        this.id_category = id_category;
        this.isFavourite = b;
    }

    public long getId_category() {
        return id_category;
    }

    public void setId_category(long id_category) {
        this.id_category = id_category;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
