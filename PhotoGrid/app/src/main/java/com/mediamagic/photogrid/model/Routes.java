package com.mediamagic.photogrid.model;

public enum Routes {
    imagesDataUrl("https://picsum.photos/list"),
    imageUrl("https://picsum.photos/300/300?image=");

    private String url;

    Routes(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
    // Routes.imagesDataUrl.getUrl()
}

