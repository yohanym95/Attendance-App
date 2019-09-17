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

public class Teachers extends AppCompatActivity implements TeacherAdapter.onItemClicked {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private TextInputLayout teacName,teacEmail,teacRegNo,teacPassword;
    private CheckBox teacCBCis,teacCBNr,teacCBPst,teacCBSppm,teacCBFst;
    HashMap<String,String> teacherData;
    private String cource;
    ValueEventListener valueEventListener;
    private ArrayList<teachersModel> list;
    private LinearLayoutManager linearLayoutManager;
    private TeacherAdapter adapter;
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        floatingActionButton = findViewById(R.id.fab1);
        recyclerView = findViewById(R.id.recycleView_teachers);

        ActionBar actionBar = getSupportActionBar();
        // actionBar.setIcon(R.drawable.landscape);
        actionBar.setTitle("Add Teacher");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        list = new ArrayList<teachersModel>();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mRequestQueue = Volley.newRequestQueue(this);
        parseJson();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCustomDialog();
            }
        });
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

//        if(item.getItemId() == R.id.main_logout){
//            mAuth.getInstance().signOut();
//            Intent i = new Intent(Teachers.this,Login.class);
//            startActivity(i);
//        }

        return true;
    }


//    private void InitListner(){
//        valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                list.clear();
//                for (DataSnapshot data : dataSnapshot.getChildren()){
//                    teachersModel model = data.getValue(teachersModel.class);
//                    list.add(model);
//
//                }
//                if(!list.isEmpty()){
//                    adapter = new TeacherAdapter(list,getApplicationContext());
//                    recyclerView.setAdapter(adapter);
//                    adapter.notifyDataSetChanged();
//                    adapter.SetOnItemClickListener(Teachers.this);
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                Toast.makeText(Teachers.this,"Failed",Toast.LENGTH_SHORT).show();
//
//            }
//
//        };
//    }
private void parseJson() {
    String url = Constants.URL_GET_TEACHER;

    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            Log.i("teacherssss :-", "["+response+"]");

            try {
                JSONArray jsonArray = response.getJSONArray("teachers");
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject student = jsonArray.getJSONObject(i);

                    String teacherName = student.getString("teacherName");
                    String teacherEmail = student.getString("teacherEmail");
                    String teacherCourse = student.getString("teacherCourse");

//                        mItemList.add(new items(imageURL,creatorNmae,likes));
                    list.add(new teachersModel(teacherName,teacherEmail,teacherCourse));


                }

                adapter = new TeacherAdapter(list,Teachers.this);
                recyclerView.setAdapter(adapter);
                adapter.SetOnItemClickListener(Teachers.this);
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
    private void myCustomDialog(){

        final AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        myDialog.setTitle("Add Teacher");
//        myDialog.setMessage("Please add the all details");

        LayoutInflater inflater = LayoutInflater.from(this);
        View myCustomlayout = inflater.inflate(R.layout.teacherscustom,null);

        teacName = myCustomlayout.findViewById(R.id.teacherName);
        teacEmail = myCustomlayout.findViewById(R.id.teachersEmail);
       // teacRegNo = myCustomlayout.findViewById(R.id.studentRegNo);
        teacPassword = myCustomlayout.findViewById(R.id.teacherPassword);
//        btnAddStudent = myCustomlayout.findViewById(R.id.stubtnAdd);
        teacCBCis = myCustomlayout.findViewById(R.id.teacherCBCIS);
        teacCBNr = myCustomlayout.findViewById(R.id.teacherCBNR);
        teacCBFst = myCustomlayout.findViewById(R.id.teacherCBFST);
        teacCBPst = myCustomlayout.findViewById(R.id.teacherCBPST);
        teacCBSppm = myCustomlayout.findViewById(R.id.teacherCBSSPE);

        myDialog.setView(myCustomlayout);


        myDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String teaName = teacName.getEditText().getText().toString();
                final String teaEmail = teacEmail.getEditText().getText().toString();
               // final String StuRegNo = stuRegNo.getEditText().getText().toString();
                final String teaPassword = teacPassword.getEditText().getText().toString();

                if(teacCBCis.isChecked()){

                    cource = "CIS";
                }else if(teacCBNr.isChecked()){

                    cource = "NR";
                }else if(teacCBPst.isChecked()){


                    cource = "PST";

                }else if(teacCBSppm.isChecked()){

                    cource= "Sport";
                }else if(teacCBFst.isChecked()){

                    cource = "FST";
                }

//                teacherData = new HashMap<>();
//                teacherData.put("teacName",teaName);
//                teacherData.put("teacEmail",teaEmail);
//                //teacherData.put("stuRegNo",StuRegNo);
//                teacherData.put("teacCourse",cource);

                if(teaName.length() == 0){
                    Toast.makeText(getApplicationContext(),"Name null",Toast.LENGTH_LONG).show();
                }else{
                    if(teaEmail.length() == 0){
                        Toast.makeText(getApplicationContext(),"Email null",Toast.LENGTH_LONG).show();
                    }else {
                        if(teaPassword.length() == 0){
                            Toast.makeText(getApplicationContext(),"Password null",Toast.LENGTH_LONG).show();
                        }else {
                            if(cource.length() == 0){
                                Toast.makeText(getApplicationContext(),"Course null",Toast.LENGTH_LONG).show();
                            }else {
                                addTeacher(teaName,teaEmail,cource,teaPassword);

                            }

                        }

                    }
                }



             //   addTeacher(teaName,teaEmail,cource,teaPassword);
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

    private void addTeacher(final String teacherName, final String teacherEmail, final String teacherCourse,final String teacherPassword){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_TEACHER,
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

                params.put("teacherName",teacherName);
                params.put("teacherEmail",teacherEmail);
                params.put("teacherCourse",teacherCourse);
                params.put("teacherPassword",teacherPassword);


                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest );

    }

    @Override
    public void OnItemClick(int index) {

    }
}
