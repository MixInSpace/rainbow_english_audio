package ru.mixinspace.r_en_audio.audioPlayer;

public interface AudioChangeListener {

    void onChanged(int position);
    boolean isPreparing();
}
