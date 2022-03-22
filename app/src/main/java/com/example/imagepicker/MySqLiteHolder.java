package com.example.imagepicker;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MySqLiteHolder extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "BookLibrary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "books";
    private static final String TABLE_NAME_CATEGORY = "categories";
    private static final String COLUMN_ID_CATEGORY = "id";
    private static final String COLUMN_CATEGORY = "name";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "name";
    private static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_PAGES = "pages";
    private static final String COLUMN_ID_FOREIGN = "category_id";
    private static final String IMAGE="image";
    private static final String FAVORITES="favorites";


    public MySqLiteHolder(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE "+ TABLE_NAME_CATEGORY + "(" + COLUMN_ID_CATEGORY +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                COLUMN_CATEGORY + " TEXT NOT NULL); ";
        db.execSQL(query);

        String query1 = " CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                IMAGE +" BLOE NOT NULL" +
                " ,"+
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_AUTHOR + " TEXT , " +
                COLUMN_YEAR + " NUMBER , " +
                COLUMN_PAGES + " NUMBER , "+
                FAVORITES + " BOOLEAN , "+
                COLUMN_ID_FOREIGN + " INTEGER NOT NULL, "+
                " FOREIGN KEY("+COLUMN_ID_FOREIGN+") REFERENCES category("+COLUMN_ID_CATEGORY+"))";
        db.execSQL(query1);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_CATEGORY);
        onCreate(db);
    }
    public boolean addCategory(String name){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CATEGORY,name);
        db.insert(TABLE_NAME_CATEGORY, null, cv);
        return  true;
    }


    public boolean addBook(byte [] bookImagePath, String title, String author, int year, int pages, long category_id, boolean favorites) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(IMAGE, bookImagePath);
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_YEAR, year);
        cv.put(COLUMN_PAGES, pages);
        cv.put(COLUMN_ID_FOREIGN, category_id);
        cv.put(FAVORITES,favorites);
        db.insert(TABLE_NAME,null,cv);

        return true;
    }
//ما الها شغل
    ArrayList<createBookClass> readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<createBookClass> book = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int x = cursor.getColumnIndex("id");
        int y = cursor.getColumnIndex("name");
        int z = cursor.getColumnIndex("author");
        int yea = cursor.getColumnIndex("year");
        int pag = cursor.getColumnIndex("pages");
        int imagePathIndex = cursor.getColumnIndex("image");
       int fav =cursor.getColumnIndex(FAVORITES);
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(x);
            String name = cursor.getString(y);
            byte[] imagePath = cursor.getBlob(imagePathIndex);
            String author = cursor.getString(z);
            int year = cursor.getInt(yea);
            int pages = cursor.getInt(pag);
            long id_category = cursor.getLong(5);
            boolean favorite = cursor.getInt(fav)>0;
            createBookClass s= new createBookClass(name,author,year,pages,id_category,favorite);
            s.setId(id);
            s.setImages(imagePath);
            book.add(s);
            cursor.moveToNext();
        }
        return  book;
    }

    public byte [] images(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,new String[]{IMAGE,},null,null,null,null,COLUMN_ID);
        if(cursor.moveToNext()){
            @SuppressLint("Range") byte [] blob= cursor.getBlob(cursor.getColumnIndex(IMAGE));
            cursor.close();
            return blob;
        }
        cursor.close();
        return null;
    }


    ArrayList<createBookClass> readAllDataOfCategory(long category_id) {
        String query = "SELECT * FROM " + TABLE_NAME+" WHERE "+COLUMN_ID_FOREIGN+" = ?";
        String[] conditions = {String.valueOf(category_id)};
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<createBookClass> book = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, conditions);
        cursor.moveToFirst();
        int x = cursor.getColumnIndex("id");
        int y = cursor.getColumnIndex("name");
        int z = cursor.getColumnIndex("author");
        int yea = cursor.getColumnIndex("year");
        int pag = cursor.getColumnIndex("pages");
        int image_path = cursor.getColumnIndex("image");
        int cat_id = cursor.getColumnIndex(COLUMN_ID_FOREIGN);
        int fav = cursor.getColumnIndex(FAVORITES);

        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(x);
            byte[] image = cursor.getBlob(image_path);
            String name = cursor.getString(y);
            String author = cursor.getString(z);
            int year = cursor.getInt(yea);
            int pages = cursor.getInt(pag);
            long id_category = cursor.getLong(cat_id);
            boolean favorite = cursor.getInt(fav)>0;
            createBookClass s= new createBookClass( name,author,year,pages,id_category,favorite);
            s.setId(id);
            s.setImages(image);
            book.add(s);
            cursor.moveToNext();
        }
        return  book;
    }
    ArrayList<createBookClass> fetchFavBooks() {
        String query = "SELECT * FROM " + TABLE_NAME+" WHERE "+FAVORITES+" = ?";
        String[] conditions = {String.valueOf(1)};
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<createBookClass> book = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, conditions);
        cursor.moveToFirst();
        int x = cursor.getColumnIndex("id");
        int y = cursor.getColumnIndex("name");
        int z = cursor.getColumnIndex("author");
        int yea = cursor.getColumnIndex("year");
        int pag = cursor.getColumnIndex("pages");
        int image_path = cursor.getColumnIndex("image");
        int cat_id = cursor.getColumnIndex(COLUMN_ID_FOREIGN);
        int fav = cursor.getColumnIndex(FAVORITES);
        while (!cursor.isAfterLast()) {
            long id = cursor.getLong(x);
            byte [] image = cursor.getBlob(image_path);
            String name = cursor.getString(y);
            String author = cursor.getString(z);
            int year = cursor.getInt(yea);
            int pages = cursor.getInt(pag);
            long id_category = cursor.getLong(cat_id);
            boolean favorite = cursor.getInt(fav)>0;

            createBookClass s= new createBookClass( name,author,year,pages,id_category,favorite);
            s.setId(id);
            s.setImages(image);
            book.add(s);

            cursor.moveToNext();
        }
        return  book;
    }

    ArrayList<CreateCategoryClass> readAllDataCategory() {
        String query = "SELECT * FROM " + TABLE_NAME_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<CreateCategoryClass> book = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        int x = cursor.getColumnIndex("number");
        while (!cursor.isAfterLast()) {
            long id = cursor.getInt(0);
            String name = cursor.getString(1);

            CreateCategoryClass s = new CreateCategoryClass( name);
            s.setCategoryId(id);
            book.add(s);

            cursor.moveToNext();
        }
        return  book;

    }
    long  updateAllData(long book_id,byte [] image,String title, String author, int year, int pages, long category_id){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(IMAGE,image);

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_YEAR, year);
        cv.put(COLUMN_PAGES, pages);
        cv.put(COLUMN_ID_FOREIGN, category_id);
        long result= db.update(TABLE_NAME,cv,"id=?",new String[]{String.valueOf(book_id)});

        return result;
    }

    long  deleteOneRow(long row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result= db.delete(TABLE_NAME,"id=?",new String[]{String.valueOf(row_id)});
       return result;
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_NAME_CATEGORY);
        db.execSQL("DELETE FROM "+ TABLE_NAME);
    }
    long ToggleFavBookById(long book_id , boolean fav){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FAVORITES, fav);
        long result= db.update(TABLE_NAME,cv,"id=?",new String[]{String.valueOf(book_id)});
        return result;
    }
}
