package com.huawenli.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MydatabaseHelper extends SQLiteOpenHelper {
    
    public static final String CREATE_BOOK = "create table Book("
            +"id integer primary key autoincrement,"
            + "author text,"
            +"price real,"
            +"pages integer,"
            +"name text)";

    public static final String CREATE_CATEGORY = "create table Category("
            +"id integer primary key autoincrement,"
            + "category_name text,"
            +"category_code integer)";

    private Context mContext;

    public MydatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BOOK);
        sqLiteDatabase.execSQL(CREATE_CATEGORY);
      //  Toast.makeText(mContext, "create succeeded", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int olderVersion, int newVersion) {
        if (olderVersion <= 1){
           // sqLiteDatabase.execSQL("drop table if exists Book");
           // sqLiteDatabase.execSQL("insert into Book (name, author,price,pages) values(?,?,?,?)",new String[]{"第一行代码","作者","19","500"});
            sqLiteDatabase.execSQL(CREATE_BOOK);
        }
        sqLiteDatabase.execSQL("drop table if exists Book");
        onCreate(sqLiteDatabase);
    }
}
