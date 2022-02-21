package edu.up.cs301.pig;

import static android.content.ContentValues.TAG;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * A GUI for a human to play Pig. This default version displays the GUI but is incomplete
 *
 *
 * @author Andrew M. Nuxoll, modified by Steven R. Vegdahl
 * @version February 2016
 */
public class PigHumanPlayer extends GameHumanPlayer implements OnClickListener {

	/* instance variables */

    // These variables will reference widgets that will be modified during play
    private TextView    playerScoreTextView  = null;
    private TextView    oppScoreTextView     = null;
    private TextView    turnTotalTextView    = null;
    private TextView    messageTextView      = null;
    private TextView    firstPlayerTextView  = null;
    private TextView    secondPlayerTextView = null;
    private ImageButton dieImageButton       = null;
    private Button      holdButton           = null;
    private boolean     clicked              = false;

    // the android activity that we are running
    private GameMainActivity myActivity;

    /**
     * constructor does nothing extra
     */
    public PigHumanPlayer(String name) {
        super(name);
    }

    /**
     * Returns the GUI's top view object
     *
     * @return
     * 		the top object in the GUI's view hierarchy
     */
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * callback method when we get a message (e.g., from the game)
     *
     * @param info
     * 		the message
     */
    @Override
    public void receiveInfo(GameInfo info) {
        //Log.d(TAG, "receiveInfo: receiveInfo is being called.");

        if(!(info instanceof PigGameState)) {
            flash(255, 1000);
            return;
        }

        PigGameState pState = new PigGameState((PigGameState) info);

        firstPlayerTextView.setText(allPlayerNames[0] + " Score: ");
        secondPlayerTextView.setText(allPlayerNames[1] + " Score: ");

        if(playerNum == 0) {
            playerScoreTextView.setText("" + pState.getPlayer0Score());
            oppScoreTextView.setText("" +pState.getPlayer1Score());
        }
        else {
            oppScoreTextView.setText("" +pState.getPlayer0Score());
            playerScoreTextView.setText("" + pState.getPlayer1Score());
        }
        turnTotalTextView.setText("" + pState.getCurrTotal());


        // When its player 0's turn make dice green
        if(pState.getPlayerID() == 0) {
            dieImageButton.setBackgroundColor(Color.GREEN);
        }
        // When its player 1's turn make dice blue
        else {
            dieImageButton.setBackgroundColor(Color.BLUE);
        }

        // Drawing the dice
        if(pState.getDiceValue() == 1) {
            dieImageButton.setImageResource(R.drawable.face1);
            if(allPlayerNames.length < 2) {
                messageTextView.setText("Awhh it's a 1. " + allPlayerNames[pState.getPlayerID()] + " lost everything");
                return;
            }
            else if(pState.getPlayerID() == 0) {
                messageTextView.setText("Awhh it's a 1. " + allPlayerNames[1] + " lost everything");
                return;
            }
            else {
                // was 0
                if(clicked == false) {
                    messageTextView.setText("Awhh it's a 1. " + allPlayerNames[0] + " lost everything");
                }
                return;
            }
        }
        else if(pState.getDiceValue() == 2) {
            dieImageButton.setImageResource(R.drawable.face2);
        }
        else if(pState.getDiceValue() == 3) {
            dieImageButton.setImageResource(R.drawable.face3);
        }
        else if(pState.getDiceValue() == 4) {
            dieImageButton.setImageResource(R.drawable.face4);
        }
        else if(pState.getDiceValue() == 5) {
            dieImageButton.setImageResource(R.drawable.face5);
        }
        else {
            dieImageButton.setImageResource(R.drawable.face6);
        }


        if(clicked) {
            if(allPlayerNames.length < 2) {
                messageTextView.setText(allPlayerNames[pState.getPlayerID()] + " only needs " + (50 - pState.getPlayer0Score()) + " more coins to win!");
                return;
            }
            else if(pState.getPlayerID() == 0) {
                messageTextView.setText(allPlayerNames[1] + " only needs " + (50 - pState.getPlayer1Score()) + "  more coins to win!");
                dieImageButton.setBackgroundColor(Color.GREEN);
                return;
            }
            else {
                messageTextView.setText(allPlayerNames[0] + " only needs " + (50 - pState.getPlayer0Score()) + " more coins to win!");
                dieImageButton.setBackgroundColor(Color.BLUE);
                return;
            }

        }
    }//receiveInfo

    /**
     * this method gets called when the user clicks the die or hold button. It
     * creates a new PigRollAction or PigHoldAction and sends it to the game.
     *
     * @param button
     * 		the button that was clicked
     */
    public void onClick(View button) {
        if(button instanceof ImageButton) {
            clicked = false;
            PigRollAction rollAction = new PigRollAction(this);
            this.game.sendAction(rollAction);
        }
        else {
            clicked = true;
            PigHoldAction holdAction = new PigHoldAction(this);
            this.game.sendAction(holdAction);
        }
    }// onClick

    /**
     * callback method--our game has been chosen/rechosen to be the GUI,
     * called from the GUI thread
     *
     * @param activity
     * 		the activity under which we are running
     */
    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.pig_layout);

        //Initialize the widget reference member variables
        this.playerScoreTextView = (TextView)activity.findViewById(R.id.yourScoreValue);
        this.oppScoreTextView    = (TextView)activity.findViewById(R.id.oppScoreValue);
        this.turnTotalTextView   = (TextView)activity.findViewById(R.id.turnTotalValue);
        this.messageTextView     = (TextView)activity.findViewById(R.id.messageTextView);
        this.dieImageButton      = (ImageButton)activity.findViewById(R.id.dieButton);
        this.holdButton          = (Button)activity.findViewById(R.id.holdButton);
        this.firstPlayerTextView = (TextView)activity.findViewById(R.id.yourScoreText);
        this.secondPlayerTextView = (TextView)activity.findViewById(R.id.oppScoreText);

        //Listen for button presses
        dieImageButton.setOnClickListener(this);
        holdButton.setOnClickListener(this);

    }//setAsGui

}// class PigHumanPlayer
