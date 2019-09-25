package com.example.yohan.attendenceapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private TextInputLayout logEmail;
    private TextInputLayout logPassword;
    private Button logButton;
    private TextView tvSIGNUP,tvforgotpassword;
    private Spinner spinnerLog;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        // actionBar.setIcon(R.drawable.landscape);
        actionBar.setTitle("Login");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        logButton = findViewById(R.id.logButton);
        logEmail = findViewById(R.id.LogEmail);
        logPassword = findViewById(R.id.LogPassword);
        spinnerLog = findViewById(R.id.spLog);
//        tvSIGNUP = findViewById(R.id.tvsignup);
        tvforgotpassword = findViewById(R.id.tvforgotpassword);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");



        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = logEmail.getEditText().getText().toString();
                String password = logPassword.getEditText().getText().toString();

                int log = spinnerLog.getSelectedItemPosition();

                if(log == 0){
                    adminLog(Email,password);
                }else if(log== 1){
                    studentLog(Email,password);
                }else if(log == 2){
                    teacherLog(Email,password);
                }


            }
        });

//        tvSIGNUP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(Login.this,createAccount.class);
//                startActivity(i);
//            }
//        });

        tvforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,forgetpassword.class);
                startActivity(i);
            }
        });
    }

    public void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(this).isadminLoggedIn()){
            startActivity(new Intent(this,MainActivity.class));
            finish();
            return;
        }else if(SharedPrefManager.getInstance(this).isStudentLoggedIn()){
            startActivity(new Intent(this,StudentProfile.class));
            finish();
            return;
        }else if(SharedPrefManager.getInstance(this).isTeacherLoggedIn()){
            startActivity(new Intent(this,TeacherProfile.class));
            finish();
            return;
        }

    }

    private void updateUI(){
        Intent i = new Intent(Login.this,MainActivity.class);
        startActivity(i);
    }

    //admin login
    private void adminLog(final String Email, final String Password){
        progressDialog.show();
//     final  String adminEmail = logEmail.getEditText().getText().toString();
//     final String adminPass = logPassword.getEditText().getText().toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_ADMIN_LOG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Course :-", "["+response+"]");

                        try{
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                //int id = obj.getInt("id");
                                //int id = Integer.v;
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .adminLogin(
                                                obj.getInt("id"),
//                                                obj.getString("username"),
                                                obj.getString("adminEmail")
                                        );
                                progressDialog.dismiss();
                                Intent i = new Intent(Login.this,MainActivity.class);
                                startActivity(i);
                                finish();
                               // progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Admin Logged!",Toast.LENGTH_SHORT).show();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();


                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("adminEmail",Email);
                params.put("adminPass",Password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    //STUDENT LOG
    private void studentLog(final String Email, final String Password){
        progressDialog.show();
//     final  String adminEmail = logEmail.getEditText().getText().toString();
//     final String adminPass = logPassword.getEditText().getText().toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_STUDENT_LOG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Course :-", "["+response+"]");

                        try{
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                //int id = obj.getInt("id");
                                //int id = Integer.v;
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .studentLogin(
                                                obj.getString("stuRegNo"),
                                                obj.getString("stuEmail"),
                                                obj.getString("stuName"),
                                                obj.getString("stuCourse")
                                        );
                                progressDialog.dismiss();
                                Intent i = new Intent(Login.this,StudentProfile.class);
                                startActivity(i);
                                finish();
                                // progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"student Logged!",Toast.LENGTH_SHORT).show();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();


                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("stuEmail",Email);
                params.put("stuPassword",Password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    //TEACHERS LOG
    private void teacherLog(final String Email, final String Password){
        progressDialog.show();
//     final  String adminEmail = logEmail.getEditText().getText().toString();
//     final String adminPass = logPassword.getEditText().getText().toString();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_TEACHER_LOG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Course :-", "["+response+"]");

                        try{
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                //int id = obj.getInt("id");
                                //int id = Integer.v;
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .teacherLogin(
                                                obj.getString("teacherEmail"),
                                                obj.getString("teacherName"),
                                                obj.getString("teacherCourse")
                                        );
                                progressDialog.dismiss();
                                Intent i = new Intent(Login.this,TeacherProfile.class);
                                startActivity(i);
                                finish();
                                // progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Teacher Logged!",Toast.LENGTH_SHORT).show();
                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),obj.getString("message"),Toast.LENGTH_LONG).show();

                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();


                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("teacherEmail",Email);
                params.put("teacherPassword",Password);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
}
