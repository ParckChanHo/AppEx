package com.example.appex;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Recommendation extends YouTubeBaseActivity {
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener listener;
    Button button;
    private static String API_KEY = "AIzaSyBn3TUlTV4c2th535c64DybRp6QXGIoz58";
    private static String videoId = "hl-ii7W4ITg";
    private static final String TAG = "Recommendation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        button = (Button) findViewById(R.id.youtubeBtn);
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youTubePlayerView);
        listener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("NmkYHmiNArc");
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                youTubePlayerView.initialize("AIzaSyBn3TUlTV4c2th535c64DybRp6QXGIoz58",listener);
            }
        });

/*
        //youtube
        initPlayer();

        ybtn = (Button)findViewById(R.id.youtubeBtn);
        ybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo();
            }
        });*/
    }
/*
    //유튜브 플레이어 메서드
    private void initPlayer() {
        playerView = findViewById(R.id.youTubePlayerView);
        playerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                player = youTubePlayer;
                player.setPlayerStateChangeListener(new YouTubePlayer.PlayerStateChangeListener() {
                    @Override
                    public void onLoading() {

                    }

                    @Override
                    public void onLoaded(String s) {
                        Log.d(TAG, "onLoaded: "+s);
                        player.play();
                    }

                    @Override
                    public void onAdStarted() {

                    }

                    @Override
                    public void onVideoStarted() {

                    }

                    @Override
                    public void onVideoEnded() {

                    }

                    @Override
                    public void onError(YouTubePlayer.ErrorReason errorReason) {
                        Log.d(TAG, "onError: "+errorReason);
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    private void playVideo() {
        if (player != null){
            if (player.isPlaying()){
                player.pause();
            }
            player.cueVideo(videoId);
        }
    }*/
}
