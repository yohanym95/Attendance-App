package com.example.yohan.attendenceapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewAdapter> {

    private ArrayList<studentModel> dataList;
    private Context context;
    public  onItemClicked mListner;

    public interface onItemClicked{
        void OnItemClick(int index);

    }
    public void SetOnItemClickListener(onItemClicked listner){
        mListner = listner;
    }

    public StudentAdapter(ArrayList<studentModel> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }


    public class StudentViewAdapter extends RecyclerView.ViewHolder{

        TextView stuName,stuRegNo,stuEmail,stuCourse;

        public StudentViewAdapter(@NonNull View itemView) {
            super(itemView);
            this.stuName = itemView.findViewById(R.id.tvstuName);
            this.stuRegNo = itemView.findViewById(R.id.tvstuRegName);
            this.stuCourse = itemView.findViewById(R.id.tvstuCourse);
            this.stuEmail = itemView.findViewById(R.id.tvstuEmail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }

    @NonNull
    @Override
    public StudentViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.studentdetails,viewGroup,false);


        return new StudentViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewAdapter studentViewAdapter, int i) {

        studentModel model = dataList.get(i);

        studentViewAdapter.stuName.setText(model.stuName);
        studentViewAdapter.stuEmail.setText(model.stuEmail);
        studentViewAdapter.stuRegNo.setText(model.stuRegNo);
        studentViewAdapter.stuCourse.setText(model.stuCourse);


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
