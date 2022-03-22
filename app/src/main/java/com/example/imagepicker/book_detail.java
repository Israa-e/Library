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

public class book_detail extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView empty_imageView;
    ImageView imageView;
    TextView no_data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail);
        recyclerView = findViewById(R.id.recyclerview_book);
        empty_imageView=findViewById(R.id.empty_imageView_book);
        imageView= findViewById(R.id.imageViewDetail);

        Intent intent = getIntent();

        long category_id = (long) intent.getSerializableExtra("category_id");
        String category_name=(String) intent.getSerializableExtra("category_name");
        setTitle(category_name);
        no_data=findViewById(R.id.no_data_book);
        try{
        MySqLiteHolder myDB = new MySqLiteHolder(this);
        ArrayList<createBookClass> cc= myDB.readAllDataOfCategory(category_id);
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
