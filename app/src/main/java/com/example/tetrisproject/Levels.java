package com.example.tetrisproject;

import android.content.Context;
import android.content.SharedPreferences;

public class Levels
{
    private int currentPoints =0;
    private MainActivity mainActivity;
    private int level = 0;
    private int refLevel = 20;  //referencyjna wartosc sluzaca do ustalania liczby punktow
                                // od ktorej bedzie nastepny poziom

    public Levels(Context context)  //pobiera context z glownej aktywnosci
    {
        mainActivity = (MainActivity) context;
    }

    public void saveHighscore() //zapisywanie najwyzszego poziomu
    {
        SharedPreferences pref = mainActivity.getSharedPreferences("GAME",0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("HIGHSCORE", level);
        editor.commit();
    }

    public int loadHighscore()  //zaladowanie najwyzszego poziomu
    {
        SharedPreferences pref = mainActivity.getSharedPreferences("GAME",0);
        return pref.getInt("HIGHSCORE",0);
    }

    public void setCurrentPoints(int currentPoints) //ustawia aktualne punkty
    {
        this.currentPoints = currentPoints;
    }

    public int getCurrentPoints()      //zwraca aktualne punkty
    {
        return this.currentPoints;
    }

    public void setLevel()      //ustawia poziom na podstawie zdobytych punktow
    {
        if (currentPoints >= 20)
        {
            this.level = 1;
        }

        if (currentPoints >= 2*refLevel)
        {
            this.level++;
            refLevel=refLevel*2;
        }
    }

    public int getLevel()   //zwraca aktualny poziom
    {
        return this.level;
    }
}
