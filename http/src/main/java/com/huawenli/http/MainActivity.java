package com.huawenli.http;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class MainActivity extends AppCompatActivity {

    private Button mRequstBtn;

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRequstBtn = findViewById(R.id.send_quest);
        mTextView = findViewById(R.id.show_text);

        mRequstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("huawenli","onClick mRequstBtn");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("huawenli","in running");
                        sendRequest();
                    }
                }).start();
            }
        });


    }

    private void sendRequest() {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://www.baidu.com/");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(8000);
            urlConnection.setReadTimeout(8000);
            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                builder.append(line);
            }
            showResponse(builder.toString());


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null){
                try{
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            if (urlConnection != null){
                urlConnection.disconnect();
            }
        }
    }

    private void showResponse(final String responce) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText(responce);
                Log.i("huawenli"," showResponse");

            }
        });
    }
}
