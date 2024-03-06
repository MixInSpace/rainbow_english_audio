package ru.mixinspace.r_en_audio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Arrays;

public class PartsActivity extends AppCompatActivity {

    private String grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts);


        Bundle arguments = getIntent().getExtras();
        grade = arguments.getString("grade");

        String gradeName = "grade" + grade;
        @SuppressLint("DiscouragedApi") int gradeResourceId = getResources().getIdentifier(gradeName, "array", getPackageName());



        if (gradeResourceId != 0) {
            String[] gradeArray = getResources().getStringArray(gradeResourceId);
            createButtons(gradeArray);
        }
    }

    private void createButtons(String[] gradeArray){
        TableLayout tableLayout = findViewById(R.id.partsTableLayout);
        // Iterate through the array and create buttons dynamically
        for (String buttonText : gradeArray) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.setGravity(Gravity.CENTER);

            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );

            layoutParams.setMargins(12, 12, 12, 12);

            Button button = new Button(this);
            button.setId(View.generateViewId());
            button.setText(buttonText);
            button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);

            button.setPadding(48, 48, 48, 48);

            button.setLayoutParams(layoutParams);

            button.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.button_background_color)));

            button.setOnClickListener(view -> {
                Intent intent = new Intent(PartsActivity.this, LessonsActivity.class);
                intent.putExtra("grade", grade);
                intent.putExtra("part", buttonText);
                startActivity(intent);
            });

            tableRow.addView(button);
            tableLayout.addView(tableRow);
        }
    }

}