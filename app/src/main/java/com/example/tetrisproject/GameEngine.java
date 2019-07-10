package com.example.tetrisproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameEngine extends View implements View.OnClickListener
{
    //"inicjacja" obiektow
    private MediaPlayer mediaPlayer;
    private Board gameBoard;
    private MainActivity mainActivity;
    private ImageButton rotateButton;
    private ImageButton rightButton;
    private ImageButton downButton;
    private ImageButton leftButton;
    private  Timer timer = new Timer();
    private Random random = new Random();
    private  ArrayList<Piece> pieceList;
    private NextPieceWindow nextPieceWindow;
    private TextView currentLevelTextView;
    private TextView highscoreLevelTextView;
    private TextView currentScoreTextView;
    private Levels levels;
    private final int score=10;
    private int timerPeriod =250;   //okresla czas spadania bloczku w 10^-3 [s] na jedna komorke
    private int level=0;
    private boolean pause;
    static final public String REACHED_LEVEL = "reachedLevel";

    public GameEngine(Context context, NextPieceWindow nextPieceWindow, Board gameBoard)
    {
        super(context);

        //przypisanie elementow layoutu do wczesniej zainicjowanych obiektow

        this.mainActivity = (MainActivity) context;
        this.nextPieceWindow =nextPieceWindow;
        this.gameBoard =  gameBoard;
        pause = mainActivity.getPause();
        pieceList = gameBoard.getPieceList();
        mediaPlayer = mainActivity.getMediaPlayer();
        levels = new Levels(context);

        currentLevelTextView = mainActivity.getCurrentLevelTextView();
        highscoreLevelTextView = mainActivity.getHighscoreLevelTextView();
        currentScoreTextView = mainActivity.getPointTextView();

        currentLevelTextView.append("0");
        currentScoreTextView.append("0");
        highscoreLevelTextView.append(""+levels.loadHighscore());

        rotateButton = mainActivity.getRotateButton();
        rightButton = mainActivity.getRightButton();
        downButton = mainActivity.getDownButton();
        leftButton = mainActivity.getLeftButton();

        rotateButton .setOnClickListener(this);
        rightButton.setOnClickListener(this);
        downButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        gameLoop();
    }

    //glowna petla wykorzystujaca watek glowny
    public void gameLoop()
    {
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                mainActivity.runOnUiThread(new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        //jesli jestesmy w trakcie rozgrywki (nie ma pauzy ani konca gry)

                        if(gameOver()==false && mainActivity.getPause()==false )
                        {
                            gameBoard.moveDown(gameBoard.getCurrentPiece());

                            //jesli bloczek nie moze sie przesunac w dol
                            if (gameBoard.can_Move_Down(gameBoard.getCurrentPiece()) == false)
                            {
                                int deletedRows = gameBoard.clearRows();
                                gameBoard.clearRows();
                                pieceList.remove(gameBoard.getCurrentPiece());
                                pieceList.add(new Piece(random.nextInt(7) + 1));
                                nextPieceWindow.invalidate();

                                //przypisanie punktow za dana ilosc usunietych bloczkow
                                if (deletedRows> 0)
                                {
                                    levels.setCurrentPoints(levels.getCurrentPoints() + deletedRows * score);
                                    int p = levels.getCurrentPoints();
                                    levels.setLevel();

                                    currentScoreTextView.setText("Points: " + p);
                                    currentLevelTextView.setText("Level " + levels.getLevel());

                                    if (levels.getLevel() > levels.loadHighscore())
                                    {
                                        levels.saveHighscore();
                                        highscoreLevelTextView.setText("Highest level: "+ levels.getLevel());
                                    }
                                }

                                //jesli zdobyty poziom jest wyzszy od aktualnego to zwiekszamy level
                                // i zwiekszamy predkosc spadania bloczkow
                                if(levels.getLevel()>level)
                                {
                                    level++;
                                    timerPeriod = timerPeriod - (levels.getLevel()*20);
                                    timer.cancel();
                                    timer = new Timer();
                                    gameLoop();
                                }
                            }
                            invalidate();
                        }
                    }
                });
            }
        }, 0, timerPeriod);
    }

    //funkcja od zakonczenia rozgrywki
    public boolean gameOver()
    {
        //sprawdzam czy warunki zakonczenia gry sa spelnione i jesli tak to pokazuje ekran zakonczenia gry
        if( gameBoard.checkGameOver(gameBoard.getCurrentPiece())==true )
        {
            timer.cancel();
            pieceList.clear();
            gameBoard.clearGameBoard();
            mainActivity.setPause(true);
            mediaPlayer.stop();
            showGameOverScreen();
            return true;
        }
        return false;
    }

    //resetuje gre poprzez rozpoczecie glownej aktywnosci od poczatku
    public void resetGame()
    {
        timer.cancel();
        pieceList.clear();
        gameBoard.clearGameBoard();
        mainActivity.setPause(true);
        mediaPlayer.stop();
        invalidate();
        Intent intent = new Intent(this.getContext(), MainActivity.class);
        getContext().startActivity(intent);
    }

    //rozppczynam przejscie do drugiej aktywnosci za pomoca intentu
    // jesli warunki zakonczenia rozgrywki sa spelnione
    public void showGameOverScreen()
    {
        Intent intent = new Intent(this.getContext(), GameOver.class);
        intent.putExtra(REACHED_LEVEL, level);
        getContext().startActivity(intent);
    }

    //zwraca odpowiednia wielkosc i kolor klockow
    //(kazda kolumna ma szerokosc 30 pikseli, kolumn jest 16, a wiec wypelnia caly nasz layout o szer 480 px)
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint p = new Paint();

        for (int x = 0; x < gameBoard.getBoardHeight(); x++) {
            for (int y = 0; y < gameBoard.getBoardWidth(); y++) {

                int color  = gameBoard.codeToColor(x,y);
                p.setColor(color);
                canvas.drawRect(y*30, x*30, y*30+30, x*30+30,p);
            }
        }
    }


    //przypisanie funkcjonalnosci do odpowiednich przyciskow
    @Override
    public void onClick(View v)
    {
        if(mainActivity.getPause()==false)
        {

            switch(v.getId())
            {
                case R.id.rightButton:
                    gameBoard.moveRight(gameBoard.getCurrentPiece());
                    invalidate();
                    break;
                case R.id.downButton:
                    gameBoard.fastDrop(gameBoard.getCurrentPiece());
                    invalidate();
                    break;
                case R.id.leftButton:
                    gameBoard.moveLeft(gameBoard.getCurrentPiece());
                    invalidate();
                    break;
                case R.id.rotateButton:
                    gameBoard.rotatePiece(gameBoard.getCurrentPiece());
                    invalidate();
                    break;
            }
        }
    }

    //zwraca wartosc z timera
    public Timer getTimer()
    {
        return this.timer;
    }

}
