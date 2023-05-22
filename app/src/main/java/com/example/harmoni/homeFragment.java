package com.example.harmoni;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.harmoni.helpers.Song_RecyclerViewAdapter;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.protocol.client.Subscription;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class homeFragment extends Fragment {
    private static final String CLIENT_ID = "b8f0cac7dd104537901f8d0fed64da41";
    private static final String REDIRECT_URI = "http://localhost:9000/callback";
    private SpotifyAppRemote mSpotifyAppRemote;

    private boolean mIsPlaying = false;
    private Button mPlayButton;

    private String currentSongURI;

    //JSON array declared here
    JSONArray songs = new JSONArray();
    JSONObject song1 = new JSONObject();
    JSONObject song= new JSONObject();




     private  SearchView searchView;
    ListView myListView;

    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        searchView= rootView.findViewById(R.id.searchView);
        mPlayButton = rootView.findViewById(R.id.play_button);


        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);

        try {

            song1.put("images", new JSONArray()
                    .put(new JSONObject("{\"height\": 640, \"url\": \"https://i.scdn.co/image/ab67616d0000b2731a84d71391df7469c5ab8539\", \"width\": 640}"))
                    .put(new JSONObject("{\"height\": 300, \"url\": \"https://i.scdn.co/image/ab67616d00001e021a84d71391df7469c5ab8539\", \"width\": 300}"))
                    .put(new JSONObject("{\"height\": 64, \"url\": \"https://i.scdn.co/image/ab67616d000048511a84d71391df7469c5ab8539\", \"width\": 64}")));

            song1.put("artist", "Pink Floyd");
            song1.put("album", "Wish You Were Here");
            song1.put("name", "Wish You Were Here");
            song1.put("uri", "spotify:track:6mFkJmJqdDVQ1REhVfGgd1");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {

            song.put("images", new JSONArray()
                    .put(new JSONObject("{\"height\": 640, \"url\": \"https://i.scdn.co/image/ab67616d0000b273ea7caaff71dea1051d49b2fe\", \"width\": 640}"))
                    .put(new JSONObject("{\"height\": 300, \"url\": \"https://i.scdn.co/image/ab67616d00001e02ea7caaff71dea1051d49b2fe\", \"width\": 300}"))
                    .put(new JSONObject("{\"height\": 64, \"url\": \"https://i.scdn.co/image/ab67616d00004851ea7caaff71dea1051d49b2fe\", \"width\": 64}")));

            song.put("artist", "Pink Floyd");
            song.put("album", "The Dark Side of the Moon");
            song.put("name", "The Great Gig in the Sky");
            song.put("uri", "spotify:track:2TjdnqlpwOjhijHCwHCP2d");

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        songs.put(song1);
        songs.put(song);


        Song_RecyclerViewAdapter adapter = new  Song_RecyclerViewAdapter(requireContext(),songs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        mPlayButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(mIsPlaying) pausePlayback();
                else startPlayback();
            }


        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(requireContext(), connectionParams,
                new Connector.ConnectionListener() {
                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("homeFragment", "Connected! Yay!");

                        // Subscribe to PlayerState
                        mSpotifyAppRemote.getPlayerApi().subscribeToPlayerState().setEventCallback(new Subscription.EventCallback<PlayerState>() {
                            @Override
                            public void onEvent(PlayerState playerState) {
                                Track track = playerState.track;
                                mIsPlaying = playerState.isPaused == false;
                                updateButton();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("homeFragment", throwable.getMessage(), throwable);
                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });

    }

    private void startPlayback() {
        if (mSpotifyAppRemote != null) {
            mSpotifyAppRemote.getPlayerApi().play(currentSongURI);
        }
    }

    private void pausePlayback() {
        if (mSpotifyAppRemote != null) {
            mSpotifyAppRemote.getPlayerApi().pause();
        }
    }

    private void updateButton() {
        if (mIsPlaying) {
            mPlayButton.setText("Pause Song");
        } else {
            mPlayButton.setText("Play Song");
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }
}
