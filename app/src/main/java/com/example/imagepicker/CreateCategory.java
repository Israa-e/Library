package com.example.imagepicker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateCategory extends AppCompatActivity {
    EditText editName;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        button=findViewById(R.id.buttonCreate);
        editName = findViewById(R.id.editTextTextPersonName);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = editName.getText().toString();
                if(categoryName.isEmpty()){
                    Toast.makeText(CreateCategory.this, "Can't be empty", Toast.LENGTH_SHORT).show();
                    editName.setError("Can't be empty");
                }else {
                    MySqLiteHolder myDB = new MySqLiteHolder(CreateCategory.this);
                    boolean result = myDB.addCategory(categoryName);
                    if (result != false) {
                        Toast.makeText(CreateCategory.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(CreateCategory.this, "Something error", Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });
    }
}