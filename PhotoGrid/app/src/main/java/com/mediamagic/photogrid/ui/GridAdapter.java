package com.mediamagic.photogrid.ui;

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

public class GridAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<PhotoData> photoDataList;
    private GridViewModel gridViewModel;

    public GridAdapter(Context appContext, List<PhotoData> photoDataList, GridViewModel gridViewModel) {
        this.gridViewModel = gridViewModel;
        layoutInflater = LayoutInflater.from(appContext);
        this.photoDataList = photoDataList;
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
        view = layoutInflater.inflate(R.layout.rowlayout, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.rowTextview);
        PhotoData photoData = photoDataList.get(i);
        if (gridViewModel.checkBitmapInfoExistsWithId(photoData.getId())) {
            BitmapInfo bitmapInfo = gridViewModel.getBitmapInfoWithId(photoData.getId());
            imageView.setImageBitmap(bitmapInfo.getBitmap());
            textView.setText(bitmapInfo.getAuthor());
        } else {
            gridViewModel.getPhotoRepository().new DownloadImageTask(view, photoData, gridViewModel).execute();
        }
        return view;
    }
}
