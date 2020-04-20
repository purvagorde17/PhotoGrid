package com.mediamagic.photogrid.model;

import android.graphics.Bitmap;

public class BitmapInfo {
    private String author;
    private Bitmap bitmap;

    public BitmapInfo(String author, Bitmap bitmap) {
        this.author = author;
        this.bitmap = bitmap;
    }

    public String getAuthor() {
        return author;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
