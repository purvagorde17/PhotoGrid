package com.mediamagic.photogrid.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.mediamagic.photogrid.R;
import com.mediamagic.photogrid.model.PhotoData;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridViewModel gridViewModel;
    private LiveData<List<PhotoData>> photoDataListLiveData;
    private GridView gridView;
    private GridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.grid);
        gridViewModel = ViewModelProviders.of(MainActivity.this).get(GridViewModel.class);
        photoDataListLiveData = gridViewModel.getPhotoRepository().getPhotoDataListLiveData();
        if (photoDataListLiveData.getValue() == null) {
            photoDataListLiveData.observe(MainActivity.this, new Observer<List<PhotoData>>() {
                @Override
                public void onChanged(List<PhotoData> photoDataList) {
                    Log.d("###", "onChanged called for photoDataLiveData: ");
                    gridAdapter = new GridAdapter(MainActivity.this, photoDataList, gridViewModel);
                    gridView.setAdapter(gridAdapter);
                }
            });
            gridViewModel.getPhotoRepository().fetchPhotoDataList();
        } else {
            Log.d("###", "photoDataListLiveData already has a value: ");
            gridAdapter = new GridAdapter(MainActivity.this, photoDataListLiveData.getValue(), gridViewModel);
            gridView.setAdapter(gridAdapter);
        }
    }
}
