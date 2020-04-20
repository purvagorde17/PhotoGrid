package com.mediamagic.photogrid.model;

public class PhotoData {
    private String author;
    private String id;

    public PhotoData(String author, String id) {
        this.author = author;
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "PhotoData{" +
                "author='" + author + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
