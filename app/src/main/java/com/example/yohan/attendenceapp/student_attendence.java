package com.example.yohan.attendenceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class student_attendence extends AppCompatActivity implements StuAttendanceAdapter.onItemClicked {
    String course,date;
    private RequestQueue mRequestQueue;
    private RecyclerView recyclerView;
    private ArrayList<StuAttendanceModel> list;
    private LinearLayoutManager linearLayoutManager;
    private StuAttendanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendence);

        recyclerView = findViewById(R.id.stuAttendanceRecyclerView);


        Bundle bundle = getIntent().getExtras();
         course =bundle.getString("course");
         date =bundle.getString("date");

        Toast.makeText(getApplicationContext(),""+course+" "+date+" Done",Toast.LENGTH_LONG).show();

        list = new ArrayList<StuAttendanceModel>();
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        mRequestQueue = Volley.newRequestQueue(this);
        parseJson(date,course);




    }

    private void parseJson(final String date, final String course) {
        String url = Constants.URL_GET_STUDENT_ATTENDANCE;

        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("date", date);
            request.put("course", course);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Course :-", "["+response+"]");

                try {
                    JSONArray jsonArray = response.getJSONArray("attendance");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject student = jsonArray.getJSONObject(i);

                        String name = student.getString("stuName");
                        String RegNo = student.getString("stuRegNo");
//                        mItemList.add(new items(imageURL,creatorNmae,likes));
                        list.add(new StuAttendanceModel(name,RegNo));


                    }

                    adapter = new StuAttendanceAdapter(list,student_attendence.this);
                    recyclerView.setAdapter(adapter);
                    adapter.SetOnItemClickListener(student_attendence.this);
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
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//
//                params.put("date",date);
//                params.put("course",course);
//
//                return params;
//
//            }
//        };
        mRequestQueue.add(jsonObjectRequest);
    }

    @Override
    public void OnItemClick(int index) {

    }
}
