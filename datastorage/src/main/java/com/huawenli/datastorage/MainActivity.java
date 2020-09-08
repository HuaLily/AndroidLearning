package com.huawenli.datastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static java.lang.System.out;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button createDataBaseBtn;
    private Button insertBtn;
    private Button deleteBtn;
    private Button updateBtn;
    private Button selectBtn;

    private MydatabaseHelper mydatabaseHelper;

    @SuppressLint("Recycle")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.text);
//        String inputText = load();
//        if (!TextUtils.isEmpty(inputText)){
//            editText.setText(inputText);
//            editText.setSelection(inputText.length());//光标移动到文本末尾
//            Toast.makeText(this, "读取成功", Toast.LENGTH_SHORT).show();
//        }

        mydatabaseHelper = new MydatabaseHelper(this, "Book", null, 2);


        createDataBaseBtn  = findViewById(R.id.createDataBase);
        createDataBaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = mydatabaseHelper.getWritableDatabase();

                Cursor cursor = db.rawQuery("select * from Book",null);
                if (cursor!=null){
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        Log.i("huawenli","name = "+ name + "pages = "+ pages +
                                "price = "+price + "author = "+ author);
                    }
                }
                cursor.close();


            }
        });
        insertBtn  = findViewById(R.id.insertData);
        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = mydatabaseHelper.getWritableDatabase();
                db.execSQL("insert into Book (name,author,price,pages) values(?,?,?,?)",new String[]{"第一行代码","作者1","1","1"});
                db.execSQL("insert into Book (name, author,price,pages) values(?,?,?,?)",new String[]{"第二行代码","作者2","22","22"});
                ContentValues values = new ContentValues();
                values.put("name","333");
                values.put("author","作者3");
                values.put("price","333");
                values.put("pages","333");
                db.insert("Book" , null, values);
                values.clear();
                values.put("name","4444");
                values.put("author","作者4");
                values.put("price","4444");
                values.put("pages","4444");
                db.insert("Book" , null, values);
            }
        });

        deleteBtn  = findViewById(R.id.deleteData);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = mydatabaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
               // contentValues.put("author","333");
               // db.delete("Book", "name = ?", new String[]{"333"});
                db.execSQL("delete from Book");

            }
        });

        updateBtn  = findViewById(R.id.updateData);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = mydatabaseHelper.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("author","4444");
                db.update("Book", contentValues,"name = ?",new String[]{"4444"});
            }
        });

        selectBtn  = findViewById(R.id.selectData);
        selectBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = mydatabaseHelper.getWritableDatabase();
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor != null){
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        Log.i("huawenli","name = "+ name + "pages = "+ pages +
                                "price = "+price + "author = "+ author);
                    }
                }cursor.close();

                Cursor cursor2 = db.query("Category", null,
                        null,
                        null,
                        null,
                        null,
                        null);
                if (cursor2.moveToFirst()){
                    while (cursor2.moveToNext()){
                        String category_name =
                                cursor2.getString(cursor.getColumnIndex("category_name"));
                        int category_code =
                                cursor2.getInt(cursor.getColumnIndex("category_code"));
                        Log.i("huawenli","category_name = "+ category_name +
                                "category_code = "+ category_code);
                    }
                }cursor2.close();

            }
        });



    }


    //从文件中读取数据
    private String load() {
        StringBuilder builder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            FileInputStream data = openFileInput("data");
            bufferedReader = new BufferedReader(new InputStreamReader(data));
            String line =  "";
            while((line = bufferedReader.readLine())!= null){
                builder.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // String inputString = editText.getText().toString();
       // save(inputString);
    }



    //保存输入的内容到文件中
    private void save(String inputString){
        BufferedWriter bufferedWriter = null;
        try {
            FileOutputStream out = openFileOutput("data", Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(inputString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedWriter != null){
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }





}
