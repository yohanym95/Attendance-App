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

public class StudentProfile extends AppCompatActivity {
    SharedPrefManager sharedPrefManager;
    String name,course,regNo,Email,course1;
    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static Context ctx;
    private TextView tvStudentName,studentName,studentRegNo,studentEmail,studentCourse;
    private Button btnAttendance,btnDetails;
    private LinearLayout detailsLayout;
    private boolean details = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        ActionBar actionBar = getSupportActionBar();
        // actionBar.setIcon(R.drawable.landscape);
        actionBar.setTitle("Student");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        tvStudentName = findViewById(R.id.tvStudenName);
        studentName = findViewById(R.id.detailsStudentName);
        studentRegNo = findViewById(R.id.detailsStudentRegNo);
        studentEmail = findViewById(R.id.detailsStudentEmail);
        studentCourse = findViewById(R.id.detailsStudentCourse);
        btnAttendance = findViewById(R.id.btnMarkAttendance);
        btnDetails = findViewById(R.id.btnDetails);
        detailsLayout = findViewById(R.id.detailsLyout);
        //sharedPrefManager = new SharedPrefManager(this);
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        name = sharedPreferences.getString("stuName",null);
        course = sharedPreferences.getString("stuCourse",null);
        regNo = sharedPreferences.getString("stuRegNo",null);
        Email = sharedPreferences.getString("stuEmail",null);
        tvStudentName.setText("Hi, "+name+" !");

        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(details == false){
                    detailsLayout.setVisibility(View.VISIBLE);
                    studentName.setText("Name : "+name);
                    studentRegNo.setText("Registration No: "+regNo);
                    studentCourse.setText("Course : "+course);
                    studentEmail.setText("Email : "+Email);
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
                Intent i = new Intent(StudentProfile.this,CheckStudent.class);
                startActivity(i);

            }
        });


    }


    private void detail(){
        studentName.setText("Name : "+name);
        studentRegNo.setText("Registration No: "+regNo);
        studentCourse.setText("Course : "+course);
        studentEmail.setText("Email : "+Email);
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
        if(!SharedPrefManager.getInstance(this).isStudentLoggedIn()){
            startActivity(new Intent(this,Login.class));
            finish();
            return;
        }
    }
}
