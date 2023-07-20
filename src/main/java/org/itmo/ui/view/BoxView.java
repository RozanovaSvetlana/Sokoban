package org.itmo.ui.view;

import static org.itmo.ui.Symbols.GREEN_BOX_LEFT_BOTTOM;
import static org.itmo.ui.Symbols.GREEN_BOX_LEFT_TOP;
import static org.itmo.ui.Symbols.GREEN_BOX_RIGHT_BOTTOM;
import static org.itmo.ui.Symbols.GREEN_BOX_RIGHT_TOP;
import static org.itmo.ui.Symbols.RED_BOX_LEFT_BOTTOM;
import static org.itmo.ui.Symbols.RED_BOX_LEFT_TOP;
import static org.itmo.ui.Symbols.RED_BOX_RIGHT_BOTTOM;
import static org.itmo.ui.Symbols.RED_BOX_RIGHT_TOP;

import com.googlecode.lanterna.TextCharacter;
import org.itmo.game.objects.GameObject;

public class BoxView extends GameObjectView {
    
    TextCharacter[] redBox = new TextCharacter[] { RED_BOX_LEFT_TOP, RED_BOX_RIGHT_TOP,
                                                   RED_BOX_LEFT_BOTTOM, RED_BOX_RIGHT_BOTTOM };
    
    TextCharacter[] greenBox = new TextCharacter[] { GREEN_BOX_LEFT_TOP, GREEN_BOX_RIGHT_TOP,
                                                     GREEN_BOX_LEFT_BOTTOM, GREEN_BOX_RIGHT_BOTTOM };
    
    TextCharacter[] currentCharacter = redBox;
    
    public BoxView(GameObject gameObject, int rowShift, int columnShift) {
        super(gameObject, rowShift, columnShift);
    }
    
    @Override
    public TextCharacter[] getView() {
        return currentCharacter;
    }
    
    /**
     * Makes the box green
     * Used when the crate is placed at the end point
     */
    public void setEndpointBox() {
        currentCharacter = greenBox;
    }
    
    /**
     * Turns the crate red
     * Used if the crate is removed from the end point
     */
    public void setNotEndpointBox() {
        currentCharacter = redBox;
    }
}
