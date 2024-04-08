package com.example.foodlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    ArrayList<FoodModel> data;

    CustomAdapter(Context context, ArrayList data) {
        this.context = context;
        this.data = data;

    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.priceShow.setText(String.valueOf(data.get(position).price));
        holder.nameTextView.setText(data.get(position).name);
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context,AddFoodActivity.class);
                intent.putExtra("id", String.valueOf(data.get(position).id));
                intent.putExtra("price", data.get(position).price);
                intent.putExtra("name", data.get(position).name);
                intent.putExtra("update", "Update");
                context.startActivity(intent);

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete Note")
                        .setMessage("Are you sure want to delete?")
                        .setIcon(R.drawable.baseline_delete_24)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                MyDatabaseHelper dbHelper = new MyDatabaseHelper(view.getContext());
                                dbHelper.deleteNote(data.get(holder.getAdapterPosition()).getId()); // Assuming id is the primary key of your contact
                                data.remove(holder.getAdapterPosition()); // Remove the item from your list
                                notifyItemRemoved(holder.getAdapterPosition());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  nameTextView,priceShow;
        ImageView update, delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            priceShow = itemView.findViewById(R.id.priceShow);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            update = itemView.findViewById(R.id.updateButton);
            delete = itemView.findViewById(R.id.deleteButton);
        }
    }
}
