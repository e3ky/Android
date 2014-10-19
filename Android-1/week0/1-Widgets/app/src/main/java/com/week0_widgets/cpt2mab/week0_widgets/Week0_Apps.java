package com.week0_widgets.cpt2mab.week0_widgets;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Week0_Apps extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

       // loadColorPreviewerRealTimeApp(colorEditText, coloredField);


        Button showColorPreviewerBasicButton = (Button) findViewById(R.id.color_previewer_basic_button);
        Button showColorBtnBasic = (Button) findViewById(R.id.show_color_btn_basic);
        final EditText colorEditText = (EditText) findViewById(R.id.color_edit_text_basic);
        final TextView coloredField = (TextView) findViewById(R.id.colored_field_basic);

        showColorPreviewerBasicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.color_previewer_basic);
            }
        });

        showColorBtnBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String colorString = colorEditText.getText().toString();
                if (colorString.equals("") || colorEditText == null) {
                    Toast.makeText(Week0_Apps.this, "Set a color hex in the text field", Toast.LENGTH_LONG).show();

                } else {
                    String colorHex = colorEditText.getText().toString();
                    try {
                        coloredField.setBackgroundColor((Color.parseColor(colorHex)));
                    } catch (IllegalArgumentException iae) {
                        Toast.makeText(Week0_Apps.this, "The color hex is not correct", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void loadColorPreviewerRealTimeApp() {
        final Button showColorPreviewerRealTimeButton = (Button) findViewById(R.id.color_previewer_real_time_button);
        Button showColorBtnRealTime = (Button) findViewById(R.id.show_color_btn_real_time);
        final EditText colorEditText = (EditText) findViewById(R.id.color_edit_text_real_time);
        final TextView coloredField = (TextView) findViewById(R.id.colored_field_real_time);

        showColorBtnRealTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.color_previewer_real_time);
            }
        });

        showColorPreviewerRealTimeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                String colorString = colorEditText.getText().toString();
                if (colorString.equals("") || colorEditText == null) {
                    Toast.makeText(Week0_Apps.this, "Set a color hex in the text field", Toast.LENGTH_LONG).show();

                } else {
                    String colorHex = colorEditText.getText().toString();
                    try {
                        coloredField.setBackgroundColor((Color.parseColor(colorHex)));
                    } catch (IllegalArgumentException iae) {
                        Toast.makeText(Week0_Apps.this, "The color hex is not correct", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                showColorPreviewerRealTimeButton.performClick();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        colorEditText.addTextChangedListener(textWatcher);
    }


}
