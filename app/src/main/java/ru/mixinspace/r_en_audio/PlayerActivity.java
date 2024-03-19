package ru.mixinspace.r_en_audio;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity implements AudioChangeListener{

    private final List<AudioList> audioLists = new ArrayList<>();
    private RecyclerView audioRecyclerView;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        View decodeView = getWindow().getDecorView();

        int options = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decodeView.setSystemUiVisibility(options);

        audioRecyclerView = findViewById(R.id.audio_recycler_view);
        final CardView playPauseCard = findViewById(R.id.play_pause_card);
        final ImageView playPauseImg = findViewById(R.id.play_pause_img);
        final ImageView prevBtn = findViewById(R.id.prev_button);
        final ImageView nextBtn = findViewById(R.id.next_button);

        audioRecyclerView.setHasFixedSize(true);
        audioRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mediaPlayer = new MediaPlayer();

        Bundle arguments = getIntent().getExtras();
        String grade = arguments.getString("grade");
        String part = arguments.getString("part");

        getAudioDataFromXML(grade, part);
    }

    private void getAudioDataFromXML(String grade, String part) {
        char partNum = part.charAt(part.length()-1);
        Log.println(Log.DEBUG, "msg", String.valueOf(partNum));
        int audioResourceID = getResources().getIdentifier("audio_list" + grade + "_" + partNum, "array", getPackageName());
        String[] audios = getResources().getStringArray(audioResourceID);

        for (String audio : audios) {
            String[] audiocomponets = audio.split(";");
            try {
                audioLists.add(new AudioList(audiocomponets[0], new URL(audiocomponets[1]), false));
            } catch (MalformedURLException e) {
                Log.println(Log.ERROR, "Error", "MalformedURLException" + audiocomponets[1]);
            }
        }

        audioRecyclerView.setAdapter(new AudioAdapter(audioLists, PlayerActivity.this));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus) {
            View decodeView = getWindow().getDecorView();

            int options = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decodeView.setSystemUiVisibility(options);
        }
    }

    @Override
    public void onChanged(int position) {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            mediaPlayer.reset();
        }
    }

    @Override
    protected void onStop() {
        mediaPlayer.stop();
        super.onStop();
    }
}