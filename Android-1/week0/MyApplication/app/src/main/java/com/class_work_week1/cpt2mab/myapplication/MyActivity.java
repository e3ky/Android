package com.class_work_week1.cpt2mab.myapplication;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        LinearLayout layout = (LinearLayout) findViewById(R.id.main_layout);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.puzzle_pieces);

        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int x = point.x;
        int y = point.y;
      //  ArrayList<> arrayList = (ArrayList) typedArray;

       // Collections.shuffle((java.util.List<?>) typedArray);


        int puzzlePiecesCount = typedArray.length();


        int gridDimension = (int) Math.sqrt(puzzlePiecesCount);

        for(int currentRowIndex = 0; currentRowIndex < gridDimension; currentRowIndex++){
            LinearLayout currentRowLayout = new LinearLayout(this);
            currentRowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            currentRowLayout.setOrientation(LinearLayout.HORIZONTAL);

            for(int currentPieceInTheRowIndex = 0; currentPieceInTheRowIndex < gridDimension; currentPieceInTheRowIndex++){
                ImageView currentPiece = new ImageView(this);
                currentPiece.setLayoutParams(new ViewGroup.LayoutParams(50,50));
               // currentPiece.setMaxHeight(40);
               // currentPiece.setMaxWidth(40);
                Drawable dr = typedArray.getDrawable(currentRowIndex*gridDimension + currentPieceInTheRowIndex);


                currentPiece.setImageDrawable(dr);
                currentPiece.setAdjustViewBounds(false);
                currentRowLayout.addView(currentPiece);

            }

            layout.addView(currentRowLayout);
        }

      /*  for(int i = 0; i < 16; i++){
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.MATCH_PARENT));
            Drawable dr = typedArray.getDrawable(i);

           imageView.setImageDrawable(dr);
            imageView.setAdjustViewBounds(false);
            layout.addView(imageView);
            //layout.addView();
        }
*/

    }

}
