package org.itmo.game.objects;

public interface GameObjectRepresentation {
    
    /**
     * Returns the character-representation in the console for the object
     * @return TextCharacter
     */
    Type getType();
    Rectangle getRectangle();
    
}
