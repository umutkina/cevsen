package com.umutkina.cevsen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.umutkina.cevsen.pushnotif.BaseActivity;
import com.umutkina.cevsen.pushnotif.ContentDetailActivity;
import com.umutkina.cevsen.pushnotif.NotificationActivity;
import com.umutkina.cevsen.pushnotif.WebViewActivity;
import com.umutkina.cevsen.pushnotif.YoutubePlayerActivity;
import com.umutkina.cevsen.pushnotif.modals.Notification;
import com.umutkina.cevsen.pushnotif.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SelectCevsenActivity extends BaseActivity {
    private String TAG = "com.umutkina.uzuntesbihat menuactivity";
    ArrayList<String> startArray = new ArrayList<>();
    ArrayList<String> endArray = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_cevsen;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        boolean isOtoPass = prefs.getBoolean("isOtoPass", false);
        if (isOtoPass) {
            Intent intent= new Intent(SelectCevsenActivity.this,MainActivity.class);

            startActivity(intent);
            finish();
        }
        for (int i = 0; i <=19; i++) {
            startArray.add(i*5+1+"");
            endArray.add((i+1)*5+"");
        }
        ArrayAdapter<String> startArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, startArray); //selected item will look like a spinner set from XML
        ArrayAdapter<String> endArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, endArray); //selected item will look like a spinner set from XML

        final Spinner spnStart = (Spinner) findViewById(R.id.spn_start);
        final Spinner spnEnd = (Spinner) findViewById(R.id.spn_end);
        TextView tvSubmit= (TextView) findViewById(R.id.tv_create);
        TextView tvCancel= (TextView) findViewById(R.id.tv_cancel);
        spnStart.setAdapter(startArrayAdapter);
        spnEnd.setAdapter(endArrayAdapter);

        int start = prefs.getInt("start", 0);
        int end = prefs.getInt("end", 0);

        if (start==0&&end==0){
            spnEnd.setSelection(endArray.size() - 1);
            tvCancel.setVisibility(View.GONE);
        }
        else{
            tvCancel.setVisibility(View.VISIBLE);
            spnStart.setSelection((start-1)/5);
            spnEnd.setSelection(end/5-1);
        }
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().putBoolean("isOtoPass",true).commit();
                finish();
            }
        });


        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start= (String) spnStart.getSelectedItem();
                String end= (String) spnEnd.getSelectedItem();
                int startInt = Integer.parseInt(start);
                int endInt = Integer.parseInt(end);
                if (endInt > startInt){
                    Intent intent= new Intent(SelectCevsenActivity.this,MainActivity.class);
                    prefs.edit().putInt("current", startInt).commit();
                    prefs.edit().putInt("start", startInt).commit();
                    prefs.edit().putInt("end", endInt).commit();
                    prefs.edit().putBoolean("isOtoPass",true).commit();
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(SelectCevsenActivity.this,"Lütfen bitiş değerini başlangıçtan daha yüksek giriniz",Toast.LENGTH_SHORT).show();
                }

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Map<String, String> stringStringMap = new HashMap<>();
            if (extras != null) {
                for (String key : extras.keySet()) {
                    Object value = extras.get(key);
                    Log.d(TAG, String.format("%s %s (%s)", key,
                            value.toString(), value.getClass().getName()));

                    stringStringMap.put(key,value.toString());
                }


                sendNotification(stringStringMap);

            }
        }

        ImageView ivNotif= (ImageView) findViewById(R.id.iv_notif);
        ivNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(SelectCevsenActivity.this,NotificationActivity.class);
                startActivity(intent);
            }
        });
    }


    private void sendNotification(Map<String, String> data) {
        Intent intent = null;
        if (data.size() > 0&&data.get("type")!=null) {

            Log.d(TAG, "data.size() > 0 " + data);

            String type = data.get("type");
            String videoUrl = data.get("videoUrl");
            String webUrl = data.get("webUrl");
            String message = data.get("message");
            String link = data.get("linkUrl");
            String imageUrl = data.get("imageUrl");
            String title = data.get("title");
            String buttonText = data.get("buttonText");
            String id = System.currentTimeMillis() + "";

            Notification notification= new Notification();

            notification.setMessage(message);
            notification.setImageUrl(imageUrl);
            notification.setLink(link);
            notification.setType(type);
            notification.setVideoUrl(videoUrl);
            notification.setWebUrl(webUrl);
            notification.setId(id);
            notification.setTitle(title);
            notification.setButtonText(buttonText);



            Utils.saveNotification(notification,this);
            switch (type) {
                case "video":
                    intent = YoutubePlayerActivity.newIntent(this, videoUrl);
                    break;

                case "content":
                    intent = ContentDetailActivity.newIntent(this, notification);

                    break;

                case "web":

                    intent= WebViewActivity.newIntent(this,webUrl);

                    break;


            }
            this.startActivity(intent);
        }


    }

}
