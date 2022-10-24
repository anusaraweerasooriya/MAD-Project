package com.example.empressnotes.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empressnotes.R;
import com.example.empressnotes.activities.ToDoUpdateTask;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.MyViewHolder> {

    private Context context;
    private ArrayList task_id, task_title, task_description, task_date, task_time, task_url, task_status;

    //constructor
    public ToDoAdapter(Context context, ArrayList task_id, ArrayList task_title, ArrayList task_description,
                       ArrayList task_date, ArrayList task_time, ArrayList task_url, ArrayList task_status) {
        this.context = context;
        this.task_id = task_id;
        this.task_title = task_title;
        this.task_description = task_description;
        this.task_date = task_date;
        this.task_time = task_time;
        this.task_url = task_url;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt_task_title.setText(String.valueOf(task_title.get(position)));
        holder.txt_task_description.setText(String.valueOf(task_description.get(position)));
        holder.txt_task_time.setText(String.valueOf(task_time.get(position)));
        holder.txt_task_url.setText(String.valueOf(task_url.get(position)));
        holder.txt_task_status.setText(String.valueOf(task_status.get(position)));

        // Split date components and format month
        try {
            String dateInput = String.valueOf(task_date.get(position));

            /*SimpleDateFormat inputFormat = new SimpleDateFormat("dd/mm/yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

            Date date = inputFormat.parse(dateInput);
            String dateOutput = outputFormat.format(date);*/

            String[] s = dateInput.split("/");
            String day = s[0];
            String month = s[1];
            String year = s[2];

            holder.txt_task_day.setText(day);
            holder.txt_task_month.setText(month);
            holder.txt_task_year.setText(year);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Recyclerview onClickListener
        holder.layoutTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ToDoUpdateTask.class);
                intent.putExtra("id", String.valueOf(task_id.get(position)));
                intent.putExtra("title", String.valueOf(task_title.get(position)));
                intent.putExtra("description", String.valueOf(task_description.get(position)));
                intent.putExtra("date", String.valueOf(task_date.get(position)));
                intent.putExtra("time", String.valueOf(task_time.get(position)));
                context.startActivity(intent);
            }
        });

        // Options btn onClickListener
        /*holder.btn_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu taskPopUp = new PopupMenu(context, holder.btn_options);
                taskPopUp.inflate(R.menu.todo_option_menu);
                taskPopUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.task_completed_option:
                                //Confirm task complete action dialog box
                                Toast.makeText(context, "Completed", Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return task_id.size();
    }

    // Fetch activity components
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_task_title, txt_task_description, txt_task_day, txt_task_month, txt_task_year,
                txt_task_url, txt_task_time, txt_task_status;
        RelativeLayout layoutTask;
        ImageView btn_options;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_task_title = itemView.findViewById(R.id.taskTitle);
            txt_task_description = itemView.findViewById(R.id.taskDescription);
            txt_task_day = itemView.findViewById(R.id.taskDay);
            txt_task_month = itemView.findViewById(R.id.taskMonth);
            txt_task_year = itemView.findViewById(R.id.taskYear);
            txt_task_time = itemView.findViewById(R.id.taskTime);
            txt_task_url = itemView.findViewById(R.id.taskURL);
            txt_task_status = itemView.findViewById(R.id.taskStatus);

            layoutTask = itemView.findViewById(R.id.layoutTask);
            btn_options = itemView.findViewById(R.id.taskMoreOptions);
        }
    }
}
