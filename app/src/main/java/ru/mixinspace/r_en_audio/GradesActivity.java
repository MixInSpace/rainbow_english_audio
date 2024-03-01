package ru.mixinspace.r_en_audio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GradesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
    }

    public void onGradesButtonPress(View v) {
        Button b = (Button)v;
        String b_text = b.getText().toString();
        Intent intent = new Intent(this, PartsActivity.class);
        intent.putExtra("grade", b_text);
        startActivity(intent);
    }
}