package com.radiant.SetupWizard;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.content.Intent;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import com.android.setupwizardlib.util.WizardManagerHelper;

import com.google.android.material.button.MaterialButton;

public class SecondWelcomeActivity extends AppCompatActivity {

    ImageView bottomPad;
    View lel;
    ImageView topPad;
    ImageView imgOne;
    ImageView imgTwo;
    ImageView imgThree;
    ImageView imgFour;
    Intent sOriginalIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_welcome);
        bottomPad = findViewById(R.id.bottomPad);
        topPad = findViewById(R.id.topPad);
        lel = findViewById(R.id.keka);
        getWindow().getDecorView().setSystemUiVisibility(2818);
        setViews();
        observelayout();
    }


    public void backBtnClick(View view) {
        onBackPressed();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void nextBtnClick(View view) {
        sOriginalIntent = getIntent();
        Intent nextIntent = WizardManagerHelper.getNextIntent(sOriginalIntent, -1);
        WizardManagerHelper.copyWizardManagerExtras(sOriginalIntent, nextIntent);
        startActivityForResult(nextIntent, 1);
    }

    public void accessBtnClick(View view) {
        Intent sendIntent = new Intent("android.intent.action.VIEW");
        sendIntent.setClassName("com.android.settings", "com.android.settings.accessibility.AccessibilitySettingsForSetupWizardActivity");
        startActivity(sendIntent);
    }

    public void langBtnClick(View view) {
        Intent sendIntent = new Intent("android.intent.action.VIEW");
        sendIntent.setClassName("com.android.settings", "com.android.settings.Settings$LocalePickerActivity");
        startActivity(sendIntent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setViews();
        observelayout();
        getWindow().getDecorView().setSystemUiVisibility(2818);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setViews();
        getWindow().getDecorView().setSystemUiVisibility(2818);
    }

    void setViews() {
        imgOne = findViewById(R.id.img);
        imgTwo = findViewById(R.id.img2);
        imgThree = findViewById(R.id.img3);
        imgFour = findViewById(R.id.img4);
    }

    public void observelayout() {
        ViewTreeObserver vto = lel.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                lel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = lel.getMeasuredWidth();

                bottomPad.setPadding(0, 0, 0, width);
                topPad.setPadding(0, 0, 0, width);
                ImageView[] imgArray = {imgOne, imgTwo, imgThree, imgFour};
                for (ImageView imageView : imgArray) {
                    imageView.setPaddingRelative(0, 0, width, 0);
                }
            }
        });
    }

}