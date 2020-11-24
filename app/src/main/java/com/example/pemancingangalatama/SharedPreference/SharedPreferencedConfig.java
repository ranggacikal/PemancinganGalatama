package com.example.pemancingangalatama.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencedConfig {

    public static final String PREFERENCE_PEMANCINGAN_APP = "prefPemancinganApp";

    public static final String PREFERENCE_ID_USER = "prefIdUser";
    public static final String PREFERENCE_NAMA_LENGKAP = "prefNamaLengkap";
    public static final String PREFERENCE_USERNAME = "prefUsername";
    public static final String PREFERENCE_NO_TELPON = "prefNoTelpon";
    public static final String PREFERENCE_EMAIL = "prefEMail";
    public static final String PREFERENCE_IMAGE = "prefImage";
    public static final String PREFERENCE_LEVEL_USER = "prefLevelUser";
    public static final String PREFERENCE_IS_LOGIN = "prefIsLogin";

    SharedPreferences preferences;
    SharedPreferences.Editor preferencesEditor;

    public SharedPreferencedConfig(Context context) {

        preferences = context.getSharedPreferences(PREFERENCE_PEMANCINGAN_APP, Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
    }

    public void savePrefString(String keyPref, String value){
        preferencesEditor.putString(keyPref, value);
        preferencesEditor.commit();
    }

    public void savePrefInt(String keyPref, int value){
        preferencesEditor.putInt(keyPref, value);
        preferencesEditor.commit();
    }

    public void savePrefBoolean(String keyPref, boolean value){
        preferencesEditor.putBoolean(keyPref, value);
        preferencesEditor.commit();
    }

    public String getPreferenceIdUser(){
        return preferences.getString(PREFERENCE_ID_USER, "");
    }

    public String getPreferenceEmail(){
        return preferences.getString(PREFERENCE_EMAIL, "");
    }

    public String getPreferenceNoTelpon(){
        return preferences.getString(PREFERENCE_NO_TELPON, "");
    }

    public String getPreferenceLevelUser(){
        return preferences.getString(PREFERENCE_LEVEL_USER, "");
    }

    public  String getPreferenceNamaLengkap() {
        return preferences.getString(PREFERENCE_NAMA_LENGKAP, "");
    }

    public  String getPreferenceUsername() {
        return preferences.getString(PREFERENCE_USERNAME, "");
    }

    public String getPreferenceImage(){
        return preferences.getString(PREFERENCE_IMAGE, "");
    }

    public Boolean getPreferenceIsLogin(){
        return preferences.getBoolean(PREFERENCE_IS_LOGIN, false);
    }
}
