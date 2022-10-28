package com.example.empressnotes.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.empressnotes.R;
import com.example.empressnotes.activities.UpdateNote;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    private Context context;
    private ArrayList note_id, note_title, note_body, note_datetime;
    Activity activity;

    Animation note_translate_anim;

    //constructor
    public NotesAdapter(Activity activity, Context context, ArrayList note_id, ArrayList note_title, ArrayList note_body, ArrayList note_datetime) {
        this.activity = activity;
        this.context = context;
        this.note_id = note_id;
        this.note_title = note_title;
        this.note_body = note_body;
        this.note_datetime = note_datetime;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notes_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt_note_id.setText(String.valueOf(note_id.get(position)));
        holder.txt_note_title.setText(String.valueOf(note_title.get(position)));
        holder.txt_note_body.setText(String.valueOf(note_body.get(position)));
        holder.txt_note_datetime.setText(String.valueOf(note_datetime.get(position)));
        holder.notes_raw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateNote.class);
                intent.putExtra("id", String.valueOf(note_id.get(position)));
                intent.putExtra("title", String.valueOf(note_title.get(position)));
                intent.putExtra("note", String.valueOf(note_body.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return note_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_note_id, txt_note_title, txt_note_body, txt_note_datetime;
        LinearLayout notes_raw;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_note_id = itemView.findViewById(R.id.txt_NoteID);
            txt_note_title = itemView.findViewById(R.id.txt_NoteTitle);
            txt_note_body = itemView.findViewById(R.id.txt_NoteNody);
            txt_note_datetime = itemView.findViewById(R.id.txt_datetime);
            notes_raw = itemView.findViewById(R.id.notesRaw);

            //Animate Recyclerview
            note_translate_anim = AnimationUtils.loadAnimation(context, R.anim.note_translate_anim);
            notes_raw.setAnimation(note_translate_anim);
        }
    }
}
