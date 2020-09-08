package com.huawenli.contentprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    ListView mlistview;

    ArrayAdapter<String> mAdpter;

    List<String> mContactsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        //实现效果，从通讯录中获取姓名和电话，并用listview显示
//        ListView contactview = findViewById(R.id.contacts_view);
//        mAdpter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mContactsList);
//        contactview.setAdapter(mAdpter);
//
//        //动态获取权限
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
//        }else{
//            readContact();
//        }

        //测试查询，通过自定义的ContentProvider
        Button query_btn = findViewById(R.id.query_btn);
        query_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.huawenli.datastorage.provider/book");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                Log.i("MainActivity","cursor = "+cursor);
                if (cursor!= null){
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("pages"));
                        Log.i("MainActivity","name = "+ name + "pages = "+ pages +
                                "price = "+price + "author = "+ author);
                    }
                }
                cursor.close();

            }
        });



    }

    private void readContact() {
        Cursor cursor;
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                mContactsList.add(name + "\n" + number );
            }
            mAdpter.notifyDataSetChanged();
        }
        if (cursor!= null){
            cursor.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readContact();
                }else{
                    Toast.makeText(this, "You denied the permission!", Toast.LENGTH_SHORT).show();;
                }
                break;
            default:
        }
    }
}
