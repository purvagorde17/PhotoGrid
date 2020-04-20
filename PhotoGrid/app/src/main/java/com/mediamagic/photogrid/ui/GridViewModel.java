package com.mediamagic.photogrid.ui;

import androidx.lifecycle.ViewModel;

import com.mediamagic.photogrid.data.PhotoRepository;
import com.mediamagic.photogrid.model.BitmapInfo;

import java.util.HashMap;

public class GridViewModel extends ViewModel {
    private PhotoRepository photoRepository;
    private HashMap<String, BitmapInfo> bitmapInfoHashMap;

    public PhotoRepository getPhotoRepository() {
        if (photoRepository == null) {
            photoRepository = new PhotoRepository();
        }
        return photoRepository;
    }

    public boolean checkBitmapInfoExistsWithId(String id) {
        if (bitmapInfoHashMap == null) {
            return false;
        }
        if (bitmapInfoHashMap.containsKey(id)) {
            return true;
        }
        return false;
    }

    public BitmapInfo getBitmapInfoWithId(String id) {
        if (bitmapInfoHashMap != null && bitmapInfoHashMap.containsKey(id)) {
            return bitmapInfoHashMap.get(id);
        }
        return null;
    }

    public void addBitmapInfoWithId(String id, BitmapInfo bitmapInfo) {
        if (bitmapInfoHashMap == null) {
            bitmapInfoHashMap = new HashMap<>();
        }
        bitmapInfoHashMap.put(id, bitmapInfo);

    }
}
