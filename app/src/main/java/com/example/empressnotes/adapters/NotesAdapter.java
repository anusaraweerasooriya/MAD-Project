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

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {
    private Context context;
    private ArrayList note_id, note_title, note_body;

    //constructor
    public NotesAdapter(Context context, ArrayList note_id, ArrayList note_title, ArrayList note_body) {
        this.context = context;
        this.note_id = note_id;
        this.note_title = note_title;
        this.note_body = note_body;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notes_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_note_id.setText(String.valueOf(note_id.get(position)));
        holder.txt_note_title.setText(String.valueOf(note_title.get(position)));
        holder.txt_note_body.setText(String.valueOf(note_body.get(position)));
    }

    @Override
    public int getItemCount() {
        return note_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txt_note_id, txt_note_title, txt_note_body;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_note_id = itemView.findViewById(R.id.txt_NoteID);
            txt_note_title = itemView.findViewById(R.id.txt_NoteTitle);
            txt_note_body = itemView.findViewById(R.id.txt_NoteNody);
        }
    }
}
