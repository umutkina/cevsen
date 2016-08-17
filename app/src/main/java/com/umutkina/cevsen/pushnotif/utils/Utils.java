package com.umutkina.cevsen.pushnotif.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.gson.Gson;
import com.umutkina.cevsen.CevsenApplication;
import com.umutkina.cevsen.pushnotif.modals.Const;
import com.umutkina.cevsen.pushnotif.modals.Notification;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 04/01/15.
 */
public class Utils {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
   public  static Object getObject(String json, Type type){
//       Object obj=null;
       Gson gson = new Gson();
//       String json = mPrefs.getString("MyObject", "");
       return  gson.fromJson(json, type);
   }
    public  static String getJson(Object o){
        String s=null;

        Gson gson = new Gson();
         s = gson.toJson(o);
        return s;

    }


    public static void showInfoDialog(Context context, final Runnable onOk,
                                      String title, String message) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle(title);
        alert.setMessage(message);

        if (onOk != null) {
            alert.setNegativeButton("İptal",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });
        }
        alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (onOk != null) {
                    onOk.run();
                }
            }
        });

        try {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    alert.show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public List<String> read(String key,Context context) throws IOException {
//        List<String> resultSet = new ArrayList<String>();
//        InputStream is = context.getAssets().open("m1.map");
//        File inputWorkbook = new File(is);
//        if(inputWorkbook.exists()){
//            Workbook w;
//            try {
//                w = Workbook.getWorkbook(inputWorkbook);
//                // Get the first sheet
//                Sheet sheet = w.getSheet(0);
//                // Loop over column and lines
//                for (int j = 0; j < sheet.getRows(); j++) {
//                    Cell cell = sheet.getCell(0, j);
//                    if(cell.getContents().equalsIgnoreCase(key)){
//                        for (int i = 0; i < sheet.getColumns(); i++) {
//                            Cell cel = sheet.getCell(i, j);
//                            resultSet.add(cel.getContents());
//                        }
//                    }
//                    continue;
//                }
//            } catch (BiffException e) {
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        else
//        {
//            resultSet.add("File not found..!");
//        }
//        if(resultSet.size()==0){
//            resultSet.add("Data not found..!");
//        }
//        return resultSet;
//    }
    public static void showInfoDialogBbbcm(Context context, final Runnable onOk,
                                           String title, String message) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle(title);
        alert.setMessage(message);

        if (onOk != null) {
            alert.setNegativeButton("İptal",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });
        }
        alert.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                if (onOk != null) {
                    onOk.run();
                }
            }
        });

        try {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    alert.show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static Object getObjectFromJsonArray(String jsonObject, Class typeRef) {
        Object object = null;

        try {
            CollectionType collectionType = Utils.OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, typeRef);
            object = Utils.OBJECT_MAPPER.readValue(jsonObject, collectionType);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    public static ArrayList<Notification> getNotifList(Context context) {

        ArrayList<Notification> notifications = null;
        CevsenApplication chatApplication = (CevsenApplication) context.getApplicationContext();

        SharedPreferences prefs = chatApplication.getPrefs();
        String string = prefs.getString(Const.NOTIF_LIST, null);
        notifications = (ArrayList<Notification>) Utils.getObjectFromJsonArray(string, Notification.class);
        if (notifications == null) {
            notifications = new ArrayList<>();
        }

        return notifications;

    }

    public static void saveNotification(Notification notification, Context context) {

        ArrayList<Notification> notifications = getNotifList(context);

        notifications.add(notification);

        String jsonObject = Utils.getJsonObject(notifications);
        CevsenApplication chatApplication = (CevsenApplication) context.getApplicationContext();

        SharedPreferences prefs = chatApplication.getPrefs();

        prefs.edit().putString(Const.NOTIF_LIST, jsonObject).commit();


    }


    public static String getJsonObject(Object object) {
        String json = null;
        try {
            json = Utils.OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static Object getObjectFromJson(String jsonObject, Class typeRef) {
        Object object = null;
        try {
            Utils.OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            object = Utils.OBJECT_MAPPER.readValue(jsonObject, typeRef);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
    public static void deleteNotification(Context context, Notification notification) {

        ArrayList<Notification> notifications = getNotifList(context);

        for (int i = 0; i < notifications.size(); i++) {
            Notification notification1 = notifications.get(i);
            if (notification.getId().equalsIgnoreCase(notification1.getId())) {
                notifications.remove(i);
            }

        }



        String jsonObject = Utils.getJsonObject(notifications);
        CevsenApplication chatApplication = (CevsenApplication) context.getApplicationContext();

        SharedPreferences prefs = chatApplication.getPrefs();

        prefs.edit().putString(Const.NOTIF_LIST, jsonObject).commit();


    }


}
