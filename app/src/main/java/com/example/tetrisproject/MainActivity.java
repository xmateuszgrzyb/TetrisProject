package com.example.tetrisproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    //"inicjacja" obiektow

    private Button buttonStart;
    private Button buttonReset;
    private ImageButton rotateButton;
    private ImageButton rightButton;
    private ImageButton downButton;
    private ImageButton leftButton;

    private TextView pointTextView;
    private TextView highscoreLevelTextView;
    private TextView currentLevelTextView;
    private GameEngine tetris;
    private NextPieceWindow nextPieceWindow;
    private boolean pause = true;
    private MediaPlayer mediaPlayer;
    private int stopMediaplayer;
    private Board gameBoard = new Board();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new MediaPlayer();    //ustawienie mediaplayera pod sciezke dzwiekowa
        try
        {
            mediaPlayer.setDataSource( this, Uri.parse("android.resource://com.example.tetrisproject/raw/tetris_sound"));
            mediaPlayer.prepare();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //przypisanie elementow layoutu do wczesniej zainicjowanych obiektow

        buttonStart = (Button) findViewById(R.id.buttonstart);
        buttonReset = (Button) findViewById(R.id.buttonreset);
        rotateButton = (ImageButton) findViewById(R.id.rotateButton);
        rightButton = (ImageButton) findViewById(R.id.rightButton);
        downButton = (ImageButton) findViewById(R.id.downButton);
        leftButton = (ImageButton) findViewById(R.id.leftButton);
        pointTextView = (TextView) findViewById(R.id.textViewPoint);
        highscoreLevelTextView= (TextView) findViewById(R.id.textViewHighscore);
        currentLevelTextView = (TextView) findViewById(R.id.levelText);

        nextPieceWindow = new NextPieceWindow(this, gameBoard);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(300,300);
        nextPieceWindow.setLayoutParams(params1);
        RelativeLayout relativeNextPieceLayout = (RelativeLayout) findViewById(R.id.relativelayout1);
        nextPieceWindow.setBackgroundColor(Color.LTGRAY);
        relativeNextPieceLayout.addView(nextPieceWindow);

        tetris = new GameEngine(this,nextPieceWindow, gameBoard);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(480, 900);
        tetris.setLayoutParams(params);
        RelativeLayout relativeTetris = (RelativeLayout) findViewById(R.id.relativelayout);
        tetris.setBackgroundColor(Color.LTGRAY);
        relativeTetris.addView(tetris);

        buttonStart.setOnClickListener(new View.OnClickListener()
        {

            int tmp=0;
            @Override
            public void onClick(View v)
            {
                stopMediaplayer = mediaPlayer.getCurrentPosition();
                tmp++;

                //po kliknieciu na przycisk start, ten zmienia swoja funkcjonalnosc na pause

                if(buttonStart.getText().equals("Start"))
                {
                    buttonStart.setText("Pause");
                    pause = false;

                    if(tmp==1)
                    {
                        mediaPlayer.start();
                        mediaPlayer.setLooping(true);
                    } else if(tmp>1)
                    {
                        mediaPlayer.seekTo(stopMediaplayer);
                        mediaPlayer.start();
                    }
                }

                //po kliknieciu na przycisk pause ponownie pojawia sie napis start
                else if(buttonStart.getText().equals("Pause"))
                {
                    buttonStart.setText("Start");
                    pause = true;
                    mediaPlayer.pause();
                }
            }
        });

        //po kliknieciu na przycisk reset gra startuje od nowa

        buttonReset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tetris.resetGame();
            }
        });

    }
    //po restarcie mediaplayer startuje od nowa z melodią
    @Override
    public void onRestart()
    {
        super.onRestart();
        pause = false;
        mediaPlayer.seekTo(stopMediaplayer);
        mediaPlayer.start();
    }
    //podczas pauzy mediaplayer zapisuje pozycje w ktorej zatrzymala sie melodia
    @Override
    public void onPause()
    {
        super.onPause();
        pause = true;
        mediaPlayer.stop();
        mediaPlayer.pause();
        stopMediaplayer = mediaPlayer.getCurrentPosition();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        finish();
    }

    //funkcje pomocnicze dla szybkiego dostepu do danych obiektow

    public Button getResetButton() { return buttonReset;}
    public ImageButton getRightButton() { return rightButton;}
    public ImageButton getDownButton() { return downButton;}
    public ImageButton getLeftButton() { return leftButton;}
    public ImageButton getRotateButton() { return rotateButton; }

    public boolean getPause() {  return pause;}
    public void setPause(boolean pause) { this.pause=pause;}
    public TextView getHighscoreLevelTextView() { return highscoreLevelTextView; }
    public TextView getPointTextView() { return pointTextView; }
    public TextView getCurrentLevelTextView() { return currentLevelTextView;}
    public MediaPlayer getMediaPlayer() {  return mediaPlayer; }
}

