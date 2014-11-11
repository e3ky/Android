package com.week1.cpt2mab.swipeandzoom;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class SwipeAndZoom extends Activity {

    private GestureDetector gestureDetector;
    private int gallerySize;
    int currentImageIndex = 1;
    private TextView currentImageIndexView;
    private ImageView imageFromGallery;
    private TypedArray galleryArray;
    private static final int SWIPE_MIN_DISTANCE = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        galleryArray = getResources().obtainTypedArray(R.array.gallery);
        gallerySize = galleryArray.length();

        imageFromGallery = (ImageView) findViewById(R.id.gallery_imgage_view);
        currentImageIndexView = (TextView) findViewById(R.id.image_index_view);

        updateImageIndexLabel();

        gestureDetector = new GestureDetector(this,new GestureListener());
        imageFromGallery.setImageDrawable(galleryArray.getDrawable(0));

        imageFromGallery.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

    }

    private class GestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("test", "---->DoubleTapped");
            if (imageFromGallery.getScaleType() != ImageView.ScaleType.CENTER_CROP) {
                imageFromGallery.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageFromGallery.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if ((e2.getX() < e1.getX()) && (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE)) {
                Log.d("test", "---->Fling left");
                if (currentImageIndex < gallerySize) {
                    currentImageIndex++;
                    imageFromGallery.setImageDrawable(galleryArray.getDrawable(currentImageIndex - 1));
                    updateImageIndexLabel();
                }
            } else if ((e2.getX() > e1.getX()) && (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE)){
                Log.d("test", "---->Fling right");
                if (currentImageIndex > 1) {
                    currentImageIndex--;
                    imageFromGallery.setImageDrawable(galleryArray.getDrawable(currentImageIndex - 1));
                    updateImageIndexLabel();
                }
            }
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

    }

    public void updateImageIndexLabel() {
        currentImageIndexView.setText(currentImageIndex + "/" + gallerySize);
    }
}
