package com.example.bmicalculator;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;


public class MainActivity extends AppCompatActivity {

   // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


    private InterstitialAd mInterstitialAd;
    boolean isAllFieldsChecked = false;

    int age = 25,heightcheck=1,weightcheck=0;
    String genderselected ="0";
    ImageView male ,female,up,down;
    Button agebtn,calbtn,heightbtn;
    ToggleButton heighttogglebtn,weighttogglebtn;
    EditText edithight,feet,inches,editage,editweight;
    FrameLayout ageframe,heightframe,weightframe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        male = findViewById(R.id.maleicon);
        female = findViewById(R.id.femaleicon);
        agebtn = findViewById(R.id.agebtn);
        calbtn = findViewById(R.id.calbtn);
        heightbtn = findViewById(R.id.heightbtn);
        up = findViewById(R.id.up);
        down = findViewById(R.id.down);
        edithight = (EditText) findViewById(R.id.editheight);
        editweight = (EditText) findViewById(R.id.editweight);
        feet = (EditText) findViewById(R.id.feet);
        inches = (EditText) findViewById(R.id.inches);
        heighttogglebtn = findViewById(R.id.heighttoggleButton);
        weighttogglebtn =findViewById(R.id.weighttoggleButton);
        ageframe = findViewById(R.id.ageFrame);
        heightframe = findViewById(R.id.heightFrame);
        weightframe = findViewById(R.id.weightFrame);
        editage = findViewById(R.id.editage);


        ageframe.setVisibility(View.INVISIBLE);
        heightframe.setVisibility(View.INVISIBLE);
        weightframe.setVisibility(View.INVISIBLE);
        calbtn.setVisibility(View.INVISIBLE);
        editage.setText(String.valueOf(age));


        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setBackgroundResource(R.drawable.redbg);
                female.setBackgroundResource(R.color.cardbg);
                ageframe.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                ageframe.setAnimation(animation);
                genderselected = "Male";

            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setBackgroundResource(R.drawable.redbg);
                male.setBackgroundResource(R.color.cardbg);
                ageframe.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                ageframe.setAnimation(animation);
                genderselected = "Female";


            }
        });
        MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,getString(R.string.adunit), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
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
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //age=Integer.parseInt(String.valueOf(editage.getText()));
                age=age+1;
                editage.setText(String.valueOf(age));




            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //age=Integer.parseInt(String.valueOf(editage.getText()));
                age=age-1;
                if(age<1){
                    age=age+1;}
                editage.setText(String.valueOf(age));

            }
        });

        agebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                    heightframe.setAnimation(animation);
                heightframe.setVisibility(View.VISIBLE);
                    agebtn.setVisibility(View.INVISIBLE);
            }
        });

        heighttogglebtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    edithight.setVisibility(View.INVISIBLE);
                    feet.setVisibility(View.VISIBLE);
                    inches.setVisibility(View.VISIBLE);
                    heightcheck =1; //feet box enabled

                }else{
                    edithight.setVisibility(View.VISIBLE);
                    feet.setVisibility(View.INVISIBLE);
                    inches.setVisibility(View.INVISIBLE);
                    heightcheck =0; //cm box enabled
                }

            }
        });


        heightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(heightcheck==0) {
                    if (edithight.getText().toString().equals("") || edithight.getText().toString().equals("0")) {
                        Toast.makeText(MainActivity.this, "Enter Valid Height", Toast.LENGTH_SHORT).show();
                    } else {
                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                        weightframe.setVisibility(View.VISIBLE);
                        weightframe.setAnimation(animation);
                        heightbtn.setVisibility(View.INVISIBLE);
                        calbtn.setVisibility(View.VISIBLE);
                        calbtn.setAnimation(animation);

                    }
                }
                else if (heightcheck == 1) {
                        if (feet.getText().toString().equals("") || feet.getText().toString().equals("0") || inches.getText().toString().equals("") || inches.getText().toString().equals("0")) {
                            Toast.makeText(MainActivity.this, "Enter Valid Height", Toast.LENGTH_SHORT).show();
                        } else {
                            weightframe.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
                            weightframe.setVisibility(View.VISIBLE);
                            heightbtn.setVisibility(View.INVISIBLE);
                            calbtn.setVisibility(View.VISIBLE);
                            calbtn.setAnimation(animation);

                        }
                    }
            }
        });

        calbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isAllFieldsChecked = CheckAllFields();
                if(weighttogglebtn.isChecked())
                {
                    weightcheck=1;
                }
                else
                {
                    weightcheck=0;
                }
                if(heighttogglebtn.isChecked())
                {
                    heightcheck=1;
                }
                else
                {
                    heightcheck=0;
                }
                if (isAllFieldsChecked) {
                    Intent intent = new Intent(MainActivity.this, ResultPage.class);
                    intent.putExtra("gender", genderselected);
                    intent.putExtra("age", String.valueOf(editage.getText()));
                    intent.putExtra("editheight", String.valueOf(edithight.getText()));
                    intent.putExtra("editfeet", String.valueOf(feet.getText()));
                    intent.putExtra("editinches", String.valueOf(inches.getText()));
                    intent.putExtra("editweight", String.valueOf(editweight.getText()));
                    intent.putExtra("heightcheck", String.valueOf((int) heightcheck));
                    intent.putExtra("weightcheck", String.valueOf((int) weightcheck));
                    startActivity(intent);
                }
            }
        });

    }
    private boolean CheckAllFields() {
        if ((heightcheck==0) &&  (edithight.length() == 0 ||edithight.getText().toString().equals("0"))) {
            edithight.setError("Enter your valid height");
            return false;
        }
        if ((heightcheck==1) &&  (feet.length() == 0)) {
            feet.setError("Enter your feet");
            return false;
        }

        if ((heightcheck==1) &&  (inches.length() == 0)) {
            inches.setError("inches is required");
            return false;
        }

        if ((weighttogglebtn.isChecked()) &&  (editweight.length() == 0 || editweight.getText().toString().equals("0"))) {
            editweight.setError("Enter your valid weight");
            return false;
        } else if (editweight.length() == 0 || editweight.getText().toString().equals("0")) {
            editweight.setError("Enter your valid weight");
            return false;
        }

        // after all validation return true.
        return true;
    }

    public void finish() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }

        super.finish();
    }

}
