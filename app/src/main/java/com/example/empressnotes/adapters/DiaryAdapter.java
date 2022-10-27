package com.example.empressnotes.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empressnotes.R;
import com.example.empressnotes.activities.UpdateDiary;

import java.io.InputStream;
import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList diary_id, diary_title, diary_date, diary_body, diary_image;
    int position;

    //DiaryAdapter constructor
    public DiaryAdapter(Activity activity, Context context, ArrayList diary_id, ArrayList diary_title, ArrayList diary_date, ArrayList diary_body, ArrayList diary_image) {
        this.activity = activity;
        this.context = context;
        this.diary_id = diary_id;
        this.diary_title = diary_title;
        this.diary_date = diary_date;
        this.diary_body = diary_body;
        this.diary_image = diary_image;
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.diary_pages_layout, parent, false);
        return new DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        holder.text_diary_title.setText(String.valueOf(diary_id.get(position)));
        holder.text_diary_date.setText(String.valueOf(diary_date.get(position)));
        holder.text_diary_title.setText(String.valueOf(diary_title.get(position)));

        try {
            String date = String.valueOf(diary_date.get(position));

            String[] s = date.split(" ");
            String day = s[0];
            String dayI = s[1];
            String month = s[2];

            holder.text_diary_dayI.setText(dayI);
            holder.text_diary_day.setText(day);
            holder.text_diary_month.setText(month);

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.layoutDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateDiary.class);
                intent.putExtra("id", String.valueOf(diary_id.get(position)));
                intent.putExtra("title", String.valueOf(diary_title.get(position)));
                intent.putExtra("date", String.valueOf(diary_date.get(position)));
                intent.putExtra("body", String.valueOf(diary_body.get(position)));
                intent.putExtra("image",String.valueOf(diary_image.get(position)));
                //intent.putExtra("id", String.valueOf(diary_id.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return diary_id.size();
    }

    public class DiaryViewHolder extends RecyclerView.ViewHolder{

        TextView  text_diary_title, text_diary_date, text_diary_body,
        text_diary_dayI, text_diary_day, text_diary_month, text_diary_image ;
        LinearLayout layoutDiary;


        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            text_diary_title = itemView.findViewById(R.id.textDiaryTitle);
            text_diary_date = itemView.findViewById(R.id.textDiaryDate);
            text_diary_body = itemView.findViewById(R.id.textDiaryBody);
            text_diary_day = itemView.findViewById(R.id.diaryDay);
            text_diary_dayI= itemView.findViewById(R.id.diaryDate);
            text_diary_month = itemView.findViewById(R.id.diaryMonth);
            layoutDiary = itemView.findViewById(R.id.diary_pages_layout);



        }
    }
}
