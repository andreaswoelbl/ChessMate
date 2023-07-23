package com.game.chessmate.GameFiles;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.game.chessmate.R;

/**
 * Resource Loader.
 */
public class ResourceLoader extends AsyncTask<Void, Void, Void> {

    private static Bitmap pawnWhite;
    private static Bitmap bishopWhite;
    private static Bitmap rookWhite;
    private static Bitmap knightWhite;
    private static Bitmap queenWhite;
    private static Bitmap kingWhite;
    private static Bitmap pawnBlack;
    private static Bitmap bishopBlack;
    private static Bitmap rookBlack;
    private static Bitmap knightBlack;
    private static Bitmap queenBlack;
    private static Bitmap kingBlack;
    private static Resources resources;
    private static int fieldSize;

    private final Runnable onResourcesLoaded;

    /**
     * Instantiates a new Resource loader.
     *
     * @param resources         the resources
     * @param screenWidth       the screen width
     * @param onResourcesLoaded
     */
    public ResourceLoader(Resources resources, int screenWidth, Runnable onResourcesLoaded) {
        ResourceLoader.resources = resources;
        this.onResourcesLoaded = onResourcesLoaded;
        ChessBoard board = ChessBoard.getInstance();
        fieldSize = board.calculateRectSize(screenWidth);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        loadBitmapFromResources();
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        this.onResourcesLoaded.run();
    }

    /**
     * Loads the sprites of the chess pieces into bitmaps.
     */
    private static void loadBitmapFromResources() {
        pawnWhite = loadBitmap(R.drawable.pawn_player1);
        bishopWhite = loadBitmap(R.drawable.bishop_player1);
        rookWhite = loadBitmap(R.drawable.rook_player1);
        knightWhite = loadBitmap(R.drawable.knight_player1);
        queenWhite = loadBitmap(R.drawable.queen_player1);
        kingWhite = loadBitmap(R.drawable.king_player1);

        pawnBlack = loadBitmap(R.drawable.pawn_player2);
        bishopBlack = loadBitmap(R.drawable.bishop_player2);
        rookBlack = loadBitmap(R.drawable.rook_player2);
        knightBlack = loadBitmap(R.drawable.knight_player2);
        queenBlack = loadBitmap(R.drawable.queen_player2);
        kingBlack = loadBitmap(R.drawable.king_player2);
    }

    /**
     * Extracts the bitmap of this ChessPiece from resources
     *
     * @param drawableId the drawable id
     * @return the bitmap
     */
    public static Bitmap loadBitmap(int drawableId){
        Bitmap bitmap = decodeFromResources(drawableId);
        return scaleBitmapToFieldSize(bitmap);
    }

    /**
     * Scales the bitmap of this PlayingPiece to the size of the rectangle container.
     */
    private static Bitmap scaleBitmapToFieldSize(Bitmap bitmap) {
        return Bitmap.createScaledBitmap(bitmap, fieldSize, fieldSize, false);
    }

    /**
     * Decodes a bitmap from resources with @param drawableId and returns the bitmap.
     * @param drawableId
     * @return
     */
    private static Bitmap decodeFromResources(int drawableId) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeResource(resources, drawableId);
        if (bitmap == null) {
            throw new RuntimeException("Could not decode bitmap from resources. DrawableId: " + drawableId );
        }
        return bitmap;
    }

    /**
     * Gets pawn player 1.
     *
     * @return the pawn player 1
     */
    public static Bitmap getPawnWhite() {
        return pawnWhite;
    }

    /**
     * Gets bishop player 1.
     *
     * @return the bishop player 1
     */
    public static Bitmap getBishopWhite() {
        return bishopWhite;
    }

    /**
     * Gets rook player 1.
     *
     * @return the rook player 1
     */
    public static Bitmap getRookWhite() {
        return rookWhite;
    }

    /**
     * Gets knight player 1.
     *
     * @return the knight player 1
     */
    public static Bitmap getKnightWhite() {
        return knightWhite;
    }

    /**
     * Gets queen player 1.
     *
     * @return the queen player 1
     */
    public static Bitmap getQueenWhite() {
        return queenWhite;
    }

    /**
     * Gets king player 1.
     *
     * @return the king player 1
     */
    public static Bitmap getKingWhite() {
        return kingWhite;
    }

    /**
     * Gets pawn player 2.
     *
     * @return the pawn player 2
     */
    public static Bitmap getPawnBlack() {
        return pawnBlack;
    }

    /**
     * Gets bishop player 2.
     *
     * @return the bishop player 2
     */
    public static Bitmap getBishopBlack() {
        return bishopBlack;
    }

    /**
     * Gets rook player 2.
     *
     * @return the rook player 2
     */
    public static Bitmap getRookBlack() {
        return rookBlack;
    }

    /**
     * Gets knight player 2.
     *
     * @return the knight player 2
     */
    public static Bitmap getKnightBlack() {
        return knightBlack;
    }

    /**
     * Gets queen player 2.
     *
     * @return the queen player 2
     */
    public static Bitmap getQueenBlack() {
        return queenBlack;
    }

    /**
     * Gets king player 2.
     *
     * @return the king player 2
     */
    public static Bitmap getKingBlack() {
        return kingBlack;
    }
}
