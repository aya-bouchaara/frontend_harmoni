package com.example.harmoni.helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.harmoni.R;


import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;

public class Song_RecyclerViewAdapter extends RecyclerView.Adapter<Song_RecyclerViewAdapter.MyViewHolder> {


    Context context;
    JSONArray songs;

    public Song_RecyclerViewAdapter(Context context, JSONArray songs){
        this.context=context;
        this.songs=songs;



    }

    @NonNull
    @Override
    public Song_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_song,parent,false);

        return new Song_RecyclerViewAdapter.MyViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull Song_RecyclerViewAdapter.MyViewHolder holder, int position) {



            try {
                holder.songName.setText(songs.getJSONObject(position).getString("name"));
                holder.artist.setText(songs.getJSONObject(position).getString("artist"));
                holder.album.setText(songs.getJSONObject(position).getString("album"));

            }catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

        @Override
    public int getItemCount() {

        return songs.length();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView songName,artist,album;
        Button play;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView10);
            songName= itemView.findViewById(R.id.songName);
            artist= itemView.findViewById(R.id.artist);
            album= itemView.findViewById(R.id.album);
            play= itemView.findViewById(R.id.play);
        }
    }
}
