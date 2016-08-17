package com.umutkina.cevsen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umutkina.cevsen.pushnotif.BaseActivity;
import com.umutkina.cevsen.pushnotif.NotificationActivity;


public class MainActivity extends BaseActivity {
     int startValue = 0;
    int last=0;
    int i=0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        startValue=  prefs.getInt("start", 0);
        last = prefs.getInt("end", 0);
        final TextView tvText= (TextView) findViewById(R.id.tv_text);
        final TextView tvCounter= (TextView) findViewById(R.id.tv_counter);
        final String[] some_array = getResources().getStringArray(R.array.cevsen);

        final TextView tvPlus= (TextView) findViewById(R.id.tv_plus);
        final TextView tvMinus= (TextView) findViewById(R.id.tv_minus);
        i=prefs.getInt("current",0);
        tvText.setText(some_array[this.i-1]);
        tvCounter.setText(i+"");
        ImageView ivEdit= (ImageView) findViewById(R.id.iv_Edit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,SelectCevsenActivity.class);
                prefs.edit().putBoolean("isOtoPass",false).commit();
                startActivity(intent);
            }
        });
        tvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.this.i <= (last - 1)) {
                    tvText.setText(some_array[++MainActivity.this.i-1]);
                    tvCounter.setText(MainActivity.this.i  + "");
                    prefs.edit().putInt("current", i).commit();
                }
                else if(MainActivity.this.i==100){
                    ++MainActivity.this.i;
                    tvText.setText(getString(R.string.action_settings));
                    tvCounter.setText("---");
                }

            }
        });
        tvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.this.i >=(startValue+1)){
                    tvText.setText(some_array[--MainActivity.this.i-1]);
                    tvCounter.setText(MainActivity.this.i +"");
                    prefs.edit().putInt("current", i).commit();
                }

            }
        });
        ImageView ivNotif= (ImageView) findViewById(R.id.iv_notif);
        ivNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(MainActivity.this,NotificationActivity.class);
                startActivity(intent);
            }
        });
    }



}
