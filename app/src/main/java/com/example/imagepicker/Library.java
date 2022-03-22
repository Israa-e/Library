package com.example.imagepicker;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Library extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView empty_imageView;
    TextView no_data;
    ArrayList<String> id,name;
    LinearLayout myRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        recyclerView = findViewById(R.id.recyclerview_Category);
        empty_imageView=findViewById(R.id.empty_imageView);
        no_data=findViewById(R.id.no_data);
        myRow = findViewById(R.id.myRow);

        try{
        MySqLiteHolder myDB = new MySqLiteHolder(this);
        ArrayList<CreateCategoryClass> cc= myDB.readAllDataCategory();
        if(cc.size() ==0){
            empty_imageView.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }else{
            CustomAdapterCategory adapter = new CustomAdapterCategory(cc,this,this);
            recyclerView.setAdapter(adapter);

        }}catch (Exception e){Log.d("tag","exception");}
        RecyclerView.LayoutManager rlm= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rlm);
       }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1){
            recreate();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.Delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    void confirmDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Delete All ?");
        builder.setMessage("Are you sure yoe want to delete All ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Library.this, "Delete", Toast.LENGTH_SHORT).show();
                MySqLiteHolder myDB = new MySqLiteHolder(Library.this);
                myDB.deleteAllData();
                Intent intent = new Intent(Library.this,Library.class);
                startActivity(intent);
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}

