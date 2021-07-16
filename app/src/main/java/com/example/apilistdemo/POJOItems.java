package com.example.apilistdemo;

public class POJOItems {

    String name;
    String slug;
    String year;
    String count;
    String img;

    public POJOItems(String name, String slug, String year, String count, String img) {
        this.name = name;
        this.slug = slug;
        this.year = year;
        this.count = count;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
