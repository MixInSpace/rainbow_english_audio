package ru.mixinspace.r_en_audio.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import ru.mixinspace.r_en_audio.R;

public class GradesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;

        LinearLayout gradesTable = findViewById(R.id.tableLayout);

        ViewGroup.LayoutParams gradesTableLayoutParams = gradesTable.getLayoutParams();

        gradesTableLayoutParams.height = (int) (screenWidth * 1.68);
        gradesTable.setLayoutParams(gradesTableLayoutParams);

        Button button11 = findViewById(R.id.grades_button11);
        ViewGroup.LayoutParams button11LayoutParams = button11.getLayoutParams();
        button11LayoutParams.width = screenWidth / 3;
        button11.setLayoutParams(button11LayoutParams);
    }

    public void onGradesButtonPress(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();
        Intent intent = new Intent(this, PartsActivity.class);
        intent.putExtra("grade", buttonText);
        startActivity(intent);
    }
}