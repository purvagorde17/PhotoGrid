package com.mediamagic.photogrid.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.MutableLiveData;

import com.mediamagic.photogrid.R;
import com.mediamagic.photogrid.model.BitmapInfo;
import com.mediamagic.photogrid.model.PhotoData;
import com.mediamagic.photogrid.model.Routes;
import com.mediamagic.photogrid.ui.GridViewModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PhotoRepository {
    private List<PhotoData> photoDataList;
    private MutableLiveData<List<PhotoData>> photoDataListLiveData;

    public MutableLiveData<List<PhotoData>> getPhotoDataListLiveData() {
        if (photoDataListLiveData == null) {
            photoDataListLiveData = new MutableLiveData<>();
        }
        return photoDataListLiveData;
    }

    public void fetchPhotoDataList() {
        if (photoDataList == null) {
            photoDataList = new ArrayList<>();
        }
        if (photoDataListLiveData != null && photoDataListLiveData.getValue() == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d("####", "fetching imagedata");
                    StringBuffer response = new StringBuffer("");
                    try {
                        Log.d("###", Routes.imagesDataUrl.getUrl());
                        URL imageDataUrl = new URL(Routes.imagesDataUrl.getUrl());
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(imageDataUrl.openStream()));

                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        fetchPhotoDataListFromResponse(response.toString());
                        photoDataListLiveData.postValue(photoDataList);
                    } catch (Exception e) {
                        Log.e("###", "exception thrown " + e);
                    }
                }
            }).start();
        }
    }

    private void fetchPhotoDataListFromResponse(String response) {
        Log.d("###", "fetchPhotoDataListFromResponse:" + response);
        if (photoDataList == null) {
            photoDataList = new ArrayList<>();
        }
        for (int i = 0; i < response.length(); i++) {
            if (i + 4 <= response.length() && response.substring(i, i + 4).equals("\"id\"")) {
                StringBuilder id = new StringBuilder();
                StringBuilder author = new StringBuilder();
                while (!Character.isDigit(response.charAt(i))) {
                    i++;
                }
                while (Character.isDigit(response.charAt(i))) {
                    id.append(response.charAt(i));
                    i++;
                }

                for (; i < response.length(); i++) {
                    if (i + 8 <= response.length() && response.substring(i, i + 8).equals("\"author\"")) {
                        i = i + 8;
                        while (response.charAt(i) != '\"') {
                            i++;
                        }
                        i++;
                        while (response.charAt(i) != '\"') {
                            author.append(response.charAt(i));
                            i++;
                        }
                        photoDataList.add(new PhotoData(author.toString(), id.toString()));
                        break;
                    }
                }
            }

        }

    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        WeakReference<View> wView;
        WeakReference<GridViewModel> wgridViewmodel;
        WeakReference<PhotoData> pData;

        public DownloadImageTask(View view, PhotoData photoData, GridViewModel gridViewModel) {
            wView = new WeakReference<>(view);
            pData = new WeakReference<>(photoData);
            wgridViewmodel = new WeakReference<>(gridViewModel);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            if (pData != null && pData.get() != null) {
                try {
                    InputStream in = new URL(Routes.imageUrl.getUrl() + pData.get().getId()).openStream();
                    bitmap = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("###", "exceptioncaught " + e);
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (wView != null && wView.get() != null
                    && pData != null && pData.get() != null
                    && wgridViewmodel != null && wgridViewmodel.get() != null) {
                ImageView imageView = wView.get().findViewById(R.id.imageView);
                TextView textView = wView.get().findViewById(R.id.rowTextview);
                imageView.setImageBitmap(bitmap);
                textView.setText(pData.get().getAuthor());
                wgridViewmodel.get().addBitmapInfoWithId(pData.get().getId(), new BitmapInfo(pData.get().getAuthor(), bitmap));
            }
        }
    }


}
