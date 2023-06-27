package com.game.chessmate.GameFiles;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

/**
 * The View in which the Chessboard is embedded.
 */
public class BoardView extends ViewGroup {

    private ChessBoard board;
    private Thread thread;
    private RenderThread runnable;

    /**
     * Instantiates a new Board view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        this.setOnTouchListener(boardClickListener);
        board = ChessBoard.getInstance();
        board.initChessBoard(this, width);

        runnable = new RenderThread();
        thread = new Thread(runnable);
    }

    /**
     * @param boardClickListener catch onTouchEvents on the Chessboard.
     */
    private View.OnTouchListener boardClickListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
               board.handleFieldClick(event);
            }
            return true;
        }
    };


    /**
     * onLayout needs to call viewChild.layout() on every child of the ViewGroup to set their size
     *
     * @param changed
     * @param l left coordinates
     * @param t top coordinates
     * @param r right coordinates
     * @param b bottom coordinates
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < this.getChildCount(); i++) {
            this.getChildAt(i).layout(l,t,r,b);
        }

        runnable.setRunning(true);
        thread.start();
    }
}