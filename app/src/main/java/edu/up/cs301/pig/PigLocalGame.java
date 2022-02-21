package edu.up.cs301.pig;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameState;

import android.util.Log;

// dummy comment, to see if commit and push work from srvegdahl account

/**
 * class PigLocalGame controls the play of the game
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 * @version February 2016
 */
public class PigLocalGame extends LocalGame {
    PigGameState pigGameState;

    /**
     * This ctor creates a new game state
     */
    public PigLocalGame() {
        pigGameState = new PigGameState();
    }

    /**
     * can the player with the given id take an action right now?
     */
    @Override
    protected boolean canMove(int playerIdx) {
        if(pigGameState.getPlayerID() == playerIdx) {
            return true;
        }
        return false;
    }

    /**
     * This method is called when a new action arrives from a player
     *
     * @return true if the action was taken or false if the action was invalid/illegal.
     */
    @Override
    protected boolean makeMove(GameAction action) {
        if(action instanceof PigHoldAction) {
            if(pigGameState.getPlayerID() == 0) {
                pigGameState.setPlayer0Score(pigGameState.getPlayer0Score() + pigGameState.getCurrTotal());
                pigGameState.setCurrTotal(0);
                if(players.length > 1) {
                    pigGameState.setPlayerId(1);
                }
            }
            else {
                pigGameState.setPlayer1Score(pigGameState.getPlayer1Score() + pigGameState.getCurrTotal());
                pigGameState.setCurrTotal(0);
                pigGameState.setPlayerId(0);
            }
            return true;
        }
        else if(action instanceof PigRollAction) {
            pigGameState.setDiceValue((int) (Math.random()* 5) + 1);
            if(pigGameState.getPlayerID() == 0) {
                if (pigGameState.getDiceValue() == 1) {
                    pigGameState.setCurrTotal(0);
                    if(players.length > 1) {
                        pigGameState.setPlayerId(1);
                    }
                } else {
                    pigGameState.setCurrTotal(pigGameState.getDiceValue() + pigGameState.getCurrTotal());
                }
            }
            else {
                if (pigGameState.getDiceValue() == 1) {
                    pigGameState.setCurrTotal(0);
                    pigGameState.setPlayerId(0);
                } else {
                    pigGameState.setCurrTotal(pigGameState.getDiceValue() + pigGameState.getCurrTotal());
                }
            }
            return true;
        }
        else {
            return false;
        }
    }//makeMove

    /**
     * send the updated state to a given player
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        PigGameState pState = new PigGameState(pigGameState);
        p.sendInfo(pState);
    }//sendUpdatedSate

    /**
     * Check if the game is over
     *
     * @return
     * 		a message that tells who has won the game, or null if the
     * 		game is not over
     */
    @Override
    protected String checkIfGameOver() {
        if(pigGameState.getPlayer0Score() >= 50) {
            return "Player 0 won with a score of: " + pigGameState.getPlayer0Score();
        }
        else if(pigGameState.getPlayer1Score()>= 50) {
            return "Player 1 won with a score of: " + pigGameState.getPlayer1Score();
        }
        else {
            return null;
        }
    }

}// class PigLocalGame
