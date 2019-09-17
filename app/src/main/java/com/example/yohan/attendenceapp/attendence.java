package com.example.yohan.attendenceapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class attendence extends AppCompatActivity  implements StuAttendanceAdapter.onItemClicked {

    private TextView tvDate;
    private ImageView ivCalendar;
    String Date;
    private CalendarView calendarView;
    private Button btnCourse,btnStuAttendance;
    private CheckBox CBCis,CBNr,CBPst,CBSppm,CBFst;
    String cource;
    String course,date;
    private RequestQueue mRequestQueue;
    private RecyclerView recyclerView;
    private ArrayList<StuAttendanceModel> list;
    private LinearLayoutManager linearLayoutManager;
    private StuAttendanceAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);

        tvDate = findViewById(R.id.tvDate);
        ivCalendar = findViewById(R.id.ivCalenderView);
       // btnCourse = findViewById(R.id.btnCourse);
        btnStuAttendance = findViewById(R.id.btnstuAttendance);
        CBCis = findViewById(R.id.attendanceCBCIS);
        CBNr = findViewById(R.id.attendanceCBNR);
        CBFst = findViewById(R.id.attendanceCBFST);
        CBPst = findViewById(R.id.attendanceCBPST);
        CBSppm = findViewById(R.id.attendanceCBSSPE);
        recyclerView = findViewById(R.id.rvstuAttendance);

        list = new ArrayList<StuAttendanceModel>();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        mRequestQueue = Volley.newRequestQueue(this);





        btnStuAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                date = tvDate.getText().toString().trim();
                course = cource;
                list.clear();
                parseJson(Date,cource);

            }
        });


        ivCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCustomDialog();

            }
        });

    }

    private void parseJson(final String date, final String course) {
        String url = Constants.URL_GET_STUDENT_ATTENDANCE;


        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Course :-", "["+response+"]");
                        try{
                    JSONObject OBJ = new JSONObject(response);
                    JSONArray jsonArray = OBJ.getJSONArray("attendance");
                            Log.i("Course :-", "["+jsonArray+"]");

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject student = jsonArray.getJSONObject(i);

                        String name = student.getString("stuName");
                        String RegNo = student.getString("stuRegNo");
//                        mItemList.add(new items(imageURL,creatorNmae,likes));
                        list.add(new StuAttendanceModel(name,RegNo));

                    }

                    adapter = new StuAttendanceAdapter(list,attendence.this);
                    recyclerView.setAdapter(adapter);
                    adapter.SetOnItemClickListener(attendence.this);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("date",date);
                params.put("course",course);

                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void myCustomDialog(){

        final AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        myDialog.setTitle("Add Date");
//        myDialog.setMessage("Please add the all details");

        LayoutInflater inflater = LayoutInflater.from(this);
        View myCustomlayout = inflater.inflate(R.layout.calendarlayout,null);

        calendarView = myCustomlayout.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Date = year+"/"+(month+1)+"/"+dayOfMonth;
            }
        });
        myDialog.setView(myCustomlayout);

        myDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                tvDate.setText(Date.trim());
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
}
