package com.itktechnologies.connect3;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private int activePlayer = 0;
    private HashMap playerTable = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropIn (View v) throws InterruptedException {

        ImageView counter = (ImageView) v;
        Drawable d = counter.getDrawable();

        if ( d == null  ) {
            counter.setTranslationY(-1000f);

            if ( activePlayer == 0 ) {
                counter.setImageResource(R.drawable.yellow);
                playerTable.put (Integer.parseInt(counter.getTag().toString()), "Yellow");

                activePlayer = 1;

            } else {
                counter.setImageResource(R.drawable.red);
                playerTable.put (Integer.parseInt(counter.getTag().toString()), "Red");

                activePlayer = 0;
            }
            counter.animate().translationY(0f).rotation(360).setDuration(300);
        }


         String winner = getWinner();

         if ( winner != null ){
             Toast.makeText(this,  winner +  " won the game!", Toast.LENGTH_SHORT).show();
             restartTable();

         }

    }

    private String getWinner() {
        String retFunc = null;
        HashSet mapCompare = new HashSet();
        boolean hasNull = false;

        int [][] sequence = new int[][] {
                {1,2,3},
                {4,5,6},
                {7,8,9},
                {1,4,7},
                {2,5,8},
                {3,6,9},
                {1,5,9},
                {3,5,7}
        };

        for (int i = 0; i < sequence.length; i++) {
            mapCompare.clear();

            for ( int y = 0; y < sequence[i].length; y++) {
                if ( playerTable.get(sequence[i][y]) == null ) {
                    hasNull = true;
                    mapCompare.clear();
                    break;

                } else {
                    mapCompare.add(playerTable.get(sequence[i][y]));
                }
            }

            if ( mapCompare.size() == 1 ) {
                return playerTable.get(sequence[i][0]).toString();
            }
        }

        if ( hasNull == false ) {
            retFunc = "nobody";
        }

        return retFunc;
    }

    public void restartTable() {
        String imgID;
        int resourceID;
        ImageView imageResource;



        for (int i = 1; i< 10; i++ ) {
            imgID = "img".concat(Integer.toString(i));
            resourceID = getResources().getIdentifier(imgID, "id", getPackageName());
            imageResource = (ImageView) findViewById(resourceID);
            imageResource.setImageDrawable(null);
        }

        playerTable.clear();

    }


}
