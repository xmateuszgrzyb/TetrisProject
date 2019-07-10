package com.example.tetrisproject;

public class Piece
{
    public int colorCode;
    public int x1, y1;
    public int x2, y2;
    public int x3, y3;
    public int x4, y4;
    private Piece piece;


    public Piece(Piece piece)
    {
        this.piece = piece;
        this.x1 = piece.x1;
        this.x2 = piece.x2;
        this.x3 = piece.x3;
        this.x4 = piece.x4;
        this.y1 = piece.y1;
        this.y2 = piece.y2;
        this.y3 = piece.y3;
        this.y4 = piece.y4;
    }

    //funkcja stwarzajca klocek na podstawie colorCodu
    public Piece(int f)
    {
        switch (f)
        {
            case 1:                 // kwadrat
                x1 = 0; y1 = 7;     //pozycja jednej komorki bloczku na macierzy [30][16]
                x2 = 0; y2 = 8;
                x3 = 1; y3 = 7;
                x4 = 1; y4 = 8;

                colorCode = 1;
                break;

            case 2:                  // litera Z
                x1 = 0;y1 = 7;
                x2 = 0;y2 = 8;
                x3 = 1;y3 = 8;
                x4 = 1;y4 = 9;

                colorCode = 2;
                break;

            case 3:                   // litera I
                x1 = 0;y1 = 6;
                x2 = 0;y2 = 7;
                x3 = 0;y3 = 8;
                x4 = 0;y4 = 9;

                colorCode = 3;
                break;

            case 4:                    // litera T
                x1 = 0;y1 = 8;
                x2 = 1;y2 = 7;
                x3 = 1;y3 = 8;
                x4 = 1;y4 = 9;

                colorCode = 4;
                break;

            case 5:                     // litera S
                x1 = 0;y1 = 7;
                x2 = 0;y2 = 8;
                x3 = 1;y3 = 6;
                x4 = 1;y4 = 7;

                colorCode = 5;
                break;

            case 6:                     // litera J
                x1 = 0;y1 = 7;
                x2 = 0;y2 = 8;
                x3 = 0;y3 = 9;
                x4 = 1;y4 = 9;

                colorCode = 6;
                break;

            case 7:                     // litera L
                x1 = 0;y1 = 7;
                x2 = 0;y2 = 8;
                x3 = 0;y3 = 9;
                x4 = 1;y4 = 7;

                colorCode = 7;
                break;
        }
    }

    //funkcja przesuwajaca klocek w dane miejsce
    public void move(int x, int y)
    {
        x1 = x1 + x;
        y1 = y1 + y;
        x2 = x2 + x;
        y2 = y2 + y;
        x3 = x3 + x;
        y3 = y3 + y;
        x4 = x4 + x;
        y4 = y4 + y;
    }

    //funkcja odwracajaca klocek o 90 stopni w prawo
    public void turnPiece()
    {
        int tmp_x1, tmp_y1;
        int tmp_x2, tmp_y2;
        int tmp_x3, tmp_y3;

        tmp_x1 = turn_AroundX1(y2);
        tmp_y1 = turn_AroundY1(x2);
        x2 = tmp_x1;
        y2 = tmp_y1;

        tmp_x2 = turn_AroundX1(y3);
        tmp_y2 = turn_AroundY1(x3);
        x3 = tmp_x2;
        y3 = tmp_y2;

        tmp_x3 = turn_AroundX1(y4);
        tmp_y3 = turn_AroundY1(x4);
        x4 = tmp_x3;
        y4 = tmp_y3;
    }

    //funkcje do odwracania i obliczania wysokosci klocka
    public int turn_AroundX1(int y) {
        return x1 + y - y1;
    }

    public int turn_AroundY1(int x) {
        return y1 - x + x1;
    }

    public int getMinXCoordinate(int x1, int x2, int x3, int x4)
    {
        return Math.min(Math.min(x1,x2),Math.min(x3,x4));
    }
}