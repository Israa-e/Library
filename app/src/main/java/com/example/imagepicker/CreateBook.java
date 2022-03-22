package com.example.imagepicker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class CreateBook extends AppCompatActivity {
    EditText name_input,author_input,pages_input,year_input ;
    Button btnAdd;
    Spinner spinner;
    String ivCoverImagePath;
    long selected_category_id;
    public static byte[] imageContent;
    public static final int PERMISSION_REQUEST_CODE = 1020;
    public static final int PROFILE_IMAGE_REQUEST_CODE = 1202;
    private ImageView ivCoverImage;
    int Select_images_id = 1;
    FloatingActionButton buttonImage;
    private boolean storagePermissionStatus = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_book);
        name_input = findViewById(R.id.etName);
        author_input = findViewById(R.id.edAuthor);
        pages_input = findViewById(R.id.edPage);
        year_input = findViewById(R.id.edYear);
        spinner = findViewById(R.id.spinner);
        btnAdd = findViewById(R.id.btnAdd);
        buttonImage = findViewById(R.id.btnImage);
        ivCoverImage = findViewById(R.id.imageView);

        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent,100);

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySqLiteHolder myDB = new MySqLiteHolder(CreateBook.this);
                String name = name_input.getText().toString();
                String author = author_input.getText().toString();
                String year = year_input.getText().toString();
                String page = pages_input.getText().toString();
         if(selected_category_id<=0){
             Toast.makeText(CreateBook.this,"There is no category",Toast.LENGTH_SHORT).show();
         }else if(name.isEmpty()){
                name_input.setError("Can't be empty");
             Toast.makeText(CreateBook.this,"Name can't be empty",Toast.LENGTH_SHORT).show();
         }else if(author.isEmpty()){
                author_input.setError("Can't be empty");
             Toast.makeText(CreateBook.this,"Author can't be empty",Toast.LENGTH_SHORT).show();
         }else if(year.isEmpty() ){
                year_input.setError("Can't be empty");
             Toast.makeText(CreateBook.this,"year can't be empty",Toast.LENGTH_SHORT).show();
         }else if(year.length()<=3 ){
                year_input.setError("Can't be less than 3");
             Toast.makeText(CreateBook.this,"year be less than 3",Toast.LENGTH_SHORT).show();
         }else if(page.isEmpty()){
                pages_input.setError("Can't be empty");
             Toast.makeText(CreateBook.this,"page can't be empty",Toast.LENGTH_SHORT).show();
         }else if(imageContent == null){
             Toast.makeText(CreateBook.this, "The image can't be empty", Toast.LENGTH_SHORT).show();
         }else {
          boolean result=myDB.addBook(imageContent,name, author,Integer.parseInt(year), Integer.parseInt(page),selected_category_id,false);
          createBookClass createBookClass= new createBookClass(name, author,Integer.parseInt(year), Integer.parseInt(page),selected_category_id,false);
          createBookClass.setImages(imageContent);
          if (result != false) {
              Toast.makeText(CreateBook.this, "Added Successfully", Toast.LENGTH_SHORT).show();
           finish();
             } else {
             Toast.makeText(CreateBook.this, "Something error", Toast.LENGTH_SHORT).show();
            }
            }}
        });


        try{
        MySqLiteHolder myDB = new MySqLiteHolder(this);
        ArrayList<CreateCategoryClass> cc = myDB.readAllDataCategory();
        ArrayAdapter<CreateCategoryClass> dataAdapter = new ArrayAdapter<CreateCategoryClass>(this,android.R.layout.simple_spinner_item, cc);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Object Cate = parent.getItemAtPosition(position);
                    CreateCategoryClass ca= (CreateCategoryClass) Cate;
                    selected_category_id = ca.getCategoryId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
             }catch (Exception e){
            Log.d("tag","Exception");
                  }



    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED ) {
                storagePermissionStatus = false;
            } else {
                storagePermissionStatus = true;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ivCoverImage.setImageBitmap(bitmap);
                imageContent = getBytes(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public  byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
        return stream.toByteArray();

    }
}