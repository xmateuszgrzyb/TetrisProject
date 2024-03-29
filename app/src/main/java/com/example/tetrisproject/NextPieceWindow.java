package com.example.tetrisproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import java.util.ArrayList;

public class NextPieceWindow extends View
{
    private Board gameBoard;
    private ArrayList<Piece> pieceList;

    //zwracania "bitmapy" konkretnego obrazu
    private Bitmap squarePiece = BitmapFactory.decodeResource(getResources(), R.drawable.square);
    private Bitmap tPiece = BitmapFactory.decodeResource(getResources(), R.drawable.tpiece);
    private Bitmap zPiece = BitmapFactory.decodeResource(getResources(), R.drawable.zpiece);
    private Bitmap sPiece = BitmapFactory.decodeResource(getResources(), R.drawable.spiece);
    private Bitmap jPiece = BitmapFactory.decodeResource(getResources(), R.drawable.jpiece);
    private Bitmap lPiece = BitmapFactory.decodeResource(getResources(), R.drawable.lpiece);
    private Bitmap iPiece = BitmapFactory.decodeResource(getResources(), R.drawable.ipiece);

    public NextPieceWindow(Context context, Board gameBoard)
    {
        super(context);
        this.gameBoard = gameBoard;
        pieceList = gameBoard.getPieceList();
    }

    //rysowanie bloczkow w zaleznosci od przydzielonego im koloru
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint p = new Paint();

        if (pieceList.size() > 0)
        {
            Piece s = gameBoard.getNextPiece();

            if (s.colorCode == 1) {
                canvas.drawBitmap(squarePiece, 0, 0, p);
            }

            if (s.colorCode == 2) {
                canvas.drawBitmap(zPiece, 0, 0, p);
            }

            if (s.colorCode == 3) {
                canvas.drawBitmap(iPiece, 0, 0, p);
            }

            if (s.colorCode == 4) {
                canvas.drawBitmap(tPiece, 0, 0, p);
            }

            if (s.colorCode == 5) {
                canvas.drawBitmap(sPiece, 0, 0, p);
            }

            if (s.colorCode == 6) {
                canvas.drawBitmap(jPiece, 0, 0, p);
            }

            if (s.colorCode == 7) {
                canvas.drawBitmap(lPiece, 0, 0, p);
            }

        }
    }
}
