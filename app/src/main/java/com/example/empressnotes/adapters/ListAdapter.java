package com.example.empressnotes.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.empressnotes.activities.ListUpdate;
import com.example.empressnotes.activities.MySubList;
import com.example.empressnotes.activities.SubListAdd;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyListViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList list_id, list_title, list_description;

    Animation translate_anim;

    //DiaryAdapter constructor
    public ListAdapter(Context context, ArrayList list_id, ArrayList list_title, ArrayList list_description) {
        this.activity = activity;
        this.context = context;
        this.list_id = list_id;
        this.list_title = list_title;
        this.list_description = list_description;
    }


    @NonNull
    @Override
    public MyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_sub_list_row, parent, false);
        return new MyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyListViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.text_list_id.setText(String.valueOf(list_id.get(position)));
        holder.getText_list_title.setText(String.valueOf(list_title.get(position)));
        holder.text_list_description.setText(String.valueOf(list_description.get(position)));

        DatabaseHelper dbRef = new DatabaseHelper(context);
        Cursor noOfItems = dbRef.getTotalItems(String.valueOf(list_id.get(position)));
        noOfItems.moveToFirst();
        int test = noOfItems.getInt(0);

        holder.text_list_quantity.setText("Total Items: "+String.valueOf(test));

        holder.layoutListsMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, MySubList.class);
                intent.putExtra("id",String.valueOf(list_id.get(position)));
//                intent.putExtra("title",String.valueOf(list_title.get(position)));
//                intent.putExtra("description",String.valueOf(list_description.get(position)));
                context.startActivities(new Intent[]{intent});

            }




        });
        holder.layoutListsMain.setOnLongClickListener(new View.OnLongClickListener() {


            @Override
            public boolean onLongClick(View view) {
                Intent intent = new Intent(context, ListUpdate.class);
                intent.putExtra("id",String.valueOf(list_id.get(position)));
                intent.putExtra("title",String.valueOf(list_title.get(position)));
                intent.putExtra("description",String.valueOf(list_description.get(position)));
                context.startActivities(new Intent[]{intent});
                return false;
            }
        });




    }

    @Override
    public int getItemCount() {
        return list_id.size();
    }


    public class MyListViewHolder extends RecyclerView.ViewHolder {

        TextView text_list_id, getText_list_title, text_list_description,text_list_quantity;
        LinearLayout layoutListsMain;

        public MyListViewHolder(@NonNull View itemView) {
            super(itemView);
            text_list_id = itemView.findViewById(R.id.list_id_txt);
            getText_list_title = itemView.findViewById(R.id.list_title_txt);
            text_list_description = itemView.findViewById(R.id.list_description_txt);
            layoutListsMain = itemView.findViewById(R.id.layoutListsMain);
            text_list_quantity = itemView.findViewById(R.id.noOfItems);
            ///Animation
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            layoutListsMain.setAnimation(translate_anim);

        }
    }
}
