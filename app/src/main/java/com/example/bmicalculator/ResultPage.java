package com.example.bmicalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class ResultPage extends AppCompatActivity {


    private InterstitialAd mInterstitialAd;
    float bmi,height,weight,calfeet=0,calinch=0,calft=0,calweight=0;
    int intbmi,hcheck,wcheck;
    String strbmi,strfeet,strinches,strcalft;
    TextView dage,dheight,dweight,dgender,dbmi,resultvalue,colortext;
    ImageView resultclr;
    Button recal;
    LottieAnimationView feedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);
        dgender = findViewById(R.id.detailsgendervalue);
        dage = findViewById(R.id.detailsagevalue);
        dheight = findViewById(R.id.detailsheightvalue);
        dweight = findViewById(R.id.detailsweightvalue);
        recal = findViewById(R.id.recalbtn);
        dbmi = findViewById(R.id.detailsbvalue);
        resultvalue = findViewById(R.id.reultvalue);
        colortext = findViewById(R.id.colortext);
        resultclr = findViewById(R.id.resultcolor);
        feedback = findViewById(R.id.lottieAnimationView);


        MobileAds.initialize(ResultPage.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, getString(R.string.adunit), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                Log.d("TAG", "The ad was dismissed.");
                                finish();
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                // Called when fullscreen content failed to show.
                                Log.d("TAG", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null;
                                Log.d("TAG", "The ad was shown.");
                            }
                        });

                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });


        Intent intent = getIntent();

        hcheck = Integer.parseInt(intent.getStringExtra("heightcheck"));
        wcheck = Integer.parseInt(intent.getStringExtra("weightcheck"));


        if (hcheck == 1) {

            strfeet = intent.getStringExtra("editfeet");
            strinches = intent.getStringExtra("editinches");
            calfeet = Float.parseFloat(String.valueOf(strfeet));
            calfeet = calfeet * 30.48f;
            calinch = Float.parseFloat(String.valueOf(strinches));
            calinch = calinch * 2.54f;
            calft = calfeet + calinch;
        } else if(hcheck==0) {

            strcalft = intent.getStringExtra("editheight");
            calft=Float.parseFloat(String.valueOf(strcalft));
        }

        if (wcheck == 1) {
            calweight = Float.parseFloat(String.valueOf(intent.getStringExtra("editweight")));
            calweight *= 0.4536f;
        } else {
            calweight = Float.parseFloat(String.valueOf(intent.getStringExtra("editweight")));
        }

        height = calft;
        weight = calweight;
        height = height / 100;
        bmi = weight / (height * height);
        bmi = Math.round(bmi);
        intbmi = (int) bmi;
        strbmi = Integer.toString(intbmi);
        if (intbmi <= 18) {
            colortext.setText(R.string.lottieunderweight);
            resultvalue.setText("Your Bmi : " + strbmi);
            resultclr.setBackgroundColor(Color.parseColor("#FFBE0F"));
            feedback.setAnimation(R.raw.thumbs);
        } else if (intbmi > 18 && intbmi <= 24) {
            colortext.setText(R.string.lottieNormal);
            resultvalue.setText("Your Bmi : " + strbmi);
            resultclr.setBackgroundColor(Color.parseColor("#00FF00"));
            feedback.setAnimation(R.raw.thumbs);
        } else if (intbmi >= 25 && intbmi <= 29) {
            colortext.setText(R.string.lottieOverweight);
            resultvalue.setText("Your Bmi :" + strbmi);
            resultclr.setBackgroundColor(Color.parseColor("#FF6000"));
        } else {
            colortext.setText(R.string.lottieObese);
            resultvalue.setText("Your Bmi : " + strbmi);
            resultclr.setBackgroundColor(Color.parseColor("#DB0000"));
        }
        //detail view part
        dgender.setText(intent.getStringExtra("gender"));
        dage.setText(intent.getStringExtra("age"));
        dbmi.setText(strbmi);
        // height detail display
        if (hcheck == 1) {
            dheight.setText(intent.getStringExtra("editfeet") + "' " + intent.getStringExtra("editinches") + '"');
        } else {
            dheight.setText(intent.getStringExtra("editheight") + " cm");
        }
        //weight detail display
        if (wcheck == 1) {
            dweight.setText(intent.getStringExtra("editweight") + " lbs");
        } else {
            dweight.setText(intent.getStringExtra("editweight") + " kg");
        }

        recal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mInterstitialAd != null) {
                    mInterstitialAd.show(ResultPage.this);

                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {

                            super.onAdDismissedFullScreenContent();
                            Intent intent = new Intent(ResultPage.this, MainActivity.class);
                            startActivity(intent);
                            mInterstitialAd = null;
                            setAds();
                            // Called when fullscreen content is dismissed.
                        }
                    });

                } else {
                    Intent intent = new Intent(ResultPage.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    public void setAds(){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,getString(R.string.adunit), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;


                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error

                        mInterstitialAd = null;
                    }
    });
    }
    @Override
    public void finish() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }

        super.finish();
    }
}


