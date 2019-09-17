package com.example.yohan.attendenceapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter> {

    private ArrayList<teachersModel> dataList;
    private Context context;
    public  onItemClicked mListner;

    public interface onItemClicked{
        void OnItemClick(int index);

    }
    public void SetOnItemClickListener(onItemClicked listner){
        mListner = listner;
    }

    public TeacherAdapter(ArrayList<teachersModel> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }



    public class TeacherViewAdapter extends RecyclerView.ViewHolder{

        TextView teacName,teacEmail,teacCourse;

        public TeacherViewAdapter(@NonNull View itemView) {
            super(itemView);

            this.teacName = itemView.findViewById(R.id.tvteachName);
            this.teacEmail = itemView.findViewById(R.id.tvteachEmail);
            this.teacCourse = itemView.findViewById(R.id.tvteachCourse);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }
    }

    @NonNull
    @Override
    public TeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teacherdetails,viewGroup,false);


        return new TeacherViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewAdapter teacherViewAdapter, int i) {

        teachersModel model = dataList.get(i);

        teacherViewAdapter.teacName.setText(model.teacName);
        teacherViewAdapter.teacEmail.setText(model.teacEmail);
        teacherViewAdapter.teacCourse.setText(model.teacCourse);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
