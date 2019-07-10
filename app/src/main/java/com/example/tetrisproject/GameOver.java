package com.example.tetrisproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.graphics.Paint;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameOver extends AppCompatActivity implements View.OnTouchListener
{
    //"inicjacja" obiektow
    private GameOverView gameOverView;
    private RelativeLayout layout;
    private TextView reachedLevel;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_activity);
        layout = (RelativeLayout) findViewById(R.id.game_over_layout);
        gameOverView = new GameOverView(this);
        layout.setOnTouchListener(this);
        layout.addView(gameOverView);
        reachedLevel = (TextView) findViewById(R.id.reachedLevel);
        //reachedLevel.setText("Your Level: " + getIntent().getIntExtra(GameEngine.REACHED_LEVEL,0));
    }

    //po dotknieciu na ekran konca gry wracamy do aktywnosci z rozgrywka
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int action = event.getAction();
        if(action==MotionEvent.ACTION_DOWN)
        {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
            return true;
        }
        return false;
    }

    //klasa wyswietlajaca nam napisy konca gry
    class GameOverView extends View
    {
        public GameOverView(Context context)
        {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas)
        {
            super.onDraw(canvas);
            Paint p = new Paint();
            p.setColor(Color.BLACK);
            p.setTextSize(200);
            canvas.drawText("Game Over",50,400,p);
            p.setTextSize(75);
            canvas.drawText("Touch!!!",450,800,p);
        }
    }
}

