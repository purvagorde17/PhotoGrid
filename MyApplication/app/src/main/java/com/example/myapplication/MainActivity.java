package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = findViewById(R.id.grid);
        List<Integer> images = new ArrayList<>();
        List<String> names = new ArrayList<>();

        images.add(R.drawable.c);
        images.add(R.drawable.d);
        images.add(R.drawable.e);
        images.add(R.drawable.f);
        images.add(R.drawable.c);
        images.add(R.drawable.d);
        images.add(R.drawable.e);
        images.add(R.drawable.f);

        names.add("mohan");
        names.add("msham");
        names.add("rehan");
        names.add("adil");
        names.add("mohan");
        names.add("msham");
        names.add("rehan");
        names.add("adil");

        Adapter_class adapterClass = new Adapter_class(this,images,names);
        gridView.setAdapter(adapterClass);

    }
}
