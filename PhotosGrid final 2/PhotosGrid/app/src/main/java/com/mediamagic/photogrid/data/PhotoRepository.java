package com.mediamagic.photogrid.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mediamagic.photogrid.R;
import com.mediamagic.photogrid.model.BitmapInfo;
import com.mediamagic.photogrid.model.PhotoData;
import com.mediamagic.photogrid.model.Routes;
import com.mediamagic.photogrid.presentation.GridViewModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PhotoRepository {

    private MutableLiveData<List<PhotoData>> photoDataListLiveData;

    private List<PhotoData> photoDataList;

    public LiveData<List<PhotoData>> getPhotoDataListLiveData() {
        if (photoDataListLiveData == null) {
            photoDataListLiveData = new MutableLiveData<>();
        }
        return photoDataListLiveData;
    }

    public void fetchPhotoDataList() {
        photoDataList = new ArrayList<>();

        if (photoDataListLiveData != null && photoDataListLiveData.getValue() == null) {
            // Load the list and add to livedata when done
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.d("####", "fetching imagedata");
                        StringBuffer response = new StringBuffer("");
                        URL imageDataUrl = new URL(Routes.imagesDataUrl.getUrl());
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(imageDataUrl.openStream()));

                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        Log.d("####", response.length() + "");

                        for (int i = 0; i < response.length(); i++) {
                            StringBuilder number = new StringBuilder("");
                            if (i + 4 <= response.length() && response.substring(i, i + 4).equals("\"id\"")) {
                                while (!Character.isDigit(response.charAt(i++))) ;
                                i--;
                                while (Character.isDigit(response.charAt(i))) {
                                    number.append(response.charAt(i));
                                    i++;
                                }

                                for (; i < response.length(); i++) {
                                    StringBuffer authorName = new StringBuffer("");
                                    if (i + 8 < response.length() && response.substring(i, i + 8).equals("\"author\"")) {
                                        i = i + 8;
                                        while (response.charAt(i++) != '\"') ;
                                        while (response.charAt(i) != '\"') {
                                            authorName.append(response.charAt(i++));
                                        }
                                        PhotoData photoData = new PhotoData(number.toString(), authorName.toString());
                                        photoDataList.add(photoData);
                                        Log.d("####", photoData.toString());
                                        break;
                                    }
                                }
                            }

                        }
                        Log.d("####", "Setting up Live Data");
                        photoDataListLiveData.postValue(photoDataList);
                        in.close();
                    } catch (Exception e) {
                        Log.d("###", "Exception thrown: " + e);
                    }
                }
            }).start();
        }
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private WeakReference<View> wView;
        private WeakReference<GridViewModel> wGridViewModel;
        PhotoData photoData;

        public DownloadImageTask(GridViewModel gridViewModel, View view, PhotoData photoData) {
            this.wView = new WeakReference<>(view);
            this.wGridViewModel = new WeakReference<>(gridViewModel);
            this.photoData = photoData;
        }

        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmap = null;
            try {
                InputStream in = new URL(Routes.imageUrl.getUrl() + photoData.getId()).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            if(wGridViewModel!=null && wGridViewModel.get()!=null) {
                GridViewModel gridViewModel = wGridViewModel.get();
                gridViewModel.addToBitmapInfoHashMap(photoData.getId(), new BitmapInfo(result, photoData.getAuthor()));
            }

            if(wView!=null && wView.get()!=null) {
            View view = wView.get();
                ImageView aImageView = view.findViewById(R.id.imageView);
                TextView aTextView = view.findViewById(R.id.rowTextview);
                aImageView.setImageBitmap(result);
                aTextView.setText(photoData.getAuthor());
            }
        }
    }

}