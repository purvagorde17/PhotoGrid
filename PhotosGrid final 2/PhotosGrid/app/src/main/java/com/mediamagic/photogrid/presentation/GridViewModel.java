package com.mediamagic.photogrid.presentation;

import androidx.lifecycle.ViewModel;

import com.mediamagic.photogrid.data.PhotoRepository;
import com.mediamagic.photogrid.model.BitmapInfo;

import java.util.HashMap;

public class GridViewModel extends ViewModel {
    PhotoRepository photoRepository;

    HashMap<String, BitmapInfo> bitmapInfoHashMap;

    public PhotoRepository getPhotoRepository() {
        if (photoRepository == null) {
            photoRepository = new PhotoRepository();
        }
        return photoRepository;
    }

    public boolean checkBitmapInfoExistsWithId(String id) {
        if (bitmapInfoHashMap != null && bitmapInfoHashMap.get(id) != null)
            return true;
        return false;
    }

    public BitmapInfo getBitmapInfoWithId(String id) {
        if (bitmapInfoHashMap != null && bitmapInfoHashMap.get(id) != null) {
            return bitmapInfoHashMap.get(id);
        }
        return null;
    }

    public void addToBitmapInfoHashMap(String id, BitmapInfo bitmapInfo) {
        if (bitmapInfoHashMap == null) {
            bitmapInfoHashMap = new HashMap<>();
        }
        bitmapInfoHashMap.put(id, bitmapInfo);
    }
}
