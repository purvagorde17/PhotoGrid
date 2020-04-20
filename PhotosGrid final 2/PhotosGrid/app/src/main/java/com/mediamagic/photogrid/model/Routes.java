package com.mediamagic.photogrid.model;

public enum Routes {
    imagesDataUrl("https://picsum.photos/list"),
    imageUrl("https://picsum.photos/300/300?image=");

    private String url;

    Routes(String envUrl) {
        this.url = envUrl;
    }

    public String getUrl() {
        return url;
    }
}
