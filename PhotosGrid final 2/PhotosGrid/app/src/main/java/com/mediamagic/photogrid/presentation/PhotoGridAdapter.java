package com.mediamagic.photogrid.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.mediamagic.photogrid.R;
import com.mediamagic.photogrid.model.BitmapInfo;
import com.mediamagic.photogrid.model.PhotoData;

import java.util.List;

public class PhotoGridAdapter extends BaseAdapter {
    private List<PhotoData> photoDataList;
    private LayoutInflater inflater;
    private GridViewModel gridViewModel;

    PhotoGridAdapter(Context applicationContext, List<PhotoData> photoDataList, GridViewModel gridViewModel) {
        inflater = LayoutInflater.from(applicationContext);
        this.photoDataList = photoDataList;
        this.gridViewModel = gridViewModel;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getCount() {
        return photoDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.rowlayout, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.rowTextview);

        if (!gridViewModel.checkBitmapInfoExistsWithId(photoDataList.get(i).getId())) {
            gridViewModel.getPhotoRepository().new DownloadImageTask(gridViewModel, view, photoDataList.get(i)).execute();
        } else {
            BitmapInfo bitmapInfo = gridViewModel.getBitmapInfoWithId(photoDataList.get(i).getId());
            imageView.setImageBitmap(bitmapInfo.getBitmap());
            textView.setText(bitmapInfo.getAuthor());
        }
        return view;
    }
}
