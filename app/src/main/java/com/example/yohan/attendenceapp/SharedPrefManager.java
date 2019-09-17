package com.example.yohan.attendenceapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static SharedPrefManager instance;
    private static Context ctx;
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_ADMIN = "username";
    private static final String KEY_ADMIN_EMAIL = "useremail";
    private static final String KEY_ADMIN_ID = "userid";

    private SharedPrefManager(Context context) {
        ctx = context;


    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean adminLogin(int id,String email){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(KEY_ADMIN_ID,id);
        editor.putString(KEY_ADMIN_EMAIL,email);
//        editor.putString(KEY_USERNAME,username);

        editor.apply();

        return true;

    }

    public boolean isadminLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_ADMIN_EMAIL,null) != null){
            return true;
        }

        return false;
    }

    public boolean logout(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }




}
