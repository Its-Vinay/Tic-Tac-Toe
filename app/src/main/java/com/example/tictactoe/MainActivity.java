package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Handler handler=new Handler();
    private TextView p1;
    private TextView p2;
    private Button reset;
    private int roundcount;
    private int p1_count;
    private int  p2_count;
    private boolean player1turn=true;
    Button[][] button=new Button[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p1=findViewById(R.id.p1);
        p2=findViewById(R.id.p2);
        reset=findViewById(R.id.reset);

        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                String buttonid="button_"+i+j;
                int resid=getResources().getIdentifier(buttonid,"id",getPackageName());
                button[i][j]=findViewById(resid);
                button[i][j].setOnClickListener(this);
            }
        }
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetgame();

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        if(player1turn)
        {
            ((Button)v).setText("X");
            ((Button)v).setTextSize(35);

        }else
        {
            ((Button)v).setText("O");
            ((Button)v).setTextAppearance(R.style.ButtonFont);
        }
        roundcount++;
        if(checkwin()) {
            if (player1turn) {
                player1wins();
            } else {
                player2wins();
            }
        }else
        if(roundcount==9)
        {
            draws();}
else {
    player1turn=!player1turn;}

    }
    private boolean checkwin(){
        String[][] field=new String[3][3];
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                field[i][j]=button[i][j].getText().toString();
            }
    }
        for(int i=0;i<3;i++)
        {
            if(field[i][0].equals(field[i][1])&&field[i][0].equals(field[i][2])&&!field[i][0].equals(""))
            {
                return true;
            }}
            for(int i=0;i<3;i++)
            {
                if(field[0][i].equals(field[1][i])&&field[0][i].equals(field[2][i])&&!field[0][i].equals(""))
                {
                    return true;

                }}
                if(field[0][0].equals(field[1][1])&&field[0][0].equals(field[2][2])&&!field[0][0].equals(""))
                {
                    return true;
                }
                if(field[0][2].equals(field[1][1])&&field[0][2].equals(field[2][0])&&!field[0][2].equals(""))
                {
                    return true;
                }
                return false;
            }
            private void player1wins(){
        p1_count++;
                Toast.makeText(this, "Player 1 wins", Toast.LENGTH_SHORT).show();
                updatescore();
                handler.postDelayed(runner,1000);




        }
        private  void player2wins(){
            p2_count++;
            Toast.makeText(this, "Player 2 wins", Toast.LENGTH_SHORT).show();
            updatescore();
            handler.postDelayed(runner,1000);

        }
        private  void draws(){
            Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
            handler.postDelayed(runner,1000);

        }
        private void updatescore(){
        p1.setText("Player1 : "+p1_count);
        p2.setText("Player2 : "+p2_count);
        }
        private Runnable runner=new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        button[i][j].setText("");
                    }

                }
                roundcount=0;
                player1turn=true;
            }
        };


        private void resetgame(){
        p1_count=0;
        p2_count=0;
        updatescore();
        handler.postDelayed(runner,10);
        }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundcount",roundcount);
        outState.putInt("p1_count",p1_count);
        outState.putInt("p2_count",p2_count);
        outState.putBoolean("player1turn",player1turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundcount=savedInstanceState.getInt("roundcount");
        p1_count=savedInstanceState.getInt("p1_count");
        p2_count=savedInstanceState.getInt("p2_count");
        player1turn=savedInstanceState.getBoolean("player1turn");
    }
}
