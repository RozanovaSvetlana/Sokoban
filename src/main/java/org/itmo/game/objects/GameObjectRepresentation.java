package org.itmo.game.objects;

import com.googlecode.lanterna.TextCharacter;
import org.itmo.ui.PrintAndManage;

public interface GameObjectRepresentation {
    
    /**
     * Returns the character-representation in the console for the object
     * @return TextCharacter
     */
    TextCharacter[] getRepresentation();
    void print(PrintAndManage printAndManage);
}
