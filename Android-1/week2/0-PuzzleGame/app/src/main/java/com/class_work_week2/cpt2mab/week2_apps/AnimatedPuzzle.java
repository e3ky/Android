package com.class_work_week2.cpt2mab.week2_apps;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AnimatedPuzzle extends Activity {

    List<ImageView> currentStateOfImageViews;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadPuzzle();
    }

    private void loadPuzzle() {
        setContentView(R.layout.activity_my);

        final GridLayout layout = (GridLayout) findViewById(R.id.main_layout);

        currentStateOfImageViews = new ArrayList<ImageView>();

        TypedArray puzzlePiecesArray = getResources().obtainTypedArray(R.array.puzzle_pieces);

        //convert the piecess array to List of Drawables
        List<Drawable> drawablesList = new ArrayList<Drawable>();
        for (int j = 0; j < puzzlePiecesArray.length(); j++) {
            drawablesList.add(puzzlePiecesArray.getDrawable(j));
        }
        puzzlePiecesArray.recycle();

        //keep the ordered puzzle states into List of Drawables
        final List<Drawable> orderedPicture = new ArrayList<Drawable>(drawablesList);

        Collections.shuffle(drawablesList);

        //getting screen resolution
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int x = point.x;
        int y = point.y;

        //setting the Grid layout row/column size
        int puzzlePiecesCount = drawablesList.size();
        int gridDimension = (int) Math.sqrt(puzzlePiecesCount);
        layout.setRowCount(gridDimension);
        layout.setColumnCount(gridDimension);
        layout.setOrientation(GridLayout.HORIZONTAL);


        for (int gridPieceIndex = 0; gridPieceIndex < puzzlePiecesCount; gridPieceIndex++) {
            final ImageView newPieceOfPuzzle = new ImageView(this);
            newPieceOfPuzzle.setPadding(1,1,1,1);
            //newPieceOfPuzzle.get

            newPieceOfPuzzle.setLayoutParams(new LinearLayout.LayoutParams(x / gridDimension, LinearLayout.LayoutParams.WRAP_CONTENT));
            final Drawable newDrawablePiece = drawablesList.get(gridPieceIndex);

            newPieceOfPuzzle.setImageDrawable(newDrawablePiece);
            newPieceOfPuzzle.setAdjustViewBounds(true);

            currentStateOfImageViews.add(newPieceOfPuzzle);

            newPieceOfPuzzle.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    View.DragShadowBuilder builder = new View.DragShadowBuilder(view);
                    newPieceOfPuzzle.startDrag(null, builder, newPieceOfPuzzle, 0);
                    return false;
                }
            });

            newPieceOfPuzzle.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View view, DragEvent dragEvent) {
                    if (dragEvent.getAction() == DragEvent.ACTION_DROP) {

                        ImageView targetPieceOfPuzzle = (ImageView) dragEvent.getLocalState();

                        AnimatorSet animatorSet = swapPiecesAnimation(newPieceOfPuzzle, targetPieceOfPuzzle);
                        animatorSet.start();

                        ImageView tempView = (ImageView) dragEvent.getLocalState();
                        Drawable tempDrawable = tempView.getDrawable();
                        tempView.setImageDrawable(newPieceOfPuzzle.getDrawable());
                        newPieceOfPuzzle.setImageDrawable(tempDrawable);

                        List<Drawable> currentPuzzleState = new ArrayList<Drawable>();
                        for (int state = 0; state < currentStateOfImageViews.size(); state++) {
                            currentPuzzleState.add(currentStateOfImageViews.get(state).getDrawable());
                        }

                        if (currentPuzzleState.equals(orderedPicture)) {
                            Toast.makeText(AnimatedPuzzle.this, "Congratulations, Borisov", Toast.LENGTH_LONG).show();

                            for(ImageView imgView:currentStateOfImageViews){
                                imgView.setEnabled(false);
                            }
                        }
                    }

                    return true;
                }

                private AnimatorSet swapPiecesAnimation(ImageView newPieceOfPuzzle, ImageView targetPieceOfPuzzle) {

                    ObjectAnimator newPieceX = ObjectAnimator.ofFloat(newPieceOfPuzzle,"x",targetPieceOfPuzzle.getX(), newPieceOfPuzzle.getX());
                    ObjectAnimator newPieceY = ObjectAnimator.ofFloat(newPieceOfPuzzle,"y",targetPieceOfPuzzle.getY(), newPieceOfPuzzle.getY());
                    ObjectAnimator targetPieceX = ObjectAnimator.ofFloat(targetPieceOfPuzzle,"x",newPieceOfPuzzle.getX() , targetPieceOfPuzzle.getX());
                    ObjectAnimator targetPieceY = ObjectAnimator.ofFloat(targetPieceOfPuzzle,"y",newPieceOfPuzzle.getY() , targetPieceOfPuzzle.getY());

                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(newPieceX,newPieceY, targetPieceX, targetPieceY);
                    animatorSet.setDuration(1000);

                    return animatorSet;
                }
            });

            layout.addView(newPieceOfPuzzle);
        }

        final Button tryAgainButton = new Button(this);
        tryAgainButton.setText("New");
        layout.addView(tryAgainButton);
        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPuzzle();
            }
        });

        final Button gameEndButton = new Button(this);
        gameEndButton.setText("Exit");
        layout.addView(gameEndButton);
        gameEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}