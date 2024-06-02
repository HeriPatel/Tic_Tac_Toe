package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;

    private ImageView imgDice;
    private int[] dice1 = new int[]{R.drawable.r_one, R.drawable.r_two, R.drawable.r_three, R.drawable.r_four, R.drawable.r_five, R.drawable.r_six};
    private int[] dice2 = new int[]{R.drawable.g_one, R.drawable.g_two, R.drawable.g_three, R.drawable.g_four, R.drawable.g_five, R.drawable.g_six};
    private int roundCount;
    final int[] diceNumber = new int[1];
    private int player1points, player2points;
    private TextView textViewplyr1, textViewplyr2;
    CountDownTimer rollTimer = null;
    private TextView txt1, txt2;
    private ImageView diceBtn;
    private Button buttonreset;
    String args1, args2;
    private LinearLayout plr1, plr2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        textViewplyr1 = findViewById(R.id.text_view_p1);
        textViewplyr2 = findViewById(R.id.text_view_p2);
        imgDice = findViewById(R.id.diceImg);
        txt1 = findViewById(R.id.edittxt_p1);
        txt2 = findViewById(R.id.edittxt_p2);
        args1 = getIntent().getStringExtra("arg1");
        args2 = getIntent().getStringExtra("arg2");
        diceBtn = findViewById(R.id.diceImg);
        plr1 = findViewById(R.id.plr1ID);
        plr2 = findViewById(R.id.plr2ID);

        if (!args1.equals("") && !args2.equals("")) {
            txt1.setText(args1);
            txt2.setText(args2);
        }

        //for dice
        Toast toast = Toast.makeText(this, "Roll a dice!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        buttonreset = findViewById(R.id.button_reset);
        buttonreset.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    public void rolldice(View view) {
        buttonreset.setEnabled(false);
        final Random ranNumber = new Random();
        rollTimer = new CountDownTimer(1500, 50) {
            @Override
            public void onTick(final long millisUntilFinished) {

                //DISABLE ROLL BUTTON UNTIL COUNTDOWN TIMER EXPIRES
                diceBtn.setEnabled(false);
                diceNumber[0] = ranNumber.nextInt(6) + 1;
                if (player1Turn) {
                    imgDice.setImageResource(dice1[diceNumber[0] - 1]);
                } else {
                    imgDice.setImageResource(dice2[diceNumber[0] - 1]);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFinish() {
                if (diceNumber[0] % 2 != 0) {
//                if (diceNumber[0] >= 0) {
                    if (player1Turn) {
                        plr1.setBackground(getDrawable(R.drawable.border_plr_one));
                        plr2.setBackground(getDrawable(R.drawable.plr_t));
                        diceBtn.setImageDrawable(getDrawable(dice2[diceNumber[0] - 1]));
                    } else {
                        plr2.setBackground(getDrawable(R.drawable.border_plr_two));
                        plr1.setBackground(getDrawable(R.drawable.plr_o));
                        diceBtn.setImageDrawable(getDrawable(dice1[diceNumber[0] - 1]));
                    }
                }
                chooseTurn(diceNumber[0], player1Turn);
                buttonreset.setEnabled(true);

            }

        }.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void chooseTurn(int diceNumber, boolean b) {
        Log.d("tag", "num 1 : " + diceNumber);
        if (diceNumber % 2 == 0) {
//        if (diceNumber >= 0) {
            startplay();
        } else {
            diceBtn.setEnabled(true);
            player1Turn = !player1Turn;
//            player1Turn = true;
        }
    }

    private void startplay() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setEnabled(true);
                buttons[i][j].setOnClickListener(this);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) view).setText("X");
            ((Button) view).setTextColor(Color.rgb(255, 0, 0));
            plr1.setBackground(getDrawable(R.drawable.border_plr_one));
            plr2.setBackground(getDrawable(R.drawable.plr_t));
            diceBtn.setImageDrawable(getDrawable(dice2[diceNumber[0] - 1]));
            disableBtn();
        } else {
            ((Button) view).setText("O");
            ((Button) view).setTextColor(Color.rgb(0, 255, 0));
            plr2.setBackground(getDrawable(R.drawable.border_plr_two));
            plr1.setBackground(getDrawable(R.drawable.plr_o));
            diceBtn.setImageDrawable(getDrawable(dice1[diceNumber[0] - 1]));
            disableBtn();
        }

        roundCount++;

        if (checkforwin()) {
            if (player1Turn) {
                player1wins();
            } else {
                //Toast.makeText(this,"P2 wins",Toast.LENGTH_SHORT).show();
                player2wins();
                player1Turn = !player1Turn;
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            //Toast.makeText(this,"P1 change..........",Toast.LENGTH_SHORT).show();
            player1Turn = !player1Turn;
        }
        diceBtn.setEnabled(true);
    }

    private void disableBtn() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setEnabled(false);
            }
        }
    }


    private boolean checkforwin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void player1wins() {
        player1points++;
        Toast toast = Toast.makeText(this, txt1.getText().toString().concat(" Wins!"), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        updatePointsText();
        resetBoard();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void player2wins() {
        player2points++;
        Toast toast = Toast.makeText(this, txt2.getText().toString().concat(" Wins!"), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        updatePointsText();
        resetBoard();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewplyr1.setText(String.valueOf(player1points));
        textViewplyr2.setText(String.valueOf(player2points));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
        diceBtn.setImageDrawable(getDrawable(dice1[0]));
        diceBtn.setEnabled(true);
        disableBtn();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void resetGame() {
        player1points = 0;
        player2points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundcount", roundCount);
        outState.putInt("player1Points", player1points);
        outState.putInt("player2Points", player2points);
        outState.putBoolean("player1Turn", player1Turn);
        outState.putString("arg1", args1);
        outState.putString("arg2", args2);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundcount");
        player1points = savedInstanceState.getInt("player1points");
        player2points = savedInstanceState.getInt("player2points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
        args1 = savedInstanceState.getString("arg1");
        args2 = savedInstanceState.getString("arg2");
    }
}