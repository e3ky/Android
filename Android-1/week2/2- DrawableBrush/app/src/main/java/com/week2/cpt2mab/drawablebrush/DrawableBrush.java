package com.week2.cpt2mab.drawablebrush;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class DrawableBrush extends Activity {

    private ImageButton currentBrush;
    private TextView brushSizeView;
    private SeekBar seekBar;
    private PaperView paper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable_brush);

        brushSizeView = (TextView) findViewById(R.id.brush_size);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);

        currentBrush = (ImageButton) findViewById(R.id.chrome_button);
        currentBrush.setBackgroundDrawable(getResources().getDrawable(R.drawable.clicked));

        seekBar.setOnSeekBarChangeListener(new BrushSizeSeekBar());

        paper = (PaperView) findViewById(R.id.paper_view);
        paper.setBrushSize(seekBar.getProgress() + 1);
    }

    public void clicked(View view) {
        if (view != currentBrush) {
            ImageButton tempImageButton = (ImageButton) view;
            tempImageButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.clicked));
            currentBrush.setBackgroundDrawable(getResources().getDrawable(R.drawable.not_clicked));
            currentBrush = tempImageButton;

            paper.setBrushImage(currentBrush.getDrawable());
        }
    }

    private class BrushSizeSeekBar implements SeekBar.OnSeekBarChangeListener {

        public BrushSizeSeekBar() {
            //super(context);
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            paper.setBrushSize(seekBar.getProgress() + 1);
            brushSizeView.setText("" + (seekBar.getProgress() + 1));

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }


}
