package edu.up.cs301.pig;

import edu.up.cs301.game.infoMsg.GameState;

public class PigGameState extends GameState {
    private int id;
    private int player0Score;
    private int player1Score;
    private int currTotal;
    private int diceValue;

    public PigGameState() {
        id = 0;
        player0Score = 0;
        player1Score = 0;
        currTotal = 0;
        diceValue = 0;
    }

    //Copy constructor
    PigGameState(PigGameState p) {
        id = p.getPlayerID();
        player0Score = p.getPlayer0Score();
        player1Score = p.getPlayer1Score();
        currTotal = p.getCurrTotal();
        diceValue = p.getDiceValue();
    }

    //Getter methods
    public int getPlayerID() {
        return id;
    }

    public int getPlayer0Score() { return player0Score; }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getCurrTotal() {
        return currTotal;
    }

    public int getDiceValue() {
        return diceValue;
    }

    //Setter methods
    public void setPlayerId(int newID) {
        this.id = newID;
    }

    public void setPlayer0Score(int newP0Score) {
        this.player0Score = newP0Score;
    }

    public void setPlayer1Score(int newP1Score) {
        this.player1Score = newP1Score;
    }

    public void setCurrTotal(int newCurrTotal) {
        this.currTotal = newCurrTotal;
    }

    public void setDiceValue(int newDiceValue) {
        this.diceValue = newDiceValue;
    }
}
