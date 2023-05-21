package com.example.harmoni;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;
import com.spotify.protocol.client.Subscription;


public class homeFragment extends Fragment {
    private static final String CLIENT_ID = "b8f0cac7dd104537901f8d0fed64da41";
    private static final String REDIRECT_URI = "http://localhost:9000/callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    private Button mPlayButton;
    private boolean mIsPlaying = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mPlayButton = rootView.findViewById(R.id.play_button);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsPlaying) {
                    pausePlayback();
                } else {
                    startPlayback();
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Set the connection parameters
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
                                Log.d("homeFragment", track.name + " by " + track.artist.name);
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
            mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");
        }
    }

    private void pausePlayback() {
        if (mSpotifyAppRemote != null) {
            mSpotifyAppRemote.getPlayerApi().pause();
        }
    }

    private void updateButton() {
        if (mIsPlaying) {
            mPlayButton.setText("Pause Playlist");
        } else {
            mPlayButton.setText("Play Playlist");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }
}
