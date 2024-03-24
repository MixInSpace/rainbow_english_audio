package ru.mixinspace.r_en_audio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

import ru.mixinspace.r_en_audio.R;

public class PartsActivity extends AppCompatActivity {

    private String grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts);

        Bundle arguments = getIntent().getExtras();
        grade = arguments.getString("grade");

        String gradeName = "grade" + grade;
        int gradeResourceId = getResources().getIdentifier(gradeName, "array", getPackageName());


        if (gradeResourceId != 0) {
            String[] gradeArray = getResources().getStringArray(gradeResourceId);
            createButtons(gradeArray);
        }
    }

    private void createButtons(String[] gradeArray) {
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

            button.setBackgroundTintList(getColorStateList(R.color.button_background_color));
            button.setBackground(getDrawable(R.drawable.rounded_button_10));

            button.setOnClickListener(view -> {
                Intent intent = new Intent(PartsActivity.this, PlayerActivity.class);
                intent.putExtra("grade", grade);
                intent.putExtra("part", buttonText);
                startActivity(intent);
            });

            tableRow.addView(button);
            tableLayout.addView(tableRow);
        }
    }
}