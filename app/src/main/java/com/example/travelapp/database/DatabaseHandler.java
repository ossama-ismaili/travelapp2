package com.example.travelapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.travelapp.R;
import com.example.travelapp.model.DirectionsData;
import com.example.travelapp.model.FavoritesData;
import com.example.travelapp.model.RecentPlacesData;
import com.example.travelapp.model.TopPlacesData;
import com.example.travelapp.model.UsersData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "travelapp.db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_TABLE_1 = "CREATE TABLE " + DirectionsEntry.TABLE_NAME + " ("
                + DirectionsEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," // THIS AUTOMATICALLY INCREMENTS THE ID BY 1
                + DirectionsEntry.COLUMN_PLACE_NAME + " TEXT NOT NULL, "
                + DirectionsEntry.COLUMN_COUNTRY_NAME + " TEXT NOT NULL, "
                + DirectionsEntry.COLUMN_PRICE + " TEXT NOT NULL, "
                + DirectionsEntry.COLUMN_RATING + " DECIMAL NOT NULL, "
                + DirectionsEntry.COLUMN_IMAGE  + " INTEGER, "
                + DirectionsEntry.COLUMN_CREATED_AT + " DATETIME NOT NULL, "
                + DirectionsEntry.COLUMN_UPDATED_AT + " DATETIME NOT NULL);";
        db.execSQL(SQL_TABLE_1);

        String SQL_TABLE_2 = "CREATE TABLE " + UsersEntry.TABLE_NAME + " ("
                + UsersEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," // THIS AUTOMATICALLY INCREMENTS THE ID BY 1
                + UsersEntry.COLUMN_USERNAME + " TEXT NOT NULL, "
                + UsersEntry.COLUMN_FIRST_NAME + " TEXT NOT NULL, "
                + UsersEntry.COLUMN_LAST_NAME + " TEXT NOT NULL, "
                + UsersEntry.COLUMN_PASSWORD + " TEXT NOT NULL, "
                + UsersEntry.COLUMN_CREATED_AT + " DATETIME NOT NULL, "
                + UsersEntry.COLUMN_UPDATED_AT + " DATETIME NOT NULL);";
        db.execSQL(SQL_TABLE_2);

        String SQL_TABLE_3 = "CREATE TABLE " + FavoritesEntry.TABLE_NAME + " ("
                + FavoritesEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," // THIS AUTOMATICALLY INCREMENTS THE ID BY 1
                + FavoritesEntry.COLUMN_USER_ID + " INTEGER NOT NULL, "
                + FavoritesEntry.COLUMN_DIRECTION_ID + " INTEGER NOT NULL, "
                + FavoritesEntry.COLUMN_CREATED_AT + " DATETIME NOT NULL, "
                + FavoritesEntry.COLUMN_UPDATED_AT + " DATETIME NOT NULL, "
                + "FOREIGN KEY("+FavoritesEntry.COLUMN_USER_ID+") REFERENCES "+ UsersEntry.TABLE_NAME+"("+ UsersEntry.ID+"), "
                + "FOREIGN KEY("+FavoritesEntry.COLUMN_DIRECTION_ID+") REFERENCES "+DirectionsEntry.TABLE_NAME+"("+DirectionsEntry.ID+") );";
        db.execSQL(SQL_TABLE_3);

        String SQL_TABLE_4 = "CREATE TABLE " + DirectionsUsersEntry.TABLE_NAME + " ("
                + DirectionsUsersEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," // THIS AUTOMATICALLY INCREMENTS THE ID BY 1
                + DirectionsUsersEntry.COLUMN_USER_ID + " INTEGER NOT NULL, "
                + DirectionsUsersEntry.COLUMN_DIRECTION_ID + " INTEGER NOT NULL, "
                + DirectionsUsersEntry.COLUMN_START_DATE + " DATETIME NOT NULL, "
                + DirectionsUsersEntry.COLUMN_END_DATE + " DATETIME NOT NULL, "
                + DirectionsUsersEntry.COLUMN_CREATED_AT + " DATETIME NOT NULL, "
                + DirectionsUsersEntry.COLUMN_UPDATED_AT + " DATETIME NOT NULL, "
                + "FOREIGN KEY("+FavoritesEntry.COLUMN_USER_ID+") REFERENCES "+ UsersEntry.TABLE_NAME+"("+ UsersEntry.ID+"), "
                + "FOREIGN KEY("+FavoritesEntry.COLUMN_DIRECTION_ID+") REFERENCES "+DirectionsEntry.TABLE_NAME+"("+DirectionsEntry.ID+") );";
        db.execSQL(SQL_TABLE_4);

        insertDummyData(db);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DirectionsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UsersEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FavoritesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DirectionsUsersEntry.TABLE_NAME);
        onCreate(db);
    }

    public List<DirectionsData> getAllDirections() {
        List<DirectionsData> directionsList= new ArrayList<DirectionsData>();
        String selectQuery= "SELECT * FROM directions";
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DirectionsData data= new DirectionsData(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getDouble(4),cursor.getInt(5));
                directionsList.add(data);
            }while(cursor.moveToNext());
        }
        return directionsList;
    }

    public List<TopPlacesData> getAllTopPlaces() {
        List<TopPlacesData> topPlacesList= new ArrayList<TopPlacesData>();
        String selectQuery= "SELECT * FROM directions ORDER BY rating DESC LIMIT 3";
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TopPlacesData data= new TopPlacesData( cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getDouble(4),cursor.getInt(5));
                topPlacesList.add(data);
            }while(cursor.moveToNext());
        }
        return topPlacesList;
    }

    public List<FavoritesData> getAllFavorites(int userId){
        List<FavoritesData> favoritesList=new ArrayList<FavoritesData>();
        String selectQuery="SELECT favorites.id, directions.place_name, directions.country_name, directions.price, directions.rating, directions.image FROM favorites " +
                            "JOIN users ON users.id=favorites.user_id " +
                            "JOIN directions ON directions.id=favorites.direction_id WHERE users.id="+ userId;
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                FavoritesData data= new FavoritesData( cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getDouble(4),cursor.getInt(5));
                favoritesList.add(data);
            }while(cursor.moveToNext());
        }
        return favoritesList;
    }

    public int favoritesCount(int dirId) {
        String countQuery = "SELECT  * FROM " + FavoritesEntry.TABLE_NAME+" WHERE direction_id="+dirId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean insertFavorite(int userId, int dirId){
        if(favoritesCount(dirId)>=1) return false;
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(FavoritesEntry.COLUMN_USER_ID,userId);
        values.put(FavoritesEntry.COLUMN_DIRECTION_ID,dirId);
        values.put(FavoritesEntry.COLUMN_CREATED_AT,formatDate(LocalDateTime.now()));
        values.put(FavoritesEntry.COLUMN_UPDATED_AT,formatDate(LocalDateTime.now()));
        db.insert(FavoritesEntry.TABLE_NAME,null,values);
        db.close();
        return true;
    }

    public void deleteFavorite(int favId) {
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(FavoritesEntry.TABLE_NAME, FavoritesEntry.ID + " = ?",new String[] { String.valueOf(favId) });
        db.close();
    }

    public List<RecentPlacesData> getAllRecentPlaces() {
        List<RecentPlacesData> recentPlacesList= new ArrayList<RecentPlacesData>();
        String selectQuery= "SELECT * FROM directions ORDER BY created_at DESC LIMIT 5";
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                RecentPlacesData data= new RecentPlacesData( cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getDouble(4),cursor.getInt(5));
                recentPlacesList.add(data);
            }while(cursor.moveToNext());
        }
        return recentPlacesList;
    }

    public DirectionsData getDirection(int dirId){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor;
        cursor= db.query(DirectionsEntry.TABLE_NAME, new String[] { DirectionsEntry.ID, DirectionsEntry.COLUMN_PLACE_NAME, DirectionsEntry.COLUMN_COUNTRY_NAME, DirectionsEntry.COLUMN_PRICE, DirectionsEntry. COLUMN_RATING, DirectionsEntry.COLUMN_IMAGE}, DirectionsEntry.ID + "=?",new String[] { String.valueOf(dirId) }, null, null, null, null);
        if (cursor!= null) cursor.moveToFirst();
        DirectionsData direction=new DirectionsData(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getDouble(4), cursor.getInt(5));
        return direction;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertDirection(DirectionsData data){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DirectionsEntry.COLUMN_PLACE_NAME,data.getPlaceName());
        values.put(DirectionsEntry.COLUMN_COUNTRY_NAME,data.getCountryName());
        values.put(DirectionsEntry.COLUMN_PRICE,data.getPrice());
        values.put(DirectionsEntry.COLUMN_RATING,data.getRating());
        values.put(DirectionsEntry.COLUMN_IMAGE,data.getImageUrl());
        values.put(DirectionsEntry.COLUMN_CREATED_AT,formatDate(LocalDateTime.now()));
        values.put(DirectionsEntry.COLUMN_UPDATED_AT,formatDate(LocalDateTime.now()));
        db.insert(DirectionsEntry.TABLE_NAME,null,values);
        db.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateDirection(DirectionsData data, int dirId){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DirectionsEntry.COLUMN_PLACE_NAME,data.getPlaceName());
        values.put(DirectionsEntry.COLUMN_COUNTRY_NAME,data.getCountryName());
        values.put(DirectionsEntry.COLUMN_PRICE,data.getPrice());
        values.put(DirectionsEntry.COLUMN_RATING,data.getRating());
        values.put(DirectionsEntry.COLUMN_IMAGE,data.getImageUrl());
        values.put(DirectionsEntry.COLUMN_UPDATED_AT,formatDate(LocalDateTime.now()));
        db.update(DirectionsEntry.TABLE_NAME,values,DirectionsEntry.ID+" = ?", new String[] { String.valueOf(dirId) });
        db.close();
    }

    public void deleteDirection(int dirId) {
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(DirectionsEntry.TABLE_NAME, DirectionsEntry.ID + " = ?",new String[] { String.valueOf(dirId) });
        db.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertUser(UsersData data){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(UsersEntry.COLUMN_USERNAME,data.getUsername());
        values.put(UsersEntry.COLUMN_FIRST_NAME,data.getFirstName());
        values.put(UsersEntry.COLUMN_LAST_NAME,data.getLastName());
        values.put(UsersEntry.COLUMN_PASSWORD,data.getPassword());
        values.put(UsersEntry.COLUMN_CREATED_AT,formatDate(LocalDateTime.now()));
        values.put(UsersEntry.COLUMN_UPDATED_AT,formatDate(LocalDateTime.now()));
        db.insert(UsersEntry.TABLE_NAME,null,values);
        db.close();
    }

    public boolean checkUser(String username, String password){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor;
        cursor= db.query(UsersEntry.TABLE_NAME, new String[] { UsersEntry.COLUMN_USERNAME, UsersEntry.COLUMN_PASSWORD }, UsersEntry.COLUMN_USERNAME + "=?",new String[] { String.valueOf(username) }, null, null, null, null);
        if (cursor!= null) cursor.moveToFirst();
        if(password.equals(cursor.getString(1))){
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updatePassword(int userId, String newPassword){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(UsersEntry.COLUMN_PASSWORD,newPassword);
        values.put(UsersEntry.COLUMN_UPDATED_AT,formatDate(LocalDateTime.now()));
        db.update(UsersEntry.TABLE_NAME,values,UsersEntry.ID+" = ?", new String[] { String.valueOf(userId) });
        db.close();
    }


    public int getUserId(String username){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor;
        cursor= db.query(UsersEntry.TABLE_NAME, new String[] { UsersEntry.ID, UsersEntry.COLUMN_USERNAME }, UsersEntry.COLUMN_USERNAME + "=?",new String[] { String.valueOf(username) }, null, null, null, null);
        if (cursor!= null) cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public UsersData getUser(int id){
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor;
        cursor= db.query(UsersEntry.TABLE_NAME, new String[] { UsersEntry.ID, UsersEntry.COLUMN_USERNAME, UsersEntry.COLUMN_FIRST_NAME, UsersEntry.COLUMN_LAST_NAME, UsersEntry. COLUMN_PASSWORD}, UsersEntry.ID + "=?",new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor!= null) cursor.moveToFirst();
        UsersData user=new UsersData(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return user;
    }

    public int bookingCount(int dirId) {
        String countQuery = "SELECT  * FROM " + DirectionsUsersEntry.TABLE_NAME+" WHERE direction_id="+dirId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean booking(int userId, int dirId, Date startDate, Date endDate){
        if(bookingCount(dirId)>=1) return false;
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(DirectionsUsersEntry.COLUMN_USER_ID, userId);
        values.put(DirectionsUsersEntry.COLUMN_DIRECTION_ID, dirId);
        values.put(DirectionsUsersEntry.COLUMN_START_DATE, startDate.toString());
        values.put(DirectionsUsersEntry.COLUMN_END_DATE, endDate.toString());
        values.put(DirectionsUsersEntry.COLUMN_CREATED_AT,formatDate(LocalDateTime.now()));
        values.put(DirectionsUsersEntry.COLUMN_UPDATED_AT,formatDate(LocalDateTime.now()));
        db.insert(DirectionsUsersEntry.TABLE_NAME,null,values);
        db.close();
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String formatDate(LocalDateTime dateObj){
        DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fDate = dateObj.format(formatObj);
        return  fDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertDummyData(SQLiteDatabase db){
        List<DirectionsData> directionsList=new ArrayList<DirectionsData>();

        directionsList.add(new RecentPlacesData(1,"Ifrane","Morocco","From $200",3.9,R.drawable.ifrane));
        directionsList.add(new RecentPlacesData(2,"Fes","Morocco","From $300",3.8,R.drawable.fes));
        directionsList.add(new RecentPlacesData(3,"Tetouan","Morocco","From $200",3.5,R.drawable.tetouan));
        directionsList.add(new RecentPlacesData(4,"Tanger","Morocco","From $300",4,R.drawable.tanger));
        directionsList.add(new RecentPlacesData(5,"Meknes","Morocco","From $200",4.1,R.drawable.meknes));
        directionsList.add(new RecentPlacesData(6,"Merzouga","Morocco","From $300",4.2,R.drawable.merzouga));

        directionsList.add(new TopPlacesData(8,"Marrakech","Morocco","$200 - $500",4.9,R.drawable.marrakech));
        directionsList.add(new TopPlacesData(9,"Agadir","Morocco","$200 - $500",4.7, R.drawable.agadir));
        directionsList.add(new TopPlacesData(10,"Essaouira","Morocco","$200 - $500",4.6,R.drawable.essaouira));

        for(DirectionsData data:directionsList) {
            try {
                Thread.sleep(2500);//pour decaler de temps de creation de chaque element
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ContentValues values=new ContentValues();
            values.put(DirectionsEntry.COLUMN_PLACE_NAME,data.getPlaceName());
            values.put(DirectionsEntry.COLUMN_COUNTRY_NAME,data.getCountryName());
            values.put(DirectionsEntry.COLUMN_PRICE,data.getPrice());
            values.put(DirectionsEntry.COLUMN_RATING,data.getRating());
            values.put(DirectionsEntry.COLUMN_IMAGE,data.getImageUrl());
            values.put(DirectionsEntry.COLUMN_CREATED_AT,formatDate(LocalDateTime.now()));
            values.put(DirectionsEntry.COLUMN_UPDATED_AT,formatDate(LocalDateTime.now()));
            db.insert(DirectionsEntry.TABLE_NAME,null,values);
        }
    }
}
