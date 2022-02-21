package edu.up.cs301.pig;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.util.Tickable;

/**
 * An AI for Pig
 *
 * @author Andrew M. Nuxoll
 * @version August 2015
 */
public class PigComputerPlayer extends GameComputerPlayer {

    /**
     * ctor does nothing extra
     */
    public PigComputerPlayer(String name) {
        super(name);
    }

    /**
     * callback method--game's state has changed
     *
     * @param info
     * 		the information (presumably containing the game's state)
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        PigGameState pgState= new PigGameState((PigGameState) info);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(pgState.getPlayerID() == playerNum) {
            int random = (int) (Math.random() * 2);
            if(random == 1) {
                PigRollAction rollAction = new PigRollAction(this);
                this.game.sendAction(rollAction);
            }
            else {
                PigHoldAction holdAction = new PigHoldAction(this);
                this.game.sendAction(holdAction);
            }
        }
        else {
            return;
        }
    }//receiveInfo

}
