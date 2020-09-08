package com.huawenli.learning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //签名算法以AppSecret作为key，使用hmac-sha1带密钥(secret)的哈希算法对代签字符串进行签名计算。签名的结果由16进制表示
    //1.生成带签名字符串表中各参数按字母顺序排序（不包含signature），如果第一个字母相同，按第二个字母排序，依次类推。
    // 排序后拼接成par1=val1&par2=val2&par3=val3的格式,所生成的字符串即为待签名的字符串。
    // 没有值的参数请不要参与签名。
    // 由于有些数据根据HTTP协议需求,需要进行URLencoding,这样接收方才可以接收到正确的参数,但如果这个参数参与签名,
    // 那么待签名字符串必须是字符串原值而非URLencoding的值。 例如(具体参数请以收到的为准，不要依据此示例修改参数)：

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        String AppSecret = "/4Ij4P/6XB5FXdyd03u1fw==";
//        String n = "appId=2882303761518576426&session=1nlfxuAGmZk9IR2L&uid=100010&signature=b560b14efb18ee2eb8f85e51c5f7c11f697abcfc";
//        StringBuilder builder = new StringBuilder();

        List<Integer> objects = new ArrayList<>(1);

    }
}
