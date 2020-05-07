package net.smallacademy.authenticatorapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Profile> profiles;

    public MyAdapter(Context c , ArrayList<Profile> p)
    {
        context = c;
        profiles = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.level.setText(profiles.get(position).getLevel());
        holder.no.setText(profiles.get(position).getuserId());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView level,no;
        ImageView profilePic;
        Button btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            level = (TextView) itemView.findViewById(R.id.level);
            no = (TextView) itemView.findViewById(R.id.no);
            btn = (Button) itemView.findViewById(R.id.checkdetail);
        }
        public void onClick(final int position)
        {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, position+" is clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
