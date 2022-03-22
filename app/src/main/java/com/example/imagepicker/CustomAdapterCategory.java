package com.example.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterCategory extends RecyclerView.Adapter<CustomAdapterCategory.MyView> {
    ArrayList<CreateCategoryClass> data;
    Activity activity;
    Context context;
    Library Library;

    public CustomAdapterCategory(ArrayList<CreateCategoryClass> data, Activity activity, Library Library) {
        this.data = data;
        this.activity = activity;
        this.Library = Library;
    }


    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("asdasd", "Created");
        View root = LayoutInflater.from(Library).inflate(R.layout.library_recyclerview, parent, false);
        return new MyView(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        final CreateCategoryClass create = data.get(position);
        holder.categoryId_tv.setText(String.valueOf(create.getCategoryId()));
        holder.categoryName_tv.setText(String.valueOf(create.getCategoryName()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("asdasd", "Clicked");
                Intent intent = new Intent(Library, book_detail.class);
                intent.putExtra("category_id",create.getCategoryId());
                intent.putExtra("category_name",create.getCategoryName());
                Library.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        TextView categoryId_tv;
        TextView categoryName_tv;
        View view;

        public MyView(@NonNull View itemView) {
            super(itemView);
            categoryId_tv = itemView.findViewById(R.id.categoryId);
            categoryName_tv = itemView.findViewById(R.id.categoryName);
            view = itemView;
        }
    }
}
