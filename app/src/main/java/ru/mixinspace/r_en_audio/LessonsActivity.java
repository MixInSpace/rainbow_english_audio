package ru.mixinspace.r_en_audio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

public class LessonsActivity extends AppCompatActivity {

    private String grade;
    private String part;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        Bundle arguments = getIntent().getExtras();
        grade = arguments.getString("grade");
        part = arguments.getString("part");

        String gradeName = "grade" + grade;
        int gradeResourceId = getResources().getIdentifier(gradeName, "array", getPackageName());

        Log.println(Log.DEBUG, "msg", part);

        List<Audio> audioData = getAudioDataFromXml();

        if (gradeResourceId != 0) {
            String[] gradeArray = getResources().getStringArray(gradeResourceId);
            createButtons(audioData);
        }
    }

    private void createButtons(List<Audio> audioData) {
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        for (Audio audio : audioData) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            ));

            Button button = new Button(this);
            button.setText(audio.getAudioName());
            button.setGravity(Gravity.CENTER);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Add your button click logic here
                }
            });

            tableRow.addView(button);
            tableLayout.addView(tableRow);
        }
    }

    private List<Audio> getAudioDataFromXml() {

        List<Audio> audioList = new ArrayList<>();

        try (XmlResourceParser parser = getResources().getXml(R.xml.audio_data)) {
            int eventType = parser.getEventType();

            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG && parser.getName().equals("audio")) {
                    String audioName = "";
                    String pageNumber = "";
                    String audioNumber = "";
                    String audioLink = "";

                    // Parse audio data
                    while (eventType != XmlResourceParser.END_TAG || !parser.getName().equals("audio")) {
                        if (eventType == XmlResourceParser.START_TAG) {
                            switch (parser.getName()) {
                                case "audio_name":
                                    audioName = parser.nextText();
                                    break;
                                case "page_number":
                                    pageNumber = parser.nextText();
                                    break;
                                case "audio_number":
                                    audioNumber = parser.nextText();
                                    break;
                                case "audio_link":
                                    audioLink = parser.nextText();
                                    break;
                            }
                        }
                        eventType = parser.next();
                    }

                    audioList.add(new Audio(audioName, pageNumber, audioNumber, audioLink));
                }

                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return audioList;

        return List.of(
                new Audio("Аудиозапись № 1 к заданию 1", "3", "1", "https://rosuchebnik.ru/kompleks_data/rainbow/audio/uchebnik3-1/audio/track_1.mp3"),
                new Audio("Аудиозапись № 2 к заданию 4", "5", "2", "https://rosuchebnik.ru/kompleks_data/rainbow/audio/uchebnik3-1/audio/track_2.mp3"),
                new Audio("Аудиозапись № 3 к заданию 7", "6", "3", "https://rosuchebnik.ru/kompleks_data/rainbow/audio/uchebnik3-1/audio/track_3.mp3")
        );
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
    }
}