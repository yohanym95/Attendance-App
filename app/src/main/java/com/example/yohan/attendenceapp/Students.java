package com.example.yohan.attendenceapp;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import com.google.firebase.auth.FirebaseUser;
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

public class Students extends AppCompatActivity implements StudentAdapter.onItemClicked{

    private TextInputLayout stuName1,stuEmail1,stuRegNo1,stuPassword1;
    private Button btnAddStudent;
    private CheckBox CBCis,CBNr,CBPst,CBSppm,CBFst;
   // Dialog myDialog;
    private String cource;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabases;
    private DatabaseReference myRef,myRef1;
    private FloatingActionButton floatingActionButton;
    HashMap<String,String> studentData;
    //private FirebaseAuth mAuth;
    ValueEventListener valueEventListener;
    private ArrayList<studentModel> list;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private StudentAdapter adapter;
    private RequestQueue mRequestQueue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        mAuth = FirebaseAuth.getInstance();
        mDatabases = FirebaseDatabase.getInstance();

        ActionBar actionBar = getSupportActionBar();
        // actionBar.setIcon(R.drawable.landscape);
        actionBar.setTitle("Add Student");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        myRef = mDatabases.getReference("Attendence").child("Student");
        myRef1 = mDatabases.getReference("Attendence").child("StudentEmail");
        list = new ArrayList<studentModel>();
        recyclerView = findViewById(R.id.recycleView_student);
       // recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
//        InitListner();
//        myRef.addValueEventListener(valueEventListener);
        mRequestQueue = Volley.newRequestQueue(this);
        parseJson();


        floatingActionButton = findViewById(R.id.fab);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCustomDialog();
            }
        });

    }

    private void parseJson() {
        String url = Constants.URL_GET_STUDENT;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("students");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject student = jsonArray.getJSONObject(i);

                        String name = student.getString("stuName");
                        String email = student.getString("stuEmail");
                        String RegNo = student.getString("stuRegNo");
                        String course = student.getString("stuCourse");
//                        mItemList.add(new items(imageURL,creatorNmae,likes));
                        list.add(new studentModel(name,email,RegNo,course));


                    }

                    adapter = new StudentAdapter(list,Students.this);
                    recyclerView.setAdapter(adapter);
                    adapter.SetOnItemClickListener(Students.this);
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

//    private void InitListner(){
//        valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                list.clear();
//                for (DataSnapshot data : dataSnapshot.getChildren()){
//                    studentModel model = data.getValue(studentModel.class);
//                    list.add(model);
//
//                }
//                if(!list.isEmpty()){
//                    adapter = new StudentAdapter(list,getApplicationContext());
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                    adapter.SetOnItemClickListener(Students.this);
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                Toast.makeText(Students.this,"Failed",Toast.LENGTH_SHORT).show();
//
//            }
//
//        };
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu1,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.main_logout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this,Login.class));
                break;
            case R.id.main_Refresh:
                parseJson();
                break;
        }
        return true;
    }


    private void myCustomDialog(){

        final AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        myDialog.setTitle("Add Student");
//        myDialog.setMessage("Please add the all details");

        LayoutInflater inflater = LayoutInflater.from(this);
        View myCustomlayout = inflater.inflate(R.layout.studentcustom,null);

        stuName1 = myCustomlayout.findViewById(R.id.studentName);
        stuEmail1 = myCustomlayout.findViewById(R.id.studentEmail);
        stuRegNo1 = myCustomlayout.findViewById(R.id.studentRegNo);
        stuPassword1 = myCustomlayout.findViewById(R.id.studentPassword);
//        btnAddStudent = myCustomlayout.findViewById(R.id.stubtnAdd);
        CBCis = myCustomlayout.findViewById(R.id.CBCIS);
        CBNr = myCustomlayout.findViewById(R.id.CBNR);
        CBFst = myCustomlayout.findViewById(R.id.CBFST);
        CBPst = myCustomlayout.findViewById(R.id.CBPST);
        CBSppm = myCustomlayout.findViewById(R.id.CBSSPE);

        myDialog.setView(myCustomlayout);

        myDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String StuName = stuName1.getEditText().getText().toString();
                final String StuEmail = stuEmail1.getEditText().getText().toString();
                final String StuRegNo = stuRegNo1.getEditText().getText().toString();
                final String StuPassword = stuPassword1.getEditText().getText().toString();

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

                if(StuName.length() == 0){
                    Toast.makeText(getApplicationContext(),"Required Name",Toast.LENGTH_LONG).show();
                }else{
                    if(StuEmail.length() == 0){
                        Toast.makeText(getApplicationContext(),"Email null",Toast.LENGTH_LONG).show();
                    }else {
                        if(StuPassword.length() == 0 && StuPassword.length()>6){
                            Toast.makeText(getApplicationContext(),"Required Password and Password length should be more than 6",Toast.LENGTH_LONG).show();
                        }else {
                            if(cource.length() == 0){
                                Toast.makeText(getApplicationContext(),"Course null",Toast.LENGTH_LONG).show();
                            }else {
                                addStudent(StuName,StuEmail,StuRegNo,cource,StuPassword);

                            }

                        }

                    }
                }


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

    private void addStudent(final String stuName, final String stuEmail, final String stuRegNo, final String stuCourse,final String stuPassword){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_STUDENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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

                params.put("stuName",stuName);
                params.put("stuEmail",stuEmail);
                params.put("stuRegNo",stuRegNo);
                params.put("stuCourse",stuCourse);
                params.put("stuPassword",stuPassword);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest );

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser == null){
//            updateUI();
//        }
    }

    private void updateUI(){
        Intent i = new Intent(Students.this, Login.class);
        startActivity(i);
    }

    @Override
    public void OnItemClick(int index) {

    }
}
