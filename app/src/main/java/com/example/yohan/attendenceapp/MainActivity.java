package com.example.yohan.attendenceapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CardView teacher,student,teacherAtten,studentAtten;
    TextView tvTeacher,tvStudent,tvTeacherAtten,tvStudentAtten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        // actionBar.setIcon(R.drawable.landscape);
        actionBar.setTitle("Attendence");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        teacher = findViewById(R.id.teacherMain);
        student = findViewById(R.id.studentMain);
        teacherAtten = findViewById(R.id.teacherAttendanceMain);
        studentAtten = findViewById(R.id.studentAttendanceMain);

        tvTeacher = findViewById(R.id.tvteacherMain);
        tvStudent = findViewById(R.id.tvstudentMain);
        tvTeacherAtten = findViewById(R.id.tvteacherAttendanceMain);
        tvStudentAtten = findViewById(R.id.tvstudentAttendanceMain);

        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Teachers.class);
                startActivity(i);
            }
        });


        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Students.class);
                startActivity(i);

            }
        });

        teacherAtten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,teacher_Attendence.class);
                startActivity(i);
            }
        });

        studentAtten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,attendence.class);
                startActivity(i);
            }
        });


        tvTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Teachers.class);
                startActivity(i);

            }
        });

        tvStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Students.class);
                startActivity(i);
            }
        });

        tvTeacherAtten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,teacher_Attendence.class);
                startActivity(i);

            }
        });

        tvStudentAtten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,attendence.class);
                startActivity(i);
            }
        });




        



    }


    @Override
    public void onStart() {
        super.onStart();
        if(!SharedPrefManager.getInstance(this).isadminLoggedIn()){
            startActivity(new Intent(this,Login.class));
            finish();
            return;
        }
    }

//    private void updateUI(){
//        Intent i = new Intent(MainActivity.this, Login.class);
//        startActivity(i);
//    }

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
}
