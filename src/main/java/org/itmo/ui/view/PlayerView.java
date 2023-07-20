package org.itmo.ui.view;

import static org.itmo.ui.Symbols.PLAYER_LEFT_SIDE_OF_BODY;
import static org.itmo.ui.Symbols.PLAYER_LEFT_SIDE_OF_HEAD;
import static org.itmo.ui.Symbols.PLAYER_RIGHT_SIDE_OF_BODY;
import static org.itmo.ui.Symbols.PLAYER_RIGHT_SIDE_OF_BODY_LEFT_TURN;
import static org.itmo.ui.Symbols.PLAYER_RIGHT_SIDE_OF_BODY_RIGHT_TURN;
import static org.itmo.ui.Symbols.PLAYER_RIGHT_SIDE_OF_HEAD;

import com.googlecode.lanterna.TextCharacter;
import org.itmo.game.objects.GameObject;

public class PlayerView extends GameObjectView {
    
    TextCharacter[] currentCharacter = new TextCharacter[] { PLAYER_LEFT_SIDE_OF_HEAD,
                                                             PLAYER_RIGHT_SIDE_OF_HEAD,
                                                             PLAYER_LEFT_SIDE_OF_BODY,
                                                             PLAYER_RIGHT_SIDE_OF_BODY };

    public PlayerView(GameObject gameObject, int rowShift, int columnShift) {
        super(gameObject, rowShift, columnShift);
    }
    
    @Override
    public TextCharacter[] getView() {
        return currentCharacter;
    }
    
    /**
     * Sets the display of the player turned to the right
     */
    public void setRightTurn() {
        setTurning(PLAYER_LEFT_SIDE_OF_BODY, PLAYER_RIGHT_SIDE_OF_BODY_RIGHT_TURN);
    }
    
    /**
     * Sets the display of the player turned to the left
     */
    public void setLeftTurn() {
        setTurning(PLAYER_RIGHT_SIDE_OF_BODY_LEFT_TURN, PLAYER_RIGHT_SIDE_OF_BODY);
    }
    
    /**
     * Sets the display of the player turned to the upright
     */
    public void setStraight() {
        setTurning(PLAYER_LEFT_SIDE_OF_BODY, PLAYER_RIGHT_SIDE_OF_BODY);
    }
    
    /**
     * Changes the player's body to the passed values
     * @param second - value for the left side of the body
     * @param third - value for the right side of the body
     */
    private void setTurning(TextCharacter second, TextCharacter third) {
        currentCharacter[2] = second;
        currentCharacter[3] = third;
    }
}
