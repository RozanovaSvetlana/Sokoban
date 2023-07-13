package org.itmo.game.objects;

import static org.itmo.game.Symbols.PLAYER_LEFT_SIDE_OF_BODY;
import static org.itmo.game.Symbols.PLAYER_LEFT_SIDE_OF_HEAD;
import static org.itmo.game.Symbols.PLAYER_RIGHT_SIDE_OF_BODY;
import static org.itmo.game.Symbols.PLAYER_RIGHT_SIDE_OF_BODY_LEFT_TURN;
import static org.itmo.game.Symbols.PLAYER_RIGHT_SIDE_OF_BODY_RIGHT_TURN;
import static org.itmo.game.Symbols.PLAYER_RIGHT_SIDE_OF_HEAD;

import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.TextCharacter;

public class Player extends GameObject implements GameObjectRepresentation {
    
    TextCharacter[] currentCharacterStraight = new TextCharacter[] { PLAYER_LEFT_SIDE_OF_HEAD,
                                                                     PLAYER_RIGHT_SIDE_OF_HEAD,
                                                                     PLAYER_LEFT_SIDE_OF_BODY,
                                                                     PLAYER_RIGHT_SIDE_OF_BODY };
    
    TextCharacter[] currentCharacter = currentCharacterStraight;
    
    public Player(TerminalRectangle position) {
        super(position);
    }
    
    public void setRightTurn() {
        setTurning(PLAYER_LEFT_SIDE_OF_BODY, PLAYER_RIGHT_SIDE_OF_BODY_RIGHT_TURN);
    }
    
    public void setLeftTurn() {
        setTurning(PLAYER_RIGHT_SIDE_OF_BODY_LEFT_TURN, PLAYER_RIGHT_SIDE_OF_BODY_RIGHT_TURN);
    }
    
    public void setStraight() {
        setTurning(PLAYER_LEFT_SIDE_OF_BODY, PLAYER_RIGHT_SIDE_OF_BODY);
    }
    
    private void setTurning(TextCharacter second, TextCharacter third) {
        currentCharacter[2] = second;
        currentCharacter[3] = third;
    }
    
    @Override
    public TextCharacter[] getRepresentation() {
        return currentCharacter;
    }

}
