package com.rd.android.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends Activity {

    ImageButton b[] = new ImageButton[9];
    ArrayList<Integer> remaining = new ArrayList<>(0);
    ArrayList<Integer> player1 = new ArrayList<>(0);
    ArrayList<Integer> player2 = new ArrayList<>(0);

    int c[][] = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {0, 3, 6},
            {1, 4, 7},
            {2, 5, 8},
            {0, 4, 8},
            {2, 4, 6},
    };

    GridLayout layout;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        layout = findViewById(R.id.grid);
        startButton = findViewById(R.id.start);
        layout.setVisibility(View.INVISIBLE);
        startButton.setVisibility(View.VISIBLE);


        b[0] = findViewById(R.id.image11);
        b[1] = findViewById(R.id.image12);
        b[2] = findViewById(R.id.image13);
        b[3] = findViewById(R.id.image21);
        b[4] = findViewById(R.id.image22);
        b[5] = findViewById(R.id.image23);
        b[6] = findViewById(R.id.image31);
        b[7] = findViewById(R.id.image32);
        b[8] = findViewById(R.id.image33);


    }

    public void click1(View view) {

        switch (view.getId()) {
            case R.id.image11:
                buttonClick(0);
                break;
            case R.id.image12:
                buttonClick(1);
                break;
            case R.id.image13:
                buttonClick(2);
                break;
            case R.id.image21:
                buttonClick(3);
                break;
            case R.id.image22:
                buttonClick(4);
                break;
            case R.id.image23:
                buttonClick(5);
                break;
            case R.id.image31:
                buttonClick(6);
                break;
            case R.id.image32:
                buttonClick(7);
                break;
            case R.id.image33:
                buttonClick(8);
                break;
        }

    }

    void buttonClick(final int i) {


        if (!remaining.isEmpty()) {
            if (remaining.contains(i)) {

                b[i].setImageResource(R.drawable.circle);
                player1.add(i);
                remaining.remove(Integer.valueOf(i));


                final int a = checkWin(player1, 1);
                if (a != 100) {
                    if (!remaining.isEmpty()) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                int r;

                                if (a == -1)
                                    r = getRandom();
                                else
                                    r = a;

                                b[r].setImageResource(R.drawable.cross);
                                player2.add(r);
                                remaining.remove(Integer.valueOf(r));

                                int x = checkWin(player2, 2);
                                Log.v("tag", "return " + x);


                            }
                        }, 500);
                    }

                }
            }

            if (remaining.isEmpty()) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "game over", Toast.LENGTH_SHORT).show();
                        startButton.setVisibility(View.VISIBLE);
                        layout.setVisibility(View.INVISIBLE);
                    }
                }, 1000);

            }
        }
    }

    public int pxToDp() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int side = width / 3;
        return Math.round(side / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public void start(View view) {
        int side = pxToDp();
        player1.clear();
        player2.clear();
        remaining.clear();

        layout.setAlpha(1);
        startButton.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.VISIBLE);

        for (int i = 0; i < 9; i++) {
            remaining.add(i);
            b[i].setImageBitmap(null);
            android.view.ViewGroup.LayoutParams params = b[i].getLayoutParams();
            params.height = side;
            params.width = side;

            b[i].setLayoutParams(params);
        }

    }

    int checkWin(ArrayList<Integer> player, int x) {

        Log.v("tag", "player " + x + " " + player.toString());

        for (int j = 0; j < 8; j++) {
            if (player.containsAll(Arrays.asList(c[j][0], c[j][1], c[j][2]))) {
                if (x == 1)
                    Toast.makeText(MainActivity.this, "player win", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Computer win", Toast.LENGTH_SHORT).show();


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.setAlpha(0.1f);
                        startButton.setVisibility(View.VISIBLE);
                    }
                }, 1000);

                return 100;
            }
        }

        for (int j = 0; j < 8; j++)
        {
            if (player.containsAll(Arrays.asList(c[j][0], c[j][1])) && remaining.contains(c[j][2])) {
                return c[j][2];
            } else if (player.containsAll(Arrays.asList(c[j][0], c[j][2])) && remaining.contains(c[j][1])) {
                return c[j][1];
            } else if (player.containsAll(Arrays.asList(c[j][1], c[j][2])) && remaining.contains(c[j][0])) {
                return c[j][0];
            }
        }
        return -1;
    }

    int getRandom() {
        Random r = new Random();
        return remaining.get(r.nextInt(remaining.size()));
    }

}
