package org.itmo.ui.view;

import static org.itmo.ui.Symbols.ENDPOINT_LEFT_BOTTOM;
import static org.itmo.ui.Symbols.ENDPOINT_LEFT_TOP;
import static org.itmo.ui.Symbols.ENDPOINT_RIGHT_BOTTOM;
import static org.itmo.ui.Symbols.ENDPOINT_RIGHT_TOP;

import com.googlecode.lanterna.TextCharacter;
import org.itmo.game.objects.GameObject;

public class EndpointView extends GameObjectView{
    
    public EndpointView(GameObject gameObject, int rowShift, int columnShift) {
        super(gameObject, rowShift, columnShift);
    }
    
    @Override
    public TextCharacter[] getView() {
        return new TextCharacter[] {ENDPOINT_RIGHT_TOP, ENDPOINT_LEFT_TOP,
                                    ENDPOINT_RIGHT_BOTTOM, ENDPOINT_LEFT_BOTTOM};
    }
}
