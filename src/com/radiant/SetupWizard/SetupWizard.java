package com.radiant.SetupWizard;

import android.app.Application;
import android.content.Context;

import com.google.android.material.color.DynamicColors;

public class SetupWizard extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }

}
