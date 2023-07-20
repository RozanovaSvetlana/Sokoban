package org.itmo.ui.view;

import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.TextCharacter;
import org.itmo.ui.PrintAndManage;

public interface GameObjectViewInterface {
    
    /**
     * Return actual position with shift
     * @return
     */
    TerminalRectangle getTerminalRectangle();
    
    /**
     * Get actual view for object
     * @return actual view
     */
    TextCharacter[] getView();
    
    /**
     * Print object
     * @param printAndManage - manager for print
     */
    void print(PrintAndManage printAndManage);
}
