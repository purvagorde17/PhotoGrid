package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter_class extends BaseAdapter {
    private List<Integer>images;
    private List<String> names;
    private Context applicationContext;
    private LayoutInflater inflater;
    public Adapter_class(Context applicationContext, List<Integer> Imageid,List<String>names){
        this.applicationContext = applicationContext;
        this.images = Imageid;
        this.names=names;
        inflater = LayoutInflater.from(applicationContext);
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= inflater.inflate(R.layout.rowlayout,null);
        ImageView img = view.findViewById(R.id.img);
        img.setImageResource(images.get(i));
        TextView name = view.findViewById(R.id.text);
        name.setText(names.get(i));
        return view;
    }
}
