package com.huawenli.broadcast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {

    private EditText mAccoumtEt;
    private EditText mPasswordEt;
    private Button mLoginBtn;
    private CheckBox rememberPwd;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAccoumtEt = findViewById(R.id.account);
        mPasswordEt = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.login);

        rememberPwd = findViewById(R.id.remember_pwd);

        //使用SharedPreferces来实现记住账号密码
        preferences = getPreferences(Context.MODE_PRIVATE);
        boolean isRemember = preferences.getBoolean("remember_password", false);
        if (isRemember){
            mAccoumtEt.setText(preferences.getString("account", ""));
            mPasswordEt.setText(preferences.getString("pwd", ""));
            rememberPwd.setChecked(true);
        }


        //点击登录，判断账号 admin 密码123 ，进入另外一个Activity
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = mAccoumtEt.getText().toString();
                String password = mPasswordEt.getText().toString();
                if ("admin".equals(account)&&"123".equals(password)){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    SharedPreferences.Editor edit = preferences.edit();
                    if (rememberPwd.isChecked()){
                        edit.putBoolean("remember_password",true);
                        edit.putString("account",account);
                        edit.putString("pwd", password);
                    }else{
                        edit.clear();
                    }
                    edit.apply();

                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "账号密码错误，重新输入", Toast.LENGTH_SHORT).show();
                    mAccoumtEt.setText("");
                    mPasswordEt.setText("");
                }
            }
        });







    }


}
