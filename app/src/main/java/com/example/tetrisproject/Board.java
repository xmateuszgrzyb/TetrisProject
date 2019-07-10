package com.example.tetrisproject;

import android.graphics.Color;
import android.graphics.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Board
{
    private final int boardHeight =30;
    private final int boardWidth=16;
    private int gameBoard[][]=new int[boardHeight][boardWidth];
    private final Random random = new Random();
    private ArrayList<Piece> pieceList = new ArrayList<Piece>();
    private  final int number_of_Pieces = 7;

    public Board()
    {
        pieceList.add(new Piece(random.nextInt(number_of_Pieces)+1));
        pieceList.add(new Piece(random.nextInt(number_of_Pieces)+1));
    }

    /*
    returns Color of x,y gameBoard cell
     */

    public int codeToColor(int x, int y) {

        if(gameBoard[x][y]==0) return Color.parseColor("#D3D3D3");  // Light Gray
        if(gameBoard[x][y]==1) return Color.parseColor("#00FF00");  // Square Green
        if(gameBoard[x][y]==2) return Color.parseColor("#FF00FF");  //  zpiece Magenta
        if(gameBoard[x][y]==3) return Color.parseColor("#0000FF");   // ipiece Blue
        if(gameBoard[x][y]==4) return Color.parseColor("#00FFFF");   // tpiece Cyan
        if(gameBoard[x][y]==5) return Color.parseColor("#ffbf00");   // spiece Orange
        if(gameBoard[x][y]==6) return Color.parseColor("#BEBEBE");   // jpiece gray
        if(gameBoard[x][y]==7) return Color.parseColor("#FF0000");  // lpiece Red

        return -1;
    }

    public void clearGameBoard()
    {
        for(int i=0; i<boardHeight; i++) {
            for(int j=0; j<boardWidth; j++) {
                gameBoard[i][j]= 0;
            }
        }
    }

    public  ArrayList<Piece> getPieceList()
    {
        return pieceList;
    }

    public Piece getCurrentPiece()
    {
        return pieceList.get(pieceList.size() - 2);
    }

    public Piece getNextPiece()
    {
        return pieceList.get(pieceList.size()-1);
    }

    private void placePiece(Piece currentPiece)
    {
        gameBoard[currentPiece.x1][currentPiece.y1] = currentPiece.colorCode;
        gameBoard[currentPiece.x2][currentPiece.y2] = currentPiece.colorCode;
        gameBoard[currentPiece.x3][currentPiece.y3] = currentPiece.colorCode;
        gameBoard[currentPiece.x4][currentPiece.y4] = currentPiece.colorCode;
    }

    private void deletePiece(Piece currentPiece)
    {
        gameBoard[currentPiece.x1][currentPiece.y1] = 0;
        gameBoard[currentPiece.x2][currentPiece.y2] = 0;
        gameBoard[currentPiece.x3][currentPiece.y3] = 0;
        gameBoard[currentPiece.x4][currentPiece.y4] = 0;
    }


    //sprawdzam czy aktualny bloczek moze sie poruszac i zwracam true jesli tak
    //(kopiuje bloczek, przesuwam go w dane miejsce (prawo/lewo/dol) i sprawdzam poprawnosc)

    private boolean piece_Can_Move(Piece currentPiece, int x, int y)
    {
        int tmp =0;

        Point p1 = new Point(currentPiece.x1, currentPiece.y1);
        Point p2 = new Point(currentPiece.x2, currentPiece.y2);
        Point p3 = new Point(currentPiece.x3, currentPiece.y3);
        Point p4 = new Point(currentPiece.x4, currentPiece.y4);

        Point tmp1 = new Point(currentPiece.x1+x, currentPiece.y1+y);
        Point tmp2 = new Point(currentPiece.x2+x, currentPiece.y2+y);
        Point tmp3 = new Point(currentPiece.x3+x, currentPiece.y3+y);
        Point tmp4 = new Point(currentPiece.x4+x, currentPiece.y4+y);

        ArrayList<Point> tmpPieceCoordinates = new ArrayList<Point>();

        tmpPieceCoordinates.add(tmp1);
        tmpPieceCoordinates.add(tmp2);
        tmpPieceCoordinates.add(tmp3);
        tmpPieceCoordinates.add(tmp4);

        //petla po rozmiarze tmpPieceCoordinates
        for(Point p : tmpPieceCoordinates )
        {
            if(p.x< boardHeight && p.y>=0 && p.y< boardWidth && gameBoard[p.x][p.y]==0)
            {
                tmp++;
            }

            else if(p.equals(p1) || p.equals(p2) || p.equals(p3) || p.equals(p4))
            {
                tmp++;
            }
        }

        if(tmp==4) //wszystkie 4 komorki danego klocka sa poprawnie ustawione
        {
            return true;
        }
        return false;
    }

    //na podobnej zasadzie jak wyzej sprawdzam czy bloczek moze sie obrocic

    private boolean piece_Can_Rotate(Piece currentPiece)
    {
        int tmp =0;
        ArrayList<Point> tmpPieceCoordinates = new ArrayList<Point>();

        Piece tmpPiece = new Piece(currentPiece);

        Point p1 = new Point(currentPiece.x1, currentPiece.y1);
        Point p2 = new Point(currentPiece.x2, currentPiece.y2);
        Point p3 = new Point(currentPiece.x3, currentPiece.y3);
        Point p4 = new Point(currentPiece.x4, currentPiece.y4);

        tmpPiece.turnPiece();

        Point tmp1 = new Point(tmpPiece.x1, tmpPiece.y1);
        Point tmp2 = new Point(tmpPiece.x2, tmpPiece.y2);
        Point tmp3 = new Point(tmpPiece.x3, tmpPiece.y3);
        Point tmp4 = new Point(tmpPiece.x4, tmpPiece.y4);

        tmpPieceCoordinates .add(tmp1);
        tmpPieceCoordinates .add(tmp2);
        tmpPieceCoordinates .add(tmp3);
        tmpPieceCoordinates .add(tmp4);

        for(Point p : tmpPieceCoordinates  ) {

            if(p.x< boardHeight && p.x>=0 && p.y>=0 && p.y< boardWidth && gameBoard[p.x][p.y]==0) {
                tmp++;
            }

            else if(p.equals(p1) || p.equals(p2) || p.equals(p3) || p.equals(p4)) {
                tmp++;
            }
        }

        if(tmp==4) //wszystkie 4 komorki danego klocka sa poprawnie ustawione
        {
            return true;
        }
        return false;
    }

    //sprawdzam czy bloczek moze poruszac sie w lewo, jesli tak to zwracam true
    private  boolean can_Move_Left(Piece currentPiece)
    {
        if(piece_Can_Move(currentPiece, 0, -1)==true)
        {
            return true;
        }
        return false;
    }

    //sprawdzam czy bloczek moze poruszac sie w prawo, jesli tak to zwracam true
    private boolean can_Move_Right(Piece currentPiece)
    {
        if(piece_Can_Move(currentPiece, 0,1) == true)
        {
            return true;
        }
        return false;
    }

    //sprawdzam czy bloczek moze poruszac sie w dol, jesli tak to zwracam true
    public boolean can_Move_Down(Piece currentPiece)
    {
        if(piece_Can_Move(currentPiece, 1,0)==true)
        {
            return true;
        }
        return false;
    }

    // funkcja sluzaca do przesuwania klocka w prawo, lewo lub w dol
    private void movePiece(Piece currentPiece, int x, int y)
    {
        deletePiece(currentPiece);
        currentPiece.move(x, y);
        placePiece(currentPiece);
    }

    //sprawdzam czy bloczek moze byc przesuniety w prawo, jesli true to wykonuje przesuniecie o jedna pozycje
    public void moveRight(Piece currentPiece)
    {
        if(can_Move_Right(currentPiece)==true)
        {
            movePiece(currentPiece, 0, 1);
        }
    }

    //sprawdzam czy bloczek moze byc przesuniety w lewo, jesli true to wykonuje przesuniecie o jedna pozycje
    public  void moveLeft(Piece currentPiece)
    {
        if(can_Move_Left(currentPiece)==true)
        {
            movePiece(currentPiece, 0, -1);
        }
    }

    //sprawdzam czy bloczek moze byc przesuniety w dol, jesli true to wykonuje przesuniecie o jedna pozycje
    public  void moveDown(Piece currentPiece)
    {
        if(can_Move_Down(currentPiece)==true)
        {
            movePiece(currentPiece, 1, 0);
        }
    }

    //sprawdzam czy bloczek moze byc przesuniety w dol, jesli true to wykonuje przesuniecie dopoki wartosc jest true
    public void fastDrop(Piece currentPiece)
    {
        deletePiece(currentPiece);

        while(can_Move_Down(currentPiece)==true)
        {
            moveDown(currentPiece);
        }
        placePiece(currentPiece);
    }

    //jesli bloczek moze sie obrocic to wykonujemy obrot o 90 stopni w prawo
    public void rotatePiece(Piece currentPiece)
    {
        //nie obracam bloczku o ksztalcie kwadratu (colorCode==1 to kwadrat)

        if(piece_Can_Rotate(currentPiece)==true && currentPiece.colorCode!=1)
        {
            deletePiece(currentPiece);
            currentPiece.turnPiece();
            placePiece(currentPiece);
        }
        placePiece(currentPiece);
    }

    //funkcja usuwajaca rzedy, ktore są całe wypelnione przez komorki klockow
    public int clearRows()
    {
        int deletedRowIndex;
        int deletedRows=0;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();

        for (int i = 0; i < boardHeight; i++)
        {
            for (int j = boardWidth - 1; j >= 0; j--)
            {
                if (gameBoard[i][j]==0) //rzad macierzy nie jest caly zapelniony, wiec wychodzimy z petli
                {
                    break;
                }
                if (j == 0)  //po przeszukaniu calego rzedu wiemy, ze poprzedni warunek nie wystapil,
                            // a wiec mamy zapelniony caly rzad
                {
                    deletedRowIndex = i;
                    arrayList.add(deletedRowIndex);
                    deletedRows++;
                    deleteRow(deletedRowIndex); //wypelnia zerami rzedy ktore zostaly zapelnione
                }
            }
        }

        if (deletedRows >= 1)
        {
            int highestRow = Collections.min(arrayList); // najwyzszy rzad ktory jest zapelniony
            int[][] gameBoardCopy = new int[highestRow][boardWidth];

            //kopiuje macierz do miejsca gdzie najwyzszy rzad jest zapelniony
            for (int i = 0; i < gameBoardCopy.length; i++) {
                for (int j = 0; j < gameBoardCopy[1].length; j++)
                {
                    gameBoardCopy[i][j] = gameBoard[i][j];
                }
            }

            //plansza zostaje odwzorowana bez rzedow, ktore zostaly wczesniej oznaczone jako zapelnione
            for (int i = 0; i < gameBoardCopy.length; i++) {
                for (int j = 0; j < gameBoardCopy[1].length; j++) {
                    gameBoard[i+deletedRows][j] = gameBoardCopy[i][j];
                }
            }
        }
        return deletedRows;
    }

    //wypelnia zerami rzedy, ktore zostaly oznaczone jako zapelnione w funkcji clearRows()
    public void deleteRow(int r)
    {
        for (int i = 0; i < boardWidth; i++)
        {
            gameBoard[r][i] =0;
        }
    }

    //sprawdzam czy bloczek moze sie poruszac w dol oraz czy bloczek zmiesci sie na planszy
    // (wysokosc kazdego klocka to minimum dwie komorki)
    public boolean checkGameOver(Piece piece)
    {
        if(can_Move_Down(piece) == false
                && piece.getMinXCoordinate(piece.x1, piece.x2, piece.x3, piece.x4)<=1)
        {
            return true;
        }
        return false;
    }

    //zwraca wysokosc planszy
    public  int getBoardHeight()
    {
        return this.boardHeight;
    }

    //zwraca szerokosc planszy
    public int getBoardWidth()
    {
        return this.boardWidth;
    }
}
