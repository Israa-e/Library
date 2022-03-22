package com.example.imagepicker;

import java.io.Serializable;

public class CreateCategoryClass implements Serializable {
    private String categoryName;
    private long categoryId;

    @Override
    public String toString() {
        return categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public CreateCategoryClass(String categoryName) {
        this.categoryName = categoryName;
    }
}
