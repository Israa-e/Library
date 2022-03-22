package com.example.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class customLibrary  extends RecyclerView.Adapter<customLibrary.MyViewHolder> {
    Context context;
    Activity activity;
    ArrayList<createBookClass> data_book;

    public customLibrary(Activity activity, Context context,ArrayList<createBookClass> data) {
        this.context = context;
        this.activity = activity;
        this.data_book = data;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root= LayoutInflater.from(context).inflate(R.layout.book_details_recyclerview,parent,false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        createBookClass create =data_book.get(position);

        holder.Book_title.setText(String.valueOf(create.getName()));
        holder.Book_year.setText(String.valueOf(create.getYear()));
        Bitmap bitmap = BitmapFactory.decodeByteArray(create.getImages(),0,create.getImages().length);
        holder.imageView.setImageBitmap(bitmap);

        if (create.isFavourite()) {
            holder.ibFavourite.setImageResource(R.drawable.ic_baseline_favorite_24);
            Intent intent = new Intent(context,Favorites.class);
            intent.putExtra("Favorites",create);

        } else {
            holder.ibFavourite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        }
        holder.ibFavourite.setOnClickListener(view -> {
            MySqLiteHolder myDB = new MySqLiteHolder(context);
            long result = myDB.ToggleFavBookById(create.getId(),!create.isFavourite());
            create.setFavourite(!create.isFavourite());
            notifyDataSetChanged();
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,EditBook.class);
                intent.putExtra("Book",create);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data_book.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Book_title,Book_year;
        View view;
        ImageButton ibFavourite;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Book_title= itemView.findViewById(R.id.nameBook);
            Book_year = itemView.findViewById(R.id.bookYear);
            view= itemView;
            imageView = itemView.findViewById(R.id.imageViewDetail);
            ibFavourite = itemView.findViewById(R.id.ibFavourite);

        }
    }

}
