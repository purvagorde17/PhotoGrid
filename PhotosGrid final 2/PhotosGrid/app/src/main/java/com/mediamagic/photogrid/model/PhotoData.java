package com.mediamagic.photogrid.model;

public class PhotoData {
    private String id;
    private String author;

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public PhotoData(String id, String author){
        this.id = id;
        this.author = author;
    }

    @Override
    public String toString() {
        return "PhotoData{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
