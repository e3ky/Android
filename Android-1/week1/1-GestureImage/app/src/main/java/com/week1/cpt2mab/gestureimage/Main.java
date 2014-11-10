package com.week1.cpt2mab.gestureimage;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
//import android.view.GestureDetector.OnDoubleTapListener;
import android.widget.Toast;


public class Main extends Activity implements OnTouchListener {

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
