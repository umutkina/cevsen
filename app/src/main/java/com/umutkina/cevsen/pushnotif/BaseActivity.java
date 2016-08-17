package com.umutkina.cevsen.pushnotif;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.umutkina.cevsen.CevsenApplication;
import com.umutkina.cevsen.R;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import butterknife.ButterKnife;

/**
 * Created by mac on 02/01/15.
 */
public abstract  class BaseActivity extends AppCompatActivity {

//    ArrayList<YdsWord> mTempArray = new ArrayList<>();
//    ArrayList<YdsWord> savedWords = new ArrayList<>();


    public SharedPreferences preferences;

    public  SharedPreferences prefs;
    CevsenApplication cApplication;

    public abstract int getLayoutId();

    InterstitialAd mInterstitialAd;
    AdView mAdView;
    AdRequest adRequestInter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        preferences = getSharedPreferences("Mypref", 0);

        cApplication= (CevsenApplication) getApplication();
        prefs=cApplication.getPrefs();

        // request kütüphanesi entegrasyonu

//        tureng();


        AdRequest adRequest = new AdRequest.Builder().build();


        mAdView = (AdView) findViewById(R.id.adView);

        if (mAdView != null) {
            mAdView.loadAd(adRequest);
            mAdView.setVisibility(View.GONE);
            mAdView.setAdListener(adListener2);
        }




        boolean showSavedDialog = preferences.getBoolean("showAd", false);
        if (showSavedDialog) {
            adRequestInter = new AdRequest.Builder().
//                addTestDevice("2795DA65D50FE4C721767208480E9ABC").
        build();
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId("ca-app-pub-4443948134096736/4830862004");
            mInterstitialAd.loadAd(adRequestInter);
            mInterstitialAd.setAdListener(adListener);

        }
        preferences.edit().putBoolean("showAd", true).commit();

    }




    public Set<?> findDuplicatesInList(List<?> beanList) {
        System.out.println("findDuplicatesInList::" + beanList);
        Set<Object> duplicateRowSet = null;
        duplicateRowSet = new LinkedHashSet<Object>();
        for (int i = 0; i < beanList.size(); i++) {
            Object superString = beanList.get(i);
            System.out.println("findDuplicatesInList::superString::" + superString);
            for (int j = 0; j < beanList.size(); j++) {
                if (i != j) {
                    Object subString = beanList.get(j);
                    System.out.println("findDuplicatesInList::subString::" + subString);
                    if (superString.equals(subString)) {
                        duplicateRowSet.add(beanList.get(j));
                    }
                }
            }
        }
        System.out.println("findDuplicatesInList::duplicationSet::" + duplicateRowSet);
        return duplicateRowSet;
    }




    AdListener adListener2 = new AdListener() {
        @Override
        public void onAdClosed() {
            super.onAdClosed();
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            super.onAdFailedToLoad(errorCode);
        }

        @Override
        public void onAdLeftApplication() {
            super.onAdLeftApplication();
        }

        @Override
        public void onAdOpened() {
            super.onAdOpened();
        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    mAdView.setVisibility(View.VISIBLE);


                    ;
                }
            }, 5000);
        }
    };

    AdListener adListener = new AdListener() {
        @Override
        public void onAdClosed() {
            super.onAdClosed();
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            super.onAdFailedToLoad(errorCode);
        }

        @Override
        public void onAdLeftApplication() {
            super.onAdLeftApplication();
        }

        @Override
        public void onAdOpened() {
            super.onAdOpened();
        }

        @Override
        public void onAdLoaded() {
            super.onAdLoaded();
            int i = randInt(3);
            if (i == 2) {
                mInterstitialAd.show();

            }

        }
    };

    public static int randInt(int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt(max + 1);

        return randomNum;
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
