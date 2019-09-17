package com.example.yohan.attendenceapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class StuAttendanceAdapter  extends RecyclerView.Adapter<StuAttendanceAdapter.StuAttendanceViewAdapter> {

    private ArrayList<StuAttendanceModel> dataList;
    private Context context;
    public onItemClicked mListner;



    public interface onItemClicked{
        void OnItemClick(int index);

    }
    public void SetOnItemClickListener(onItemClicked listner){
        mListner = listner;
    }

    public StuAttendanceAdapter(ArrayList<StuAttendanceModel> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    public class StuAttendanceViewAdapter extends RecyclerView.ViewHolder{
        TextView stuName,stuRegNo;

        public StuAttendanceViewAdapter(@NonNull View itemView) {
            super(itemView);

            this.stuName = itemView.findViewById(R.id.tvAttendanceName);
            this.stuRegNo = itemView.findViewById(R.id.tvAttendanceRegNo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }


    @NonNull
    @Override
    public StuAttendanceViewAdapter onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stuattendancelayout,viewGroup,false);

        return new StuAttendanceViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StuAttendanceViewAdapter stuAttendanceViewAdapter, int i) {
        StuAttendanceModel model = dataList.get(i);

        stuAttendanceViewAdapter.stuName.setText(model.stuName);
        stuAttendanceViewAdapter.stuRegNo.setText(model.stuRegNo);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
