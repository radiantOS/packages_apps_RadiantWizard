package com.radiant.SetupWizard;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.setupcompat.util.WizardManagerHelper;
import android.content.Intent;
import android.content.ComponentName;
import android.content.om.IOverlayManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textview.MaterialTextView;

public class NavigationPickerActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView bottomPad;
    View lel;
    MaterialCardView GestureCard;
    MaterialCardView ButtonCard;
    MaterialTextView GestureText;
    MaterialTextView ButtonText;
    LinearLayout GestureLayout;
    LinearLayout ButtonLayout;
    LinearLayout optGest;
    Button rdntNxt;
    SwitchMaterial gestTut;
    TextView gestt;
    TextView gestsum;

    int selection = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        bottomPad = findViewById(R.id.bottomPad);
        getWindow().getDecorView().setSystemUiVisibility(2818);
        setViews();
        observelayout();
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

    private void setOverlay(IOverlayManager overlayManager, String overlay) {
        try {
            overlayManager.setEnabledExclusiveInCategory(overlay, -2);
        } catch (RemoteException | IllegalStateException | SecurityException e) {
            e.printStackTrace();
        }
    }

    void setViews() {
        GestureCard = findViewById(R.id.gest);
        ButtonCard = findViewById(R.id.threebut);
        GestureText = findViewById(R.id.gesttext);
        ButtonText = findViewById(R.id.buttext);
        GestureLayout = findViewById(R.id.gestlayout);
        ButtonLayout = findViewById(R.id.butlayout);
        rdntNxt = findViewById(R.id.cap);
        gestTut = findViewById(R.id.gestSwitch);
        gestsum = findViewById(R.id.gestsumma);
        gestt = findViewById(R.id.gestsum);
        optGest = findViewById(R.id.optbox);
        lel = findViewById(R.id.keka);

        GestureLayout.setOnClickListener(this);
        ButtonLayout.setOnClickListener(this);
        rdntNxt.setOnClickListener(this);
        optGest.setOnClickListener(this);
        setColor();
    }

    void setColor() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = NavigationPickerActivity.this.getTheme();

        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        @ColorInt int color = typedValue.data;

        theme.resolveAttribute(R.attr.colorOnSurfaceVariant, typedValue, true);
        @ColorInt int color2 = typedValue.data;

        if (selection == 1) {
            ButtonCard.setStrokeColor(color);
            ButtonText.setTextColor(color);
            GestureCard.setStrokeColor(color2);
            GestureText.setTextColor(color2);
            gestTut.setEnabled(false);
            gestsum.setEnabled(false);
            gestt.setEnabled(false);
            gestt.setTextColor(gestsum.getCurrentTextColor());
        } else {
            GestureCard.setStrokeColor(color);
            GestureText.setTextColor(color);
            ButtonCard.setStrokeColor(color2);
            ButtonText.setTextColor(color2);
            gestTut.setEnabled(true);
            gestsum.setEnabled(true);
            gestt.setEnabled(true);
            gestt.setTextColor(Color.BLACK);
        }
    }

    public void observelayout() {
        ViewTreeObserver vto = lel.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                lel.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = lel.getMeasuredWidth();

                bottomPad.setPadding(0, 0, 0, width);
            }
        });
    }

    public void startTutactivity() {
        Intent intent = new Intent("com.android.quickstep.action.GESTURE_SANDBOX").setPackage("com.android.launcher3");
        startActivityForResult(intent.putExtra("tutorial_steps", new String[]{"HOME_NAVIGATION", "BACK_NAVIGATION", "OVERVIEW_NAVIGATION"}), 4449);
    }

    public void letsGoHome() {
        Intent sOriginalIntent = getIntent();
        Intent nextIntent = WizardManagerHelper.getNextIntent(sOriginalIntent, -1);
        WizardManagerHelper.copyWizardManagerExtras(sOriginalIntent, nextIntent);
        startActivityForResult(nextIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String kek = String.valueOf(resultCode);
        if (requestCode == 4449) {
            if(resultCode != 0){
                letsGoHome();
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == rdntNxt) {
            if (selection == 1) {
                setOverlay(IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")), "com.android.internal.systemui.navbar.threebutton");
                letsGoHome();
            } else {
                if (!gestTut.isChecked()){
                    setOverlay(IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")), "com.android.internal.systemui.navbar.gestural");
                    letsGoHome();
                }
                else{
                    setOverlay(IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")), "com.android.internal.systemui.navbar.gestural");
                    startTutactivity();
                }
            }
        } else if (v == GestureLayout) {
            selection = 2;
            setColor();
        } else if (v == ButtonLayout) {
            selection = 1;
            setColor();
        } else if (v == optGest && selection == 2) {
            gestTut.setChecked(!gestTut.isChecked());
        }
    }
}