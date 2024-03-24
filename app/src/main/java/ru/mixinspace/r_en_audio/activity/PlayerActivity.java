package ru.mixinspace.r_en_audio.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import ru.mixinspace.r_en_audio.R;
import ru.mixinspace.r_en_audio.audioPlayer.AudioAdapter;
import ru.mixinspace.r_en_audio.audioPlayer.AudioChangeListener;
import ru.mixinspace.r_en_audio.audioPlayer.AudioList;

public class PlayerActivity extends AppCompatActivity implements AudioChangeListener {

    private final List<AudioList> audioLists = new ArrayList<>();
    private RecyclerView audioRecyclerView;
    private MediaPlayer mediaPlayer;
    private TextView endTime, startTime;
    private boolean isPlaying = false;
    private boolean isPreparing = false;
    private boolean isPaused = false;
    private SeekBar playerSeekBar;
    private ImageView playPauseImg;
    private Timer timer;
    private int currentAudioPosition = 0;
    private AudioAdapter audioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        audioRecyclerView = findViewById(R.id.audio_recycler_view);
        final CardView playPauseCard = findViewById(R.id.play_pause_card);
        final ImageView prevBtn = findViewById(R.id.prev_button);
        final ImageView nextBtn = findViewById(R.id.next_button);
        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);
        playPauseImg = findViewById(R.id.play_pause_img);
        playerSeekBar = findViewById(R.id.player_seek_bar);

        audioRecyclerView.setHasFixedSize(true);
        audioRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mediaPlayer = new MediaPlayer();

        Bundle arguments = getIntent().getExtras();
        String grade = arguments.getString("grade");
        String part = arguments.getString("part");

        getAudioDataFromXML(grade, part);



        playPauseCard.setOnClickListener(view -> {
            if (isPreparing) return;
            if (isPlaying) {
                isPlaying = false;
                isPaused = true;
                mediaPlayer.pause();

                playPauseImg.setImageResource(R.drawable.play_icon);
            } else {
                isPlaying = true;
                isPaused = false;
                mediaPlayer.start();

                playPauseImg.setImageResource(R.drawable.pause_icon);
            }
        });

        nextBtn.setOnClickListener(view -> {
            if (isPreparing) return;
            int nextAudioPosition = currentAudioPosition + 1;
            if (nextAudioPosition>=audioLists.size()){
                nextAudioPosition = 0;
            }

            audioLists.get(currentAudioPosition).setPlaying(false);
            audioLists.get(nextAudioPosition).setPlaying(true);

            audioAdapter.updateList(audioLists);
            audioRecyclerView.scrollToPosition(nextAudioPosition);

            onChanged(nextAudioPosition);
        });

        prevBtn.setOnClickListener(view -> {
            if (isPreparing) return;
            int prevAudioPosition = currentAudioPosition - 1;
            if (prevAudioPosition < 0){
                prevAudioPosition = audioLists.size()-1;
            }

            audioLists.get(currentAudioPosition).setPlaying(false);
            audioLists.get(prevAudioPosition).setPlaying(true);

            audioAdapter.updateList(audioLists);
            audioRecyclerView.scrollToPosition(prevAudioPosition);

            onChanged(prevAudioPosition);
        });

        playerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int pos, boolean fromUser) {
                if (isPreparing) return;
                if (fromUser) {
                    if (isPlaying || isPaused) {
                        mediaPlayer.seekTo(pos);
                    } else {
                        mediaPlayer.seekTo(0);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void getAudioDataFromXML(String grade, String part) {
        char partNum = part.charAt(part.length()-1);
        Log.println(Log.DEBUG, "msg", String.valueOf(partNum));
        int audioResourceID = getResources().getIdentifier("audio_list" + grade + "_" + partNum, "array", getPackageName());
        String[] audios = getResources().getStringArray(audioResourceID);

        for (String audio : audios) {
            String[] audioComponents = audio.split(";");
            audioLists.add(new AudioList(audioComponents[0].trim(), audioComponents[1].trim(), false));
        }
        audioAdapter = new AudioAdapter(audioLists, PlayerActivity.this);
        audioRecyclerView.setAdapter(audioAdapter);
    }

    @Override
    public void onChanged(int position) {
        if (isPreparing) return;
        if (mediaPlayer.isPlaying() || isPaused){
            mediaPlayer.pause();
            mediaPlayer.reset();
        }

        currentAudioPosition = position;

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            String audioLink = audioLists.get(position).getAudioLink();
            mediaPlayer.setDataSource(audioLink);
            mediaPlayer.prepareAsync();
            isPreparing = true;
        } catch (IOException e) {
            Toast.makeText(PlayerActivity.this, "Unable to load this Audio", Toast.LENGTH_SHORT).show();
        }

        mediaPlayer.setOnPreparedListener(mediaPlayer -> {
            int getTotalDuration = mediaPlayer.getDuration();
            String duration = String.format(Locale.getDefault(),"%02d:%02dc",
                    TimeUnit.MILLISECONDS.toMinutes(getTotalDuration),
                    TimeUnit.MILLISECONDS.toSeconds(getTotalDuration) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(getTotalDuration)));
            Log.println(Log.DEBUG, "dur", duration);
            isPreparing = false;
            isPlaying = true;
            endTime.setText(duration);
            mediaPlayer.start();

            playerSeekBar.setMax(getTotalDuration);

            playPauseImg.setImageResource(R.drawable.pause_icon);
        });

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    final int getCurrentDuration = mediaPlayer.getCurrentPosition();
                    String duration = String.format(Locale.getDefault(),"%02d:%02dc",
                            TimeUnit.MILLISECONDS.toMinutes(getCurrentDuration),
                            TimeUnit.MILLISECONDS.toSeconds(getCurrentDuration) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(getCurrentDuration)));
                    playerSeekBar.setProgress(getCurrentDuration);
                    startTime.setText(duration);
                });
            }
        },500,500);

        mediaPlayer.setOnCompletionListener(mediaPlayer -> {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);

            isPlaying = false;
            isPaused = true;

            playPauseImg.setImageResource(R.drawable.play_icon);

            playerSeekBar.setProgress(0);
        });
    }


    @Override
    protected void onDestroy() {
        if (isPlaying || isPaused || mediaPlayer.isPlaying() || isPreparing) {
            mediaPlayer.reset();

            timer.purge();
            timer.cancel();
        }

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if (isPlaying || isPaused) mediaPlayer.pause();

        isPlaying = false;
        playPauseImg.setImageResource(R.drawable.play_icon);

        super.onStop();
    }

    @Override
    public boolean isPreparing() {
        return isPreparing;
    }
}