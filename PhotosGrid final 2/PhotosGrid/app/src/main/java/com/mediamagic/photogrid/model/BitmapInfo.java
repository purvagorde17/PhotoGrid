package com.mediamagic.photogrid.model;

import android.graphics.Bitmap;

public class BitmapInfo {

    private String author;
    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getAuthor() {
        return author;
    }

    public BitmapInfo(Bitmap bitmap, String author) {
        this.bitmap = bitmap;
        this.author = author;
    }
}
