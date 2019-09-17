package com.example.yohan.attendenceapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewAdapter> {

    private ArrayList<courseModel> dataList;
    private Context context;
    public onItemClicked mListner;

    public interface onItemClicked{
        void OnItemClick(int index);

    }
    public void SetOnItemClickListener(onItemClicked listner){
        mListner = listner;
    }

    public CourseAdapter(ArrayList<courseModel> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }



    public class CourseViewAdapter extends RecyclerView.ViewHolder{
        TextView courseName,courseNo,courseDep;

        public CourseViewAdapter(@NonNull View itemView) {
            super(itemView);
            this.courseName = itemView.findViewById(R.id.tvcourseName);
            this.courseNo = itemView.findViewById(R.id.tvcourseNo);
            this.courseDep = itemView.findViewById(R.id.tvcourseDep);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
    @NonNull
    @Override
    public CourseViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coursedetails,viewGroup,false);


        return new CourseViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewAdapter courseViewAdapter, int i) {

        courseModel model = dataList.get(i);

        courseViewAdapter.courseName.setText(model.courseName);
        courseViewAdapter.courseNo.setText(model.courseNo);
        courseViewAdapter.courseDep.setText(model.courseDep);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
