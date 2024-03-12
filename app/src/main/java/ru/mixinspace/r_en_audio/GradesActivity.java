package ru.mixinspace.r_en_audio;

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
        Button b = (Button) v;
        String b_text = b.getText().toString();
        Intent intent = new Intent(this, PartsActivity.class);
        intent.putExtra("grade", b_text);
        startActivity(intent);
    }
}