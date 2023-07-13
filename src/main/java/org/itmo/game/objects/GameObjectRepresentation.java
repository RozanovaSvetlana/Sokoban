package org.itmo.game.objects;

import com.googlecode.lanterna.TextCharacter;

public interface GameObjectRepresentation {
    
    /**
     * Returns the character-representation in the console for the object
     * @return TextCharacter
     */
    TextCharacter getRepresentation();
}
