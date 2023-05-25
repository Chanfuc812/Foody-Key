package com.foodykey.food.local;

import android.content.Context;

public class MySharedPreferences {
    private static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    private final Context mContext;

    public MySharedPreferences(Context mContext) {
        this.mContext = mContext;
    }



    public void putStringValue(String key, String s) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                SHARED_PREFERENCES, 0);
        android.content.SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, s);
        editor.apply();
    }

    public String getStringValue(String key) {
        android.content.SharedPreferences pref = mContext.getSharedPreferences(
                SHARED_PREFERENCES, 0);
        return pref.getString(key, "");
    }


}