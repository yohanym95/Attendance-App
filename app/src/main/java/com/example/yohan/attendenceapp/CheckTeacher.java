package com.example.yohan.attendenceapp;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class CheckTeacher extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    private TextView textResult;
    String result;
    String date;
    String name,Email,course1;
    private static final String SHARED_PREF_NAME = "mysharedpref12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_teacher);

        scannerView = findViewById(R.id.idTeacherScanner);
        textResult = findViewById(R.id.txtTeacherResult);

        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        name = sharedPreferences.getString("teacherName",null);
        Email = sharedPreferences.getString("teacherEmail",null);
        getDate();

        //Request Permission
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(CheckTeacher.this);
                        scannerView.startCamera();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(CheckTeacher.this,"You must accept this permission",Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

    @Override
    protected void onDestroy() {
        scannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    public void handleResult(Result rawResult) {
        result = rawResult.getText();
        textResult.setText("Submitted");
        addTeacher(date,name,Email,"Yes",result);
        scannerView.startCamera();
        Toast.makeText(CheckTeacher.this,"Now you are in "+result+ " Course",Toast.LENGTH_LONG).show();
    }

    private void getDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yyyy / MM / dd ");
        date = mdformat.format(calendar.getTime());
    }

    private void addTeacher(final String date, final String teacherName,final String teacherEmail, final String Attendance,final String course){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_Teacher_ATTENDANCE,
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

                params.put("Date",date);
                params.put("teacherName",teacherName);
                params.put("teacherEmail",teacherEmail);
                params.put("Attendance",Attendance);
                params.put("Course",course);


                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest );

    }
}
