package com.example.yohan.attendenceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TeacherProfile extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    String name,course,regNo,Email,course1;
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static Context ctx;
    private TextView tvTeacherName,TeacherName,TeacherEmail,TeacherCourse;
    private Button btnAttendance,btnDetails;
    private LinearLayout detailsLayout;
    private boolean details = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);

        ActionBar actionBar = getSupportActionBar();
        // actionBar.setIcon(R.drawable.landscape);
        actionBar.setTitle("Teacher");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        tvTeacherName = findViewById(R.id.tvTeacherName);
        TeacherName = findViewById(R.id.detailsTeacherName);
        TeacherEmail = findViewById(R.id.detailsTeacherEmail);
        TeacherCourse = findViewById(R.id.detailsTeacherCourse);
        btnAttendance = findViewById(R.id.btnMarkTeacherAttendance);
        btnDetails = findViewById(R.id.btnTeacherDetails);
        detailsLayout = findViewById(R.id.detailsTeacherLyout);
        //sharedPrefManager = new SharedPrefManager(this);
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        name = sharedPreferences.getString("teacherName",null);
        course = sharedPreferences.getString("teacherCourse",null);
        Email = sharedPreferences.getString("teacherEmail",null);
        tvTeacherName.setText("Hi, "+name+" !");

        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(details == false){
                    detailsLayout.setVisibility(View.VISIBLE);
                    TeacherName.setText("Name : "+name);
                    TeacherCourse.setText("Course : "+course);
                    TeacherEmail.setText("Email : "+Email);
                    details = true;
                }else if(details == true){
                    detailsLayout.setVisibility(View.GONE);
                    details = false;
                }

            }
        });

        btnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TeacherProfile.this,CheckTeacher.class);
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_logout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this,Login.class));
                break;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!SharedPrefManager.getInstance(this).isTeacherLoggedIn()){
            startActivity(new Intent(this,Login.class));
            finish();
            return;
        }
    }
}
