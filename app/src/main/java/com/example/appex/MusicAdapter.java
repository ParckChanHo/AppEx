package com.example.appex;

import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {
    ArrayList<Music> list;
    public MusicAdapter(ArrayList<Music> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item,parent,false);
        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, int position) {
        Music item = list.get(position);
        holder.title.setText(item.title);
        holder.img.setImageResource(item.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MusicViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        ImageView img;
        public MusicViewHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_tx);
            img = (ImageView) itemView.findViewById(R.id.item_img);
        }
    }
}
