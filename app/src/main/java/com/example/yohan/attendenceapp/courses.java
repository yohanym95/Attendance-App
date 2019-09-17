package com.example.yohan.attendenceapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class courses extends AppCompatActivity implements CourseAdapter.onItemClicked {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private TextInputLayout courseName,courseNo;
    HashMap<String,String> courseData;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabases;
    private DatabaseReference myRef;
    ValueEventListener valueEventListener;
    private ArrayList<courseModel> list;
   // private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CourseAdapter adapter;
    private RequestQueue mRequestQueue;
    private CheckBox CBCis,CBNr,CBPst,CBSppm,CBFst;
    private String cource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        ActionBar actionBar = getSupportActionBar();
        // actionBar.setIcon(R.drawable.landscape);
        actionBar.setTitle("Add Course");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        floatingActionButton = findViewById(R.id.fab2);
        recyclerView = findViewById(R.id.recycleView_course);
        mAuth = FirebaseAuth.getInstance();
        mDatabases = FirebaseDatabase.getInstance();
        myRef = mDatabases.getReference("Attendence").child("Course");
        list = new ArrayList<courseModel>();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mRequestQueue = Volley.newRequestQueue(this);
//        InitListner();
//        myRef.addValueEventListener(valueEventListener);
        parseJson();



        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCustomDialog();
            }
        });
    }

    private void parseJson() {
        String url = Constants.URL_GET_COURSE;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("courses");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject student = jsonArray.getJSONObject(i);

                        String courseName = student.getString("courseName");
                        String courseNo = student.getString("courseNo");
                        String courseDep = student.getString("courseDep");

//                        mItemList.add(new items(imageURL,creatorNmae,likes));
                        list.add(new courseModel(courseName,courseNo,courseDep));


                    }

                    adapter = new CourseAdapter(list,courses.this);
                    recyclerView.setAdapter(adapter);
                    adapter.SetOnItemClickListener(courses.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }
    private void InitListner(){
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    courseModel model = data.getValue(courseModel.class);
                    list.add(model);



                }
                if(!list.isEmpty()){
                    adapter = new CourseAdapter(list,getApplicationContext());
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.SetOnItemClickListener(courses.this);

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(courses.this,"Failed",Toast.LENGTH_SHORT).show();

            }

        };
    }

    private void myCustomDialog(){

        final AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        myDialog.setTitle("Add Course");
//        myDialog.setMessage("Please add the all details");

        LayoutInflater inflater = LayoutInflater.from(this);
        View myCustomlayout = inflater.inflate(R.layout.coursecustom,null);

        courseName = myCustomlayout.findViewById(R.id.courseName);
        courseNo = myCustomlayout.findViewById(R.id.courseNo);
        CBCis = myCustomlayout.findViewById(R.id.CBCourseCIS);
        CBNr = myCustomlayout.findViewById(R.id.CBCourseNR);
        CBFst = myCustomlayout.findViewById(R.id.CBCourseFST);
        CBPst = myCustomlayout.findViewById(R.id.CBCoursePST);
        CBSppm = myCustomlayout.findViewById(R.id.CBCourseSSPE);



        myDialog.setView(myCustomlayout);



        myDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                final String coursName = courseName.getEditText().getText().toString();
                final String coursNo = courseNo.getEditText().getText().toString();

                if(CBCis.isChecked()){

                    cource = "CIS";
                }else if(CBNr.isChecked()){

                    cource = "NR";
                }else if(CBPst.isChecked()){


                    cource = "PST";

                }else if(CBSppm.isChecked()){

                    cource= "Sport";
                }else if(CBFst.isChecked()){

                    cource = "FST";
                }

                courseData = new HashMap<>();
                courseData.put("courseName",coursName);
                courseData.put("courseNo",coursNo);




                addCourse(coursNo,coursName,cource);


                dialog.dismiss();

            }

        });

        myDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });

        myDialog.show();

    }

    @Override
    public void OnItemClick(int index) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       super.onCreateOptionsMenu(menu);

       getMenuInflater().inflate(R.menu.menu,menu);
       return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout){
            mAuth.getInstance().signOut();
            Intent i = new Intent(courses.this,Login.class);
            startActivity(i);
        }

        return true;
    }

    private void addCourse(final String courseNo,final String courseName,final String courseDep ){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_COURSE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Course :-", "["+response+"]");

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(),jsonObject.getString("message")+"",Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("courseNo",courseNo);
                params.put("courseName",courseName);
                params.put("courseDep",courseDep);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest );


    }
}
