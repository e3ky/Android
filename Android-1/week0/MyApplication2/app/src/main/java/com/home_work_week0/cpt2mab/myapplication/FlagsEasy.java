package com.home_work_week0.cpt2mab.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class FlagsEasy extends Activity {
    int firstColorIndex = 5;
    int secondColorIndex = 12;
    int thirdColorIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flags_easy);

        final int [] colorsArray= getResources().getIntArray(R.array.my_color_array);
        final int colorsArrayLength = colorsArray.length;

        final View firstColor = (View) findViewById(R.id.first_color);
        final View secondColor = (View) findViewById(R.id.second_color);
        final View thirdColor = (View) findViewById(R.id.third_color);

        firstColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstColorIndex = (firstColorIndex + 1) % colorsArrayLength;
                firstColor.setBackgroundColor(colorsArray[firstColorIndex]);

            }
        });
        secondColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secondColorIndex = (secondColorIndex + 1) % colorsArrayLength;
                secondColor.setBackgroundColor(colorsArray[secondColorIndex]);

            }
        });
        thirdColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thirdColorIndex = (thirdColorIndex + 1) % colorsArrayLength;
                thirdColor.setBackgroundColor(colorsArray[thirdColorIndex]);

            }
        });


    }


}
