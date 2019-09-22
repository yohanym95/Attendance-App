package com.example.yohan.attendenceapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    private static SharedPrefManager instance;
    private static Context ctx;
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_ADMIN_EMAIL = "adminEmail";
    private static final String KEY_ADMIN_ID = "adminId";
    private static final String KEY_STUDENT = "stuName";
    private static final String KEY_STU_EMAIL = "stuEmail";
    private static final String KEY_STU_REGNO = "stuRegNo";
    private static final String KEY_STU_COURSE = "stuCourse";
    private static final String KEY_TEACHER_EMAIL = "teacherEmail";
    private static final String KEY_TEACHER_COURSE = "teacherCourse";
    private static final String KEY_TEACHER_NAME = "teacherName";

    private SharedPrefManager(Context context) {
        ctx = context;


    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    //admin sharedpreferences
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

    //student shared preferences
    public boolean studentLogin(String regNo,String email,String name,String course){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_STU_REGNO,regNo);
        editor.putString(KEY_STU_EMAIL,email);
        editor.putString(KEY_STUDENT,name);
        editor.putString(KEY_STU_COURSE,course);

        editor.apply();

        return true;

    }

    public boolean isStudentLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_STU_EMAIL,null) != null){
            return true;
        }

        return false;
    }

    //teacher shared preferences
    public boolean teacherLogin(String email,String name,String course){

        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(KEY_TEACHER_EMAIL,email);
        editor.putString(KEY_TEACHER_NAME,name);
        editor.putString(KEY_TEACHER_COURSE,course);

        editor.apply();

        return true;

    }

    public boolean isTeacherLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_TEACHER_EMAIL,null) != null){
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
