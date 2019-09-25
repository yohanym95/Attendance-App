package com.example.yohan.attendenceapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class forgetpassword extends AppCompatActivity {
    private TextInputLayout resetEmail,resetRegNo,resetPassword;
    private Button resetButton;
    FirebaseAuth mAuth;
    private Spinner spinnerLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        mAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        // actionBar.setIcon(R.drawable.landscape);
        actionBar.setTitle("Reset Password");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        resetEmail = findViewById(R.id.reset_Email);
//        resetRegNo = findViewById(R.id.reset_RegNo);
        resetPassword = findViewById(R.id.reset_Password);
        resetButton = findViewById(R.id.btnresetPassword1);
        spinnerLog = findViewById(R.id.spforgetpassword);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = resetEmail.getEditText().getText().toString();
//                String Regno = resetRegNo.getEditText().getText().toString();
                String Password = resetPassword.getEditText().getText().toString();



                int log = spinnerLog.getSelectedItemPosition();

                if(log == 0){
                    resetAdminPassword(Email,Password);

                }else if(log== 1){
                    resetStudemtPassword(Email,Password);
                }else if(log == 2){
                    resetTeacherPassword(Email,Password);
                }

            }
        });


    }

    public void resetStudemtPassword(final String email, final String Password){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_STUDENT_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Course :-", "["+response+"]");

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(),jsonObject.getString("message")+"",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(forgetpassword.this,Login.class);
                            startActivity(i);
                            finish();
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

                params.put("stuPassword",Password);
                params.put("stuEmail",email);
//                params.put("Attendance",Attendance);
//                params.put("course",course);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest );

    }

    public void resetTeacherPassword(final String email, final String Password){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_TEACHER_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Course :-", "["+response+"]");

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(),jsonObject.getString("message")+"",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(forgetpassword.this,Login.class);
                            startActivity(i);
                            finish();
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

                params.put("teacherPassword",Password);
                params.put("teacherEmail",email);
//                params.put("Attendance",Attendance);
//                params.put("course",course);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest );

    }


    public void resetAdminPassword(final String email, final String Password){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_ADMIN_PASSWORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Course :-", "["+response+"]");

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(),jsonObject.getString("message")+"",Toast.LENGTH_LONG).show();
                            Intent i = new Intent(forgetpassword.this,Login.class);
                            startActivity(i);
                            finish();
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

                params.put("adminPass",Password);
                params.put("adminEmail",email);
//                params.put("Attendance",Attendance);
//                params.put("course",course);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest );

    }

}
