package com.example.empressnotes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empressnotes.R;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private Context context;
    private ArrayList task_id, task_title, task_description, task_date, task_time, task_weblink, task_status;

    //constructor
    public ToDoAdapter(Context context, ArrayList task_id, ArrayList task_title, ArrayList task_description,
                       ArrayList task_date, ArrayList task_time, ArrayList task_weblink, ArrayList task_status) {
        this.context = context;
        this.task_id = task_id;
        this.task_title = task_title;
        this.task_description = task_description;
        this.task_date = task_date;
        this.task_time = task_time;
        this.task_weblink = task_weblink;
        this.task_status = task_status;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.todo_task_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.MyViewHolder holder, int position) {
        holder.txt_task_title.setText(String.valueOf(task_title.get(position)));
        holder.txt_task_description.setText(String.valueOf(task_description.get(position)));
        holder.txt_task_time.setText(String.valueOf(task_time.get(position)));
        holder.txt_task_weblink.setText(String.valueOf(task_weblink.get(position)));
        holder.txt_task_status.setText(String.valueOf(task_status.get(position)));

        try {
            String date = String.valueOf(task_date.get(position));

            String[] s = date.split("/");
            String day = s[0];
            String month = s[1];
            String year = s[2];

            holder.txt_task_day.setText(day);
            holder.txt_task_month.setText(month);
            holder.txt_task_year.setText(year);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return task_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_task_title, txt_task_description, txt_task_day, txt_task_month, txt_task_year,
                txt_task_time, txt_task_weblink, txt_task_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_task_title = itemView.findViewById(R.id.taskTitle);
            txt_task_description = itemView.findViewById(R.id.taskDescription);
            txt_task_day = itemView.findViewById(R.id.taskDay);
            txt_task_month = itemView.findViewById(R.id.taskMonth);
            txt_task_year = itemView.findViewById(R.id.taskYear);
            txt_task_time = itemView.findViewById(R.id.taskTime);
            txt_task_weblink = itemView.findViewById(R.id.taskWeblink);
            txt_task_status = itemView.findViewById(R.id.taskStatus);
        }
    }
}
