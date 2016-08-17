package com.umutkina.cevsen.pushnotif.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.umutkina.cevsen.R;
import com.umutkina.cevsen.pushnotif.ContentDetailActivity;
import com.umutkina.cevsen.pushnotif.NotificationActivity;
import com.umutkina.cevsen.pushnotif.WebViewActivity;
import com.umutkina.cevsen.pushnotif.YoutubePlayerActivity;
import com.umutkina.cevsen.pushnotif.modals.Notification;
import com.umutkina.cevsen.pushnotif.utils.Utils;

import java.util.ArrayList;

/**
 * Created by umutkina on 14/08/16.
 */
public class NotificationAdapter extends BaseAdapter {
    private String TAG = "com.umutkina.ydsservice notifadapter";
    Context context;
    ArrayList<Notification> notifications = new ArrayList<>();

    public NotificationAdapter(Context context, ArrayList<Notification> notifications) {
        super();
        this.notifications = notifications;
        this.context = context;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int position) {
        return notifications.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final String text = notifications.get(position).getTitle();
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.saved_words_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_word);
        ImageView ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
        // Populate the data into the template view using the data object
        tvName.setText(text);

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                NotificationActivity notificationActivity = (NotificationActivity) context;
                notificationActivity.itemDelete(notifications.get(position));
            }
        };

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showInfoDialog(context, runnable, context.getString(R.string.warnings), "Bildirimi silmek istediğinizden amin misiniz?");

            }
        });
        // Return the completed view to render on screen
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification(notifications.get(position),context);
            }
        });
        return convertView;
    }

    private void sendNotification(Notification notification, Context context) {
        Intent intent = null;


        String type = notification.getType();
        String title = notification.getTitle();
        String videoUrl = notification.getVideoUrl();
        String webUrl = notification.getWebUrl();
        String message = notification.getMessage();
        String link =notification.getLink();
        String imageUrl = notification.getImageUrl();
        String id = System.currentTimeMillis() + "";







        switch (type) {
            case "video":
                intent = YoutubePlayerActivity.newIntent(context, videoUrl);
                break;

            case "content":

                intent = ContentDetailActivity.newIntent(context, notification);

                break;


            case "web":

                intent = WebViewActivity.newIntent(context, webUrl);

                break;

        }


        context.startActivity(intent);


    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }
}
