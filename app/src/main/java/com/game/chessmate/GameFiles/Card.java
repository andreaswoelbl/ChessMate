package com.game.chessmate.GameFiles;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.game.chessmate.GameFiles.PlayingPieces.ChessPiece;
import com.game.chessmate.GameFiles.PlayingPieces.ChessPieceType;
import com.game.chessmate.R;

public class Card {
    private String name;
    private String desc;
    private String useCase;
    private boolean continuingEffectUntilEnd=false;
    private boolean continuingEffectUntilCaptured=false;
    private boolean owned;
    private int id;
    private int drawableId;

    private Bitmap sprite;

    public Card (int number) throws IllegalArgumentException{
        id=number;
        switch (number){
            case 0:
                name="Cowardice";
                desc="Move one of your opponent's pawns one or two squares backward. It may not enter or cross an occupied square.";
                useCase="[i] Play this card immediately after your move";
                drawableId=R.drawable.cowardice;
                break;
            case 1:
                name="Crusade";
                desc="Play this card when you move a bishop without capturing a piece. This bishop immediately moves one more time.";
                useCase="[i] Play this card after your bishop's first move.";
                drawableId=R.drawable.crusade;
                break;
            case 2:
                name="Dark Mirror";
                desc="On this move, one of your pawns can capture by moving diagonally backward instead of forward.";
                useCase="[i] Play this card on your turn, instead of making a regular move.";
                drawableId=R.drawable.dark_mirror;
                break;

            case 3:
                name="Death Dance";
                desc="Exchange the position of any of your pieces with any adjacent enemy piece.";
                useCase="[i] Play this card on your turn, instead of making a regular move.";
                drawableId=R.drawable.death_dance;
                break;

            case 4:
                name="Disintegration";
                desc="Remove one of your own pawns from the chessboard, and set it aside. It is now dead, and cannot be brought back into play with another card.";
                useCase="[i] Play this card immediately before or after your move.";
                drawableId=R.drawable.disintegration;
                break;

            case 5:
                name="Champion";
                desc="Any one knight in play becomes a Champion. Place a marker underneath it. Instead of jumping like a knight, to the opposite corner of a 2 by 3 rectangle, a Champion jumps to the opposite corner of a 3 by 4 rectangle.";
                useCase="[i] Play this card immediately after your move. Continuing Effect until piece is captured.";
                continuingEffectUntilCaptured=true;
                drawableId=R.drawable.champion;
                break;

            case 6:
                name="Rebirth";
                desc="Move one enemy piece to any square it could have occupied at the beginning of the game. The square must be empty or contain one of your pieces. If one of your pieces is in the square, it is captured.";
                useCase="[i] Play this card immediately after your move.";
                drawableId=R.drawable.rebirth;
                break;

            case 7:
                name="Revelation";
                desc="Replace one of your knights or one of your opponent's knights by a bishop owned by the same player.";
                useCase="[i] Play this card immediately after your move.";
                drawableId=R.drawable.revelation;
                break;

            case 8:
                name="Long Jump";
                desc="Move one of your knights to any square whose color is different from the one it currently occupies. You cannot capture a piece with this move.";
                useCase="[i] Play this card on your turn, instead of making a regular move.";
                drawableId=R.drawable.long_jump;
                break;

            case 9:
                name="Lost Castle";
                desc="Swap the positions of one of your rooks and one of your opponent's rooks.";
                useCase="[i] Play this card on your turn, instead of making a regular move.";
                drawableId=R.drawable.lost_castle;
                break;

            case 10:
                name="Man of Straw";
                desc="Play this card when your king is in check (even checkmate). It swaps positions with any one of your pawns, as long as the new position does not place it in check.";
                useCase="[i] Play this card before your move.";
                drawableId=R.drawable.man_of_straw;
                break;

            case 11:
                name="Mystic Shield";
                desc="The piece you just moved cannot be captured by your opponent on his next turn. If you moved more than one piece, you must designate only one to be protected.";
                useCase="[i] Play this card immediately after your move.";
                drawableId=R.drawable.mystic_shield;
                break;

            case 12:
                name="Forbidden City";
                desc="Place a marker in any unoccupied square. No piece can enter this square, or pass through it, for the rest of the game. Knights and other \"jumping\" pieces may still pass over it.";
                useCase="[i] Play this card immediately after your move. Continuing Effect until the end of game.";
                continuingEffectUntilEnd=true;
                drawableId=R.drawable.forbidden_city;
                break;

            case 13:
                name="Holy Quest";
                desc="Swap the positions of a bishop and a knight belonging to your opponent.";
                useCase="[i] Play this card immediately after your move.";
                drawableId=R.drawable.holy_quest;
                break;

            case 14:
                name="Bombard";
                desc="On this move, one of your rooks can move in its normal straight line, jump over any piece or one obstruction on the board, and continue in a straight line. The piece or obstruction is not affected by being jumped. At the end of its move, the rook may make a normal capture.";
                useCase="[i] Play this card on your turn, instead of making a regular move.";
                drawableId=R.drawable.bombard;
                break;

            case 15:
                name="Coup";
                desc="Your king becomes a prince for the remainder of the game. A prince moves like a king but can be captured. Choose one of your pieces, except a rook or a queen, and mark it. This piece keeps its standard move, but is the new king, and your opponent will win if he checkmates it.";
                useCase="[i] Play this card immediately after your move. Continuing Effect until the end of game";
                continuingEffectUntilEnd=true;
                drawableId=R.drawable.coup;
                break;

            case 16:
                name="Hand of Fate";
                desc="Exchange your hand with your opponent's. He must draw another card to replace this one.";
                useCase="[i] Play this card before your move.";
                drawableId=R.drawable.hand_of_fate;
                break;

            case 17:
                name="Funeral Pyre";
                desc="All captured pieces of both players are now considered dead. They cannot be returned to the chessboard throguh the play of a card.";
                useCase="[i] Play this card immediately after your move.";
                drawableId=R.drawable.funeral_pyre;
                break;

            case 18:
                name="Martyr";
                desc="Play this card when one of your bishops has the choice of taking two or more of your opponent's pieces. Capture as many of these pieces as you want (at least two). Your bishop is removed from play and regarded as captured.";
                useCase="[i] Play this card on your turn, instead of making a regular move.";
                drawableId=R.drawable.martyr;
                break;

            case 19:
                name="Confabulation";
                desc="Make an otherwise legal move which puts two of your pieces (other than kings) on the same square. They \"merge\" into a new piece. It can move and capture like either one of them, and is affected by any card that affects either of them. Move the two pieces together. Confabulated pawns cannot promote.";
                useCase="[i] Play this card on your turn, instead of making a regular move. Continuing Effect until piece is captured";
                continuingEffectUntilCaptured=true;
                drawableId=R.drawable.confabulation;
                break;

            case 20:
                name="Fog of War";
                desc="This card cancels the effect of any other card. Both cards are discarded. If the opposing card constituted your opponent's whole move, he may make another move, but he may not play another card.";
                useCase="[i] Play this card immediately after your opponent has played the card you want to cancel.";
                drawableId=R.drawable.fog_of_war;
                break;

            case 21:
                name="Spoils of War";
                desc="Play this card when you capture one of your opponent's pieces. The capturing piece changes permanently into a piece of the kind it captured. For instance, if one of your pawns captures a knight, it becomes a knight.";
                useCase="[i] Play this card immediately after your move.";
                drawableId=R.drawable.spoils_of_war;
                break;

            case 22:
                name="Think Again";
                desc="Your opponent's move is cancelled. He must make a different move, with the same piece or with another one. If he used a card, he may take it back.";
                useCase="[i] Play this card immediately after your opponent's turn.";
                drawableId=R.drawable.think_again;
                break;

            case 23:
                name="Vulture";
                desc="Take the last card played by your opponent and put it in your hand. (If each player has his own deck of cards, you must also discard your top undrawn card.)";
                useCase="[i] Play this card immediately after your opponent plays a card.";
                drawableId=R.drawable.vulture;
                break;

            case 24:
                name="Abduction";
                desc="Your opponent must look away from the board for ten seconds, as you remove any one of his pieces except the king. He then has ten seconds to look at the board and state what piece you removed and which square it occupied. If he remembers correctly, the piece is put back in its place. If not, it is captured.";
                useCase="[i] Play this card immediately after your move.";
                drawableId=R.drawable.abduction;
                break;


            default:
                throw new IllegalArgumentException ("Not a valid card!");
        }
    }

    public void activateCard(ChessPiece playingPiece1, ChessPiece playingPiece2, Field field, Card[]cardsPlayer1, Card[]cardsPlayer2, Deck deck){
        switch (id){
            case 0:
                cowardice(playingPiece1);
                break;
            case 1:
                crusade(playingPiece1);
                break;
            case 2:
                darkMirror(playingPiece1,playingPiece2);
                break;
            case 3:
                deathDance(playingPiece1,playingPiece2);
                break;
            case 4:
                disintegration(playingPiece1);
                break;
            case 5:
                champion(playingPiece1);
                break;
            case 6:
                rebirth(playingPiece2);
                break;
            case 7:
                revelation(playingPiece1,playingPiece2);
                break;
            case 8:
                longJump(playingPiece1,field);
                break;
            case 9:
                lostCastle(playingPiece1,playingPiece2);
                break;
            case 10:
                manOfStraw(playingPiece1,playingPiece2);
                break;
            case 11:
                mysticShield(field);
                break;
            case 12:
                forbiddenCity(field);
                break;
            case 13:
                holyQuest(playingPiece1,playingPiece2);
                break;
            case 14:
                bombard(playingPiece1);
                break;
            case 15:
                coup(playingPiece1);
                break;
            case 16:
                handOfFate(cardsPlayer1,cardsPlayer2,deck);
                break;
            case 17:
                funeralPyre();
                break;
            case 18:
                martyr();
                break;
            case 19:
                confabulation();
                break;
            case 20:
                fogOfWar();
                break;
            case 21:
                spoilsOfWar(playingPiece1,playingPiece2);
                break;
            case 22:
                thinkAgain();
                break;
            case 23:
                vulture();
                break;
            case 24:
                abduction(playingPiece2);
                break;
        }
    }

    public void cowardice(ChessPiece oponentPiece){
        //move opponent pawn one or two fields backward (no occupied square)
        // TODO: 31.05.2021 implement

    }

    public void crusade(ChessPiece playingPiece){
        //bishop moves one more time (when it doesn't capture a piece)
        if (playingPiece.getPlayingPieceType()== ChessPieceType.BISHOP){
            playingPiece.getLegalFields();
        }
    }

    public void darkMirror(ChessPiece playingPiece, ChessPiece oponentPiece){
        //one pawns can capture by moving diagonally backward instead of forward
        // TODO: 31.05.2021 implement after implementing capturing-Function
    }

    public void deathDance(ChessPiece playingPiece, ChessPiece oponentPiece){
        //Exchange the position of any of your pieces with any adjacent enemy piece
       Field temp=playingPiece.getPosition();
       playingPiece.setPosition(oponentPiece.getPosition());
       oponentPiece.setPosition(temp);
    }

    public void disintegration(ChessPiece playingPiece){
        //Remove one of your own pawns (It is now dead and cannot be brought back into play)
        // TODO: 31.05.2021 implement after capturing-Function is implemented
    }

    public void abduction(ChessPiece oponentPiece){
        //opponent must look away from the board for ten seconds, as you remove any one of his pieces except the king. He then has ten seconds to look at the board If he remembers correctly, the piece is put back in its place.

    }

    public void bombard(ChessPiece playingPiece){
        //On this move, one of your rooks can move in its normal straight line, jump over any piece or one obstruction on the board, and continue in a straight line. (At the end of its move, the rook may make a normal capture)

    }

    public void coup(ChessPiece playingPiece){
        //Your king becomes a prince (prince moves like a king but can be captured). Choose one of your pieces, except a rook or a queen, and mark it. (This piece keeps its standard move, but is the new king)

    }

    public void confabulation(){
        //Make an otherwise legal move which puts two of your pieces (other than kings) on the same square. They "merge" into a new piece. It can move and capture like either one of them, and is affected by any card that affects either of them. Move the two pieces together. Confabulated pawns cannot promote.

    }

    public void champion(ChessPiece playingPiece){
        //Any one knight becomes a Champion. Place a marker underneath it. (a Champion jumps to the opposite corner of a 3 by 4 rectangle).

    }

    public void rebirth(ChessPiece oponentPiece){
        //Move one enemy piece to any square it could have occupied at the beginning of the game. The square must be empty or contain one of your pieces. If one of your pieces is in the square, it is captured.
        switch (oponentPiece.getPlayingPieceType()){
            case BISHOP:
                break;
            case KING:
                break;
            case KNIGHT:
                break;
            case PAWN:
                break;
            case QUEEN:
                break;
            case ROOK:
                break;
        }
    }

    public void revelation(ChessPiece playingPiece1,ChessPiece playingPiece2){
        //Replace one of your knights or one of your opponent's knights by a bishop owned by the same player.
        Field temp=playingPiece1.getPosition();
        playingPiece1.setPosition(playingPiece2.getPosition());
        playingPiece2.setPosition(temp);

        // TODO: 31.05.2021 check if the Pieces belong to same player
    }

    public void spoilsOfWar(ChessPiece playingPiece, ChessPiece oponentPiece){
        //when you capture one of your opponent's pieces. The capturing piece changes permanently into a piece of the kind it captured. For instance, if one of your pawns captures a knight, it becomes a knight.

    }

    public void thinkAgain(){
        //Your opponent's move is cancelled. He must make a different move

    }

    public void vulture(){
        //Take the last card played by your opponent and put it in your hand.

    }

    public void longJump(ChessPiece playingPiece, Field field){
        //Move one of your knights to any square whose color is different from the one it currently occupies. You cannot capture a piece with this move.

        playingPiece.setPosition(field);

        // TODO: 31.05.2021 move only if color of field is different from current color
    }

    public void lostCastle(ChessPiece playingPiece, ChessPiece oponentPiece){
        //Swap your rook with one of your oponents rooks
        Field temp=playingPiece.getPosition();
        playingPiece.setPosition(oponentPiece.getPosition());
        oponentPiece.setPosition(temp);
    }

    public void manOfStraw(ChessPiece playingPiece1, ChessPiece playingPiece2){
        //Play this card when your king is in check (even checkmate). It swaps positions with any one of your pawns, as long as the new position does not place it in check.

        Field temp=playingPiece1.getPosition();
        playingPiece1.setPosition(playingPiece2.getPosition());
        playingPiece2.setPosition(temp);

        // TODO: 31.05.2021 check if new position doesn't place king in check
    }

    public void martyr(){
        //Play this card when one of your bishops has the choice of taking two or more of your opponent's pieces. Capture as many of these pieces as you want (at least two). Your bishop is removed from play and regarded as captured.

    }

    public void mysticShield(Field field){
        //protect 1 of your pieces for the next turn
        field.getCurrentPiece().setProtected(true);
        field.setPlayingPieceShield();

        // TODO: 18.05.2021 shield only for the next turn 
    }

    public void fogOfWar(){
        //This card cancels the effect of any other card. Both cards are discarded. If the opposing card constituted your opponent's whole move, he may make another move, but he may not play another card.

    }

    public void forbiddenCity(Field field){
        //block field till end of game
        if (!field.hasPiece())
            field.setBlocked();

        // TODO: 18.05.2021 piece can't move to this field or jump over it


    }

    public void holyQuest(ChessPiece playingPiece1,ChessPiece playingPiece2){
        //Swap the positions of a bishop and a knight belonging to your opponent.
        if (playingPiece1.getPlayingPieceType()==ChessPieceType.BISHOP&&playingPiece2.getPlayingPieceType()==ChessPieceType.KNIGHT) {
            Field temp = playingPiece1.getPosition();
            playingPiece1.setPosition(playingPiece2.getPosition());
            playingPiece2.setPosition(temp);
        }


    }

    public void funeralPyre(){
        //All captured pieces of both players are now considered dead. They cannot be returned to the chessboard throguh the play of a card.

    }

    public void handOfFate(Card [] player1,Card[]player2, Deck deck){
        //Exchange your hand with your opponent's. He must draw another card to replace this one.
        Card[]temp=player1;

        player1=player2;

        int j=0;
        for (int i=0;i<player1.length;i++){
            if (temp[i].getName().equals("Hand of Fate")) {
                player2[j] = temp[i];
                j++;
            }
        }

        player2[player2.length]=deck.drawCard();
    }




    public void createBitmap(Resources resources, int drawableId){
        this.sprite = BitmapFactory.decodeResource(resources, drawableId);
    }

    public boolean isContinuingUntilEnd(){
        if (continuingEffectUntilEnd)
            return true;
        return false;
    }

    public boolean isContinuingUntilCaptured(){
        if (continuingEffectUntilCaptured)
            return true;
        return false;
    }

    public String getName(){return name;}

    public String getDesc(){return desc;}

    public String getUseCase(){return useCase;}

    public int getDrawableId(){return this.drawableId;}

    public boolean isOwned(){
        if (owned)
            return true;
        return false;
    }

    public void setOwned(){owned=true;}
}
