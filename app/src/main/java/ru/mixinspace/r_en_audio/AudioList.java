package ru.mixinspace.r_en_audio;

import java.net.URL;

public class AudioList {
    private String title;
    private boolean isPlaying;
    private URL audioLink;

    public AudioList(String title, URL audioLink, boolean isPlaying) {
        this.title = title;
        this.isPlaying = isPlaying;
        this.audioLink = audioLink;
    }

    public String getTitle() {
        return title;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public URL getAudioLink() {
        return audioLink;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }
}
