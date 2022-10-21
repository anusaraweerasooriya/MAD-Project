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

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder> {

    private Context context;
    private ArrayList diary_id, diary_title, diary_date, diary_body;

    //DiaryAdapter constructor
    public DiaryAdapter(Context context, ArrayList diary_id, ArrayList diary_title, ArrayList diary_date, ArrayList diary_body) {
        this.context = context;
        this.diary_id = diary_id;
        this.diary_title = diary_title;
        this.diary_date = diary_date;
        this.diary_body = diary_body;
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.diary_pages_layout, parent, false);
        return new DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        holder.text_diary_title.setText(String.valueOf(diary_id.get(position)));
        holder.text_diary_date.setText(String.valueOf(diary_date.get(position)));
        holder.text_diary_title.setText(String.valueOf(diary_title.get(position)));
    }

    @Override
    public int getItemCount() {
        return diary_id.size();
    }

    public class DiaryViewHolder extends RecyclerView.ViewHolder{

        TextView  text_diary_title, text_diary_date, text_diary_body;

        public DiaryViewHolder(@NonNull View itemView) {
            super(itemView);
            text_diary_title = itemView.findViewById(R.id.textDiaryTitle);
            text_diary_date = itemView.findViewById(R.id.textDiaryDate);
            text_diary_body = itemView.findViewById(R.id.textDiaryBody);
        }
    }
}
