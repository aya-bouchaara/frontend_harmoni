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
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;

public class Song_RecyclerViewAdapter extends RecyclerView.Adapter<Song_RecyclerViewAdapter.MyViewHolder> {


    private SongClickListener songClickListener;

    Context context;
    JSONArray songs;

    public Song_RecyclerViewAdapter(Context context, JSONArray songs, SongClickListener songClickListener){
        this.context=context;
        this.songs=songs;
        this.songClickListener = songClickListener;


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

                String imageUrl = songs.getJSONObject(position).getJSONArray("images").getJSONObject(1).getString("url");
                loadImageFromUrl(imageUrl, holder.imageView);

                String songURI = songs.getJSONObject(position).getString("uri");
                holder.play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (songClickListener != null) {
                            songClickListener.onSongClick(songURI);
                        }
                    }
                });

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
    private void loadImageFromUrl(String imageUrl, ImageView imageView) {
        // Use your preferred library (e.g., Picasso, Glide, Volley) to load the image from the URL
        // and set it to the ImageView.
        // Here's an example using Picasso:
        Picasso.get().load(imageUrl).into(imageView);
    }
}
