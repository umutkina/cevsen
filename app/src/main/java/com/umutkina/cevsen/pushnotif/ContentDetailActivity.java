package com.umutkina.cevsen.pushnotif;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.umutkina.cevsen.R;
import com.umutkina.cevsen.pushnotif.modals.Notification;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ContentDetailActivity extends BaseActivity {
    public static final String CONTENT = "content";

    public static final String IMAGE = "imageUrl";
    public static final String MESSAGE = "message";
    public static final String LINK = "linkUrl";
    public static final String VIDEO = "videoUrl";
    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_send)
    TextView tvSend;
    @Bind(R.id.iv_play)
    ImageView ivPlay;


    public static Intent newIntent(Context context, Notification hashMap) {

        Intent intent = new Intent(context, ContentDetailActivity.class);
        intent.putExtra(CONTENT, hashMap);

        return intent;

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_content_detail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Notification hashMap = (Notification) getIntent().getExtras().get(CONTENT);
        String image = hashMap.getImageUrl();
        String message = hashMap.getMessage();
        final String link = hashMap.getLink();
        final String video = hashMap.getVideoUrl();
        String buttonText = hashMap.getButtonText();
        System.out.println("imageurllog : " + image + " " + ivImage);


        if (buttonText != null) {
            tvSend.setText(buttonText);
        }

        if (video==null) {
            ivPlay.setVisibility(View.GONE);
        }
        if (link == null) {
            tvSend.setVisibility(View.GONE);
        }
        if (image == null) {
            ivImage.setVisibility(View.GONE);
        }
        else {
            Picasso.with(this)
                    .load(image)
//                .placeholder(R.drawable.user_placeholder)
//                .error(R.drawable.user_placeholder_error)
                    .into(ivImage);
        }
        if (message == null) {
            tvContent.setVisibility(View.GONE);
        }
        else {
            tvContent.setText(message.replace("##", "\n"));
        }
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = YoutubePlayerActivity.newIntent(ContentDetailActivity.this, video);
                startActivity(intent);
            }
        });
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                startActivity(i);
            }
        });

    }
}
