package com.example.imagepicker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    View libraryCard,favoritesCard,createBookCard,createCategoryCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        libraryCard = findViewById(R.id.cardlibrary);
        favoritesCard = findViewById(R.id.cardfavorite);
        createBookCard = findViewById(R.id.cardaddnewbook);
        createCategoryCard = findViewById(R.id.cardcreatecategory);
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MANAGE_DOCUMENTS};
        for(String permission: permissions){
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        permission)) {
                } else {
                    ActivityCompat.requestPermissions(this,
                            permissions, 0);

                }
            }
        }
        libraryCard.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i = new Intent(MainActivity.this, Library.class);
              startActivity(i);
            }
          });
        favoritesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Favorites.class);
                startActivity(i);
            }
        });
        createBookCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CreateBook.class);
                startActivity(i);
            }
        });
        createCategoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CreateCategory.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){

                for (int i =0; i<grantResults.length;i++){
                    Log.d("tag","onRequestPermissionsResult : permissions"+permissions[i]);
                    Log.d("tag","onRequestPermissionsResult : result "+grantResults[i]);
                }
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.d("tag","PrintHelloWorld : Hello World ");
                }

        }
    }
}