package com.week2.cpt2mab.drawablebrush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by cpt2mab on 11/12/2014.
 */
public class PaperView extends View {

    private final float DEFAULT_BRUSH_SIZE = 50;
    private final int DEFAULT_ALPHA_VALUE = 120;

    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private float brushSize;
    private Drawable brushImage;
    private Bitmap brush;
    private Paint alphaSettingPaint;
    private Paint canvasPaint;

    public PaperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBrushImage(getResources().getDrawable(R.drawable.chrome));
        brushSize = DEFAULT_BRUSH_SIZE;
        setBrush();
        alphaSettingPaint = new Paint();
        alphaSettingPaint.setAlpha(DEFAULT_ALPHA_VALUE);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        Log.d("test", "onDraw called");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        setBrush();
        float touchX = event.getX() - brush.getWidth() / 2;
        float touchY = event.getY() - brush.getHeight() / 2;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                drawCanvas.drawBitmap(brush, touchX, touchY, alphaSettingPaint);
                break;
            default:
                return false;
        }
        return true;
    }

    public void setBrush() {
        brush = Bitmap.createScaledBitmap(((BitmapDrawable) brushImage).getBitmap(),
                (int) (1 + brushImage.getIntrinsicHeight() * brushSize / 50),
                (int) (1 + brushImage.getIntrinsicWidth() * brushSize / 50),
                false);
        invalidate();
    }

    public void setBrushSize(int newSize) {
        brushSize = newSize;
    }

    public void setBrushImage(Drawable brushImage) {
        this.brushImage = brushImage;
    }
}