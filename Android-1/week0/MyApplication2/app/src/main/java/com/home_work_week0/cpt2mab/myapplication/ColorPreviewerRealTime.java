package com.home_work_week0.cpt2mab.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ColorPreviewerRealTime extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_previewer_real_time);

        final Button showColorBtnBasic = (Button) findViewById(R.id.show_color_btn_real_time);
        final EditText colorEditText = (EditText) findViewById(R.id.color_edit_text_real_time);
        final TextView coloredField = (TextView) findViewById(R.id.colored_field_real_time);


        showColorBtnBasic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                String colorString = colorEditText.getText().toString();
                if (colorString.equals("") || colorEditText == null) {
                    Toast.makeText(ColorPreviewerRealTime.this, "Set a color hex in the text field", Toast.LENGTH_LONG).show();

                } else {
                    String colorHex = colorEditText.getText().toString();
                    try {
                        coloredField.setBackgroundColor((Color.parseColor(colorHex)));
                    } catch (IllegalArgumentException iae) {
                        Toast.makeText(ColorPreviewerRealTime.this, "The color hex is not correct", Toast.LENGTH_LONG).show();
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
                showColorBtnBasic.performClick();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        colorEditText.addTextChangedListener(textWatcher);
    }

}
