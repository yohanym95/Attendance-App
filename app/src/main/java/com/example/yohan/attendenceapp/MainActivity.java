package com.example.yohan.attendenceapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ListView listView;
    private ArrayList<String> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        // actionBar.setIcon(R.drawable.landscape);
        actionBar.setTitle("Attendence");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        listView = findViewById(R.id.admin_list);
        myList = new ArrayList<>();

        myList.add("Teachers");
        myList.add("Student");
        myList.add("Attendence");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.select_dialog_item,myList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent i = new Intent(MainActivity.this,Teachers.class);
                    startActivity(i);
                }else if(position == 1){
                    Intent i = new Intent(MainActivity.this,Students.class);
                    startActivity(i);
                }else if(position == 2){
                    Intent i = new Intent(MainActivity.this,attendence.class);
                    startActivity(i);
                }

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
