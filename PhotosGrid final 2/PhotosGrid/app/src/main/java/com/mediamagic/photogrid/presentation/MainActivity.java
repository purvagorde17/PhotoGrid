package com.mediamagic.photogrid.presentation;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.mediamagic.photogrid.R;
import com.mediamagic.photogrid.model.PhotoData;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    GridViewModel gridViewModel;
    LiveData<List<PhotoData>> photoDataListLiveData;

    GridView gridView;
    PhotoGridAdapter photoGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.grid);
        gridViewModel = new ViewModelProvider(this).get(GridViewModel.class);
        photoDataListLiveData = gridViewModel.getPhotoRepository().getPhotoDataListLiveData();
        if (photoDataListLiveData.getValue() != null) {
            photoGridAdapter = new PhotoGridAdapter(MainActivity.this, photoDataListLiveData.getValue(), gridViewModel);
            gridView.setAdapter(photoGridAdapter);
        }
        photoDataListLiveData.observe(this, new Observer<List<PhotoData>>() {
                    @Override
                    public void onChanged(List<PhotoData> photoDataList) {
                        Log.d("####", "PhotoData onchanged called");
                        photoGridAdapter = new PhotoGridAdapter(MainActivity.this, photoDataList, gridViewModel);
                        gridView.setAdapter(photoGridAdapter);
                    }
                }
        );
        gridViewModel.getPhotoRepository()
                .fetchPhotoDataList();

    }
}
