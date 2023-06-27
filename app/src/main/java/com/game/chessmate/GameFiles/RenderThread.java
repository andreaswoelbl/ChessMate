package com.game.chessmate.GameFiles;

import android.util.Log;
import android.view.View;

import com.game.chessmate.GameFiles.PlayingPieces.ChessPiece;

import static android.content.ContentValues.TAG;

/**
 * The type Render thread.
 */
public class RenderThread implements Runnable {

    private ChessBoard board;
    private boolean running;
    private int targetFPS = 30;
    private double averageFPS;

    /**
     * Instantiates a new Render thread.
     *
     */
    public RenderThread() {
        this.board = ChessBoard.getInstance();
    }

    /**
     * Creates frames on the canvas by invalidating the view @averageFPS. The thread will do its task and then sleep for @waitTime to
     * ensure steady FPS.
     */
    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        double frameCount = 0;
        long targetTime = 1000 / targetFPS;

        while(running) {
            startTime = System.nanoTime();

            Field[][] fields = this.board.getBoardFields();
            for (Field[] rowFields : fields) {
                for (Field f : rowFields) {
                    if (f.getUpdate()) {
                        f.setUpdate(false);
                        f.invalidate();
                    }
                    ChessPiece piece = f.getCurrentPiece();
                    if (piece != null && piece.getUpdateView()) {
                        piece.setUpdateView(false);

                        if (piece.updateMovementOffset()) {
                            piece.updateOffsets();
                        }

                        View pieceView = (View)piece;
                        pieceView.invalidate();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;
            if(waitTime < 0){
                waitTime = 0;
            }
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if (frameCount == targetFPS) {
                averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                Log.d(TAG, "run: FPS: "  + averageFPS);
            }
        }
    }

    /**
     * Sets running.
     *
     * @param running determines if the thread is running
     */
    public void setRunning(boolean running) {
        this.running = running;
    }
}
