package com.game.chessmate.GameFiles.Networking.NetObjects;

public class GameDataObject {
    String lobbyCode;
    GameStates currentClientState;
    //Movement
    boolean movedBack;
    boolean moved;
    FieldDataObject origin;
    FieldDataObject target;


    //Cards
    boolean usedCard;
    int cardId;
    CardDataObject cardObject;

    //CheatFunction
    boolean cheatActivated;
    int WrongCheatRevealPlayer;

    //Win
    private boolean win;
    private boolean loose;

    public GameDataObject() {}

    public boolean isMovedBack() {
        return movedBack;
    }

    public void setMovedBack(boolean movedBack) {
        this.movedBack = movedBack;
    }

    public GameStates getCurrentClientState() {
        return currentClientState;
    }

    public void setCurrentClientState(GameStates currentClientState) {
        this.currentClientState = currentClientState;
    }

    public FieldDataObject getOrigin() {
        return origin;
    }

    public String getLobbyCode() {
        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public void setOrigin(FieldDataObject origin) {
        this.origin = origin;
    }

    public FieldDataObject getTarget() {
        return target;
    }

    public void setTarget(FieldDataObject target) {
        this.target = target;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean isUsedCard() {
        return usedCard;
    }

    public void setUsedCard(boolean usedCard) {
        this.usedCard = usedCard;
    }

    public CardDataObject getCardObject() {
        return cardObject;
    }

    public void setCardObject(CardDataObject cardObject) {
        this.cardObject = cardObject;
    }

    public boolean isCheatActivated() {
        return cheatActivated;
    }

    public void setCheatActivated(boolean cheatActivated) {
        this.cheatActivated = cheatActivated;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public int getWrongCheatRevealPlayer() {
        return WrongCheatRevealPlayer;
    }

    public void setWrongCheatRevealPlayer(int wrongCheatRevealPlayer) {
        WrongCheatRevealPlayer = wrongCheatRevealPlayer;
    }

    public boolean isLoose() {
        return loose;
    }

    public void setLoose(boolean loose) {
        this.loose = loose;
    }
}