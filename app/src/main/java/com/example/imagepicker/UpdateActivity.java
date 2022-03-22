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

public class UpdateActivity extends AppCompatActivity {

    EditText name,author,year,pages;
    Spinner spinner;
    Button button;
    long selected_category_id;
    ArrayList<CreateCategoryClass> cc;
    ImageView imageView;
    public static byte[] imageContent;
    FloatingActionButton buttonImage;
    private boolean storagePermissionStatus = false;
    public static final int PERMISSION_REQUEST_CODE = 1020;
    public static final int PROFILE_IMAGE_REQUEST_CODE = 1202;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
         name = findViewById(R.id.NameUpdate);
         author = findViewById(R.id.AuthorUpdate);
         year = findViewById(R.id.YearUpdate);
         pages = findViewById(R.id.PageUpdate);
         spinner = findViewById(R.id.spinnerUpdate);
         button =findViewById(R.id.btnUpdate);
         imageView = findViewById(R.id.imageViewUpdate);
        buttonImage =findViewById(R.id.btnImageUpdate);
        Intent intent = getIntent();
        createBookClass book_update = (createBookClass) intent.getSerializableExtra("book_update");

        name.setText(book_update.getName());
        author.setText(book_update.getAuthor());
        year.setText(String.valueOf(book_update.getYear()));
        pages.setText(String.valueOf(book_update.getPages()));
        Bitmap bitmap = BitmapFactory.decodeByteArray(book_update.getImages(),0,book_update.getImages().length);
        imageView.setImageBitmap(bitmap);
        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,100);

            }
        });

        try{
            MySqLiteHolder myDB = new MySqLiteHolder(this);
             cc = myDB.readAllDataCategory();
            ArrayAdapter<CreateCategoryClass> dataAdapter = new ArrayAdapter<CreateCategoryClass>(this,android.R.layout.simple_spinner_item, cc);
            spinner.setAdapter(dataAdapter);
            int selected_pos = 0;
            for(int i= 0;i< dataAdapter.getCount();i++){
                CreateCategoryClass cat = dataAdapter.getItem(i);
                if (cat.getCategoryId() == book_update.getId_category()){
                    selected_pos = i;
                }
            }
            Log.d("Selected",selected_pos+"");
            spinner.setSelection(selected_pos);
            spinner.setSelected(true);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameUpdated = name.getText().toString();
                String authorUpdated = author.getText().toString();
                String yearUpdated = year.getText().toString();
                String pagesUpdated = pages.getText().toString();

                MySqLiteHolder myDB = new MySqLiteHolder(UpdateActivity.this);

                if(imageContent == null){
                    imageContent = book_update.getImages();
                }
                long result=myDB.updateAllData(book_update.getId(),imageContent,nameUpdated,authorUpdated,Integer.parseInt(yearUpdated),Integer.parseInt(pagesUpdated),selected_category_id);
                if(result == -1 ){
                    Toast.makeText(UpdateActivity.this, "Failed To Update.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UpdateActivity.this, "Update successfully.", Toast.LENGTH_SHORT).show();
                    createBookClass create = new createBookClass(nameUpdated,authorUpdated,Integer.parseInt(yearUpdated),Integer.parseInt(pagesUpdated),selected_category_id,false);
                    create.setImages(imageContent);
                    Intent intent1 = new Intent(UpdateActivity.this, EditBook.class);
                    intent1.putExtra("Book",create);
                    startActivity(intent1);
                    finish();
                }


            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED /*-1*/) {
                //User denied our app to access storage (Ask him again)
                storagePermissionStatus = false;
            } else {
                //User granted our app to read storage
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
                imageView.setImageBitmap(bitmap);
                imageContent = getBytes(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }}


    public  byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream);
        return stream.toByteArray();

    }

}