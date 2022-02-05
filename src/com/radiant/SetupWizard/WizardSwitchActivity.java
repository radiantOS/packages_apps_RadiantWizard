package com.radiant.SetupWizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.android.setupwizardlib.util.WizardManagerHelper;

public class WizardSwitchActivity extends AppCompatActivity {

    Intent sOriginalIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupShit();
    }

    public void Emerg(View view) {
        Intent callIntent = new Intent("com.android.phone.EmergencyDialer.DIAL");
        WizardSwitchActivity.this.startActivity(callIntent);
    }

    public void Next(View view) {
        sOriginalIntent = getIntent();
        Intent nextIntent = WizardManagerHelper.getNextIntent(sOriginalIntent, -1);
        WizardManagerHelper.copyWizardManagerExtras(sOriginalIntent, nextIntent);
        startActivityForResult(nextIntent, 1);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void setupShit(){
        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));
        window.getDecorView().setSystemUiVisibility(2818);
    }

}
