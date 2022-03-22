package com.example.imagepicker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditBook extends AppCompatActivity {
    createBookClass book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eidt_book);
        Intent intent = getIntent();

        book = (createBookClass) intent.getSerializableExtra("Book");

        TextView name =findViewById(R.id.EditName);
        TextView author =findViewById(R.id.EditAuthor);
        TextView year =findViewById(R.id.EditYear);
        TextView pages =findViewById(R.id.EditPages);
        TextView category =findViewById(R.id.EditCategory);
        Button btnEdit = findViewById(R.id.btnEdit);
        ImageView imageView = findViewById(R.id.imageView2);

        name.setText(book.getName());
        author.setText(book.getAuthor());
        year.setText(String.valueOf(book.getYear()));
        pages.setText(String.valueOf(book.getPages()));
        category.setText(String.valueOf(book.getId_category()));
        Bitmap bitmap = BitmapFactory.decodeByteArray(book.getImages(),0,book.getImages().length);
        imageView.setImageBitmap(bitmap);
        setTitle(book.getName());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EditBook.this,UpdateActivity.class);
                intent1.putExtra("book_update",book);
                startActivity(intent1);


            }
        });

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
        builder.setTitle("Delete ?");
        builder.setMessage("Are you sure yoe want to delete this book ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(EditBook.this, "Delete", Toast.LENGTH_SHORT).show();
                MySqLiteHolder myDB = new MySqLiteHolder(EditBook.this);
                 long result =  myDB.deleteOneRow(book.getId());
                if(result == -1 ){
                    Toast.makeText(EditBook.this, "Failed To Delete.", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EditBook.this, "Deleted successfully.", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(EditBook.this,book_detail.class);
                intent.putExtra("category_id",book.getId_category());
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