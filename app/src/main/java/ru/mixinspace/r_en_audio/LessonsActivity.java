package ru.mixinspace.r_en_audio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LessonsActivity extends AppCompatActivity {
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        Bundle arguments = getIntent().getExtras();
        String grade = arguments.getString("grade");
        String part = arguments.getString("part");

        List<Audio> audioData = getAudioDataFromXml();

        createButtons(audioData);
    }

    private void createButtons(List<Audio> audioData) {
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        for (Audio audio : audioData) {
            TableRow tableRow = new TableRow(this);
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );

            layoutParams.setMargins(12, 12, 12, 12);

            Button button = new Button(this);
            button.setText(audio.getAudioName());
            button.setGravity(Gravity.CENTER);
            button.setPadding(48, 48, 48, 48);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            button.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.button_background_color)));
            button.setOnClickListener(view -> {
                // Add your button click logic here
            });

            tableRow.addView(button);
            tableLayout.addView(tableRow);
        }
    }

    private List<Audio> getAudioDataFromXml() {

        @SuppressLint("DiscouragedApi") int audioResourceID = getResources().getIdentifier("audio_list2_1", "array", getPackageName());
        String[] audios = getResources().getStringArray(audioResourceID);
        List<Audio> audioList = new ArrayList<>();

        for(String audio : audios){
            String[] audiocomponets = audio.split(";");
            audioList.add(new Audio(
                    audiocomponets[0],
                    audiocomponets[1],
                    audiocomponets[2],
                    audiocomponets[3])
            );
        }

        return audioList;
    }

    // Data class to represent audio information
    public static class Audio {
        private final String audioName;
        private final String pageNumber;
        private final String audioNumber;
        private final String audioLink;

        public Audio(String audioName, String pageNumber, String audioNumber, String audioLink) {
            this.audioName = audioName;
            this.pageNumber = pageNumber;
            this.audioNumber = audioNumber;
            this.audioLink = audioLink;
        }

        public String getAudioName() {
            return audioName;
        }

        public String getPageNumber() {
            return pageNumber;
        }

        public String getAudioNumber() {
            return audioNumber;
        }

        public String getAudioLink() {
            return audioLink;
        }
    }
}