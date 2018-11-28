package com.inspectionsheetdemo;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.multidex.MultiDex;

import java.util.ArrayList;

import eu.thirdspaceauto.akka.hacksprint.Models.Excavators;
import eu.thirdspaceauto.akka.hacksprint.Utils.PreferencesManager;

public class Common extends Application {
    public static Editor editor;
    public static SharedPreferences prefs;

    public static ArrayList<Excavators> excavators = new ArrayList<> ();

    public void onCreate() {
        super.onCreate();
        PreferencesManager.initialize(this);
        MultiDex.install(this);
    }
}
