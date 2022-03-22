package com.example.imagepicker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView empty_imageView;
    TextView no_data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        recyclerView = findViewById(R.id.recyclerview_favorites);
        empty_imageView=findViewById(R.id.empty_imageView_fav);
        no_data=findViewById(R.id.no_data_fav);
        Intent intent = getIntent();
        try{
            MySqLiteHolder myDB = new MySqLiteHolder(this);
            ArrayList<createBookClass> cc= myDB.fetchFavBooks();
            if(cc.size() ==0){
                empty_imageView.setVisibility(View.VISIBLE);
                no_data.setVisibility(View.VISIBLE);
            }else{
                customLibrary adapter = new customLibrary(this,this,cc);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }}catch (Exception e) {
            Log.d("tag","exception");
        }
        RecyclerView.LayoutManager rlm= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rlm);
    }

}