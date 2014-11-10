package com.week2.cpt2mab.gestureimage;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
//import android.view.GestureDetector.OnDoubleTapListener;

import com.week2.cpt2mab.gestureimage.R;

import java.util.ArrayList;
import java.util.List;


public class KeyFrameAnimator extends Activity implements OnTouchListener {

    private final String DEBUG_TAG = "DEBUG_TAG";
    private RelativeLayout mainLayout;
    private ImageView imageView;

    private PointF pointer1;
    private PointF pointer2;
    private PointF oldPointer1;
    private PointF oldPointer2;

    private float oldDistance = 0;
    private float distance;

    private boolean oneFingerModeOn = false;
    private boolean twoFingersModeOn = false;

    private int clickCounter = 0;
    private long startTime;
    private long durationBetweenTaps = 0;

    List<Animator> frameSet = new ArrayList<Animator>();
    private AnimatorSet mainAnimatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialization();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int action = event.getActionMasked();
        int pointersCount = event.getPointerCount();

        setFingersMode(pointersCount);

       /* if (!isPointerIntoImageViewBoundaries(pointersCount,event)){
            return true;
        }*/

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                pointer1 = new PointF();
                pointer2 = new PointF();
                oldPointer1 = new PointF();
                oldPointer2 = new PointF();
                //Log.d(DEBUG_TAG, "Action was DOWN single");
                break;
            case (MotionEvent.ACTION_MOVE):
                if (oneFingerModeOn) {
                    Log.d(DEBUG_TAG, "Action was MOVE with 1 finger");
                    pointer1.set(event.getX(0), event.getY(0));
                    if (oldPointer1.x != 0) {
                        imageView.setTranslationX(imageView.getTranslationX() + (pointer1.x - oldPointer1.x));
                        imageView.setTranslationY(imageView.getTranslationY() + (pointer1.y - oldPointer1.y));
                    }
                    oldPointer1.set(pointer1);
                    oldDistance = 0;
                    break;
                } else if (twoFingersModeOn) {
                    Log.d(DEBUG_TAG, "Action was MOVE with 2 fingers");
                    pointer1.set(event.getX(0), event.getY(0));
                    pointer2.set(event.getX(1), event.getY(1));
                    if (oldPointer2.x != 0 && oldPointer1.x != 0) {
                        //rotate section
                        float rotationAngle = (imageView.getRotation() + angleBetween2Lines(pointer1, pointer2, oldPointer1, oldPointer2));
                        imageView.setRotation(rotationAngle);

                        //scale section
                        distance = getDistanceBetweenTwoPoints(pointer1, pointer2);
                        if (oldDistance != 0) {
                            imageView.setScaleX(imageView.getScaleX() * (distance / oldDistance));
                            imageView.setScaleY(imageView.getScaleY() * (distance / oldDistance));
                        }
                        oldDistance = distance;
                    }
                    oldPointer1.set(pointer1);
                    oldPointer2.set(pointer2);
                }
                break;

            case (MotionEvent.ACTION_UP):
                //Double TAP functionality
                clickCounter++;
                if (clickCounter == 1) {
                    startTime = System.currentTimeMillis();
                } else if (clickCounter == 2) {
                    durationBetweenTaps = System.currentTimeMillis() - startTime;
                    if (durationBetweenTaps < 300) {
                        //Toast.makeText(this,"DOUBLE TAP",Toast.LENGTH_LONG).show();
                        clickCounter = 0;
                        durationBetweenTaps = 0;
                        initialization();
                    } else {
                        clickCounter = 1;
                        startTime = System.currentTimeMillis();
                    }
                }
                // Log.d(DEBUG_TAG, "Action was UP");
                break;
            case (MotionEvent.ACTION_CANCEL):
                //Log.d(DEBUG_TAG, "Action was CANCEL");
                break;
            case (MotionEvent.ACTION_OUTSIDE):
                // Log.d(DEBUG_TAG, "Movement occurred outside bounds " + "of current screen element");
                break;
            default:
                return super.onTouchEvent(event);
        }
        return true;
    }

    private boolean isPointerIntoImageViewBoundaries(int pointersNumber, MotionEvent event) {
        Rect imageViewBoundaries = new Rect();
        imageViewBoundaries.set(imageView.getLeft(), imageView.getTop(), imageView.getRight(), imageView.getBottom());
        for (int i = 0; i < pointersNumber; i++) {
            if (!imageViewBoundaries.contains((int) event.getX(i), (int) event.getY(i))) {
                return false;
            }
        }
        return true;
    }

    private void setFingersMode(int pointersCount) {
        if (pointersCount == 1) {
            //  Toast.makeText(this,"one finger touch",Toast.LENGTH_LONG).show();
            oneFingerModeOn = true;
            twoFingersModeOn = false;
        } else if (pointersCount == 2) {
            //   Toast.makeText(this,"two finger touch",Toast.LENGTH_LONG).show();
            twoFingersModeOn = true;
            oneFingerModeOn = false;
        } else if (pointersCount == 3) {
            // Toast.makeText(this,"three finger touch",Toast.LENGTH_LONG).show();
            oneFingerModeOn = false;
            twoFingersModeOn = false;
        }
    }

    private void initialization() {
        setContentView(R.layout.activity_main);
        mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        imageView = (ImageView) findViewById(R.id.image);
        mainLayout.setOnTouchListener(this);

        Button saveStateButton = (Button) findViewById(R.id.save_state_button);
        Button playAnimationButton = (Button) findViewById(R.id.play_animation_button);
        final TextView instruction = (TextView) findViewById(R.id.instruction_text_view);
        instruction.setVisibility(View.INVISIBLE);

        saveStateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PropertyValuesHolder rotateImage = PropertyValuesHolder.ofFloat("rotation", imageView.getRotation());
                PropertyValuesHolder scaleXImage = PropertyValuesHolder.ofFloat("scaleX", imageView.getScaleX());
                PropertyValuesHolder scaleYImage = PropertyValuesHolder.ofFloat("scaleY", imageView.getScaleY());
                PropertyValuesHolder translationXImage = PropertyValuesHolder.ofFloat("translationX", imageView.getTranslationX());
                PropertyValuesHolder translationYImage = PropertyValuesHolder.ofFloat("translationY", imageView.getTranslationY());
                frameSet.add(ObjectAnimator.ofPropertyValuesHolder(imageView, rotateImage, scaleXImage, scaleYImage, translationXImage, translationYImage));
            }
        });

        playAnimationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainAnimatorSet = new AnimatorSet();
                mainAnimatorSet.playSequentially(new ArrayList<Animator>(frameSet));
                instruction.setVisibility(View.VISIBLE);
                mainAnimatorSet.start();
            }
        });
    }

    public static float angleBetween2Lines(PointF startPoint, PointF endPoint, PointF startPoint2, PointF endPoint2) {
        float angle1 = (float) Math.toDegrees(Math.atan2(startPoint.y - endPoint.y, startPoint.x - endPoint.x));
        float angle2 = (float) Math.toDegrees(Math.atan2(startPoint2.y - endPoint2.y, startPoint2.x - endPoint2.x));

        return angle1 - angle2;
    }

    public static float getDistanceBetweenTwoPoints(PointF point1, PointF point2) {
        return (float) Math.sqrt((point2.x - point1.x) * (point2.x - point1.x) + (point2.y - point1.y) * (point2.y - point1.y));
    }

}
