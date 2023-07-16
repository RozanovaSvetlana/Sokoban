package org.itmo.ui.windows;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;

public interface WindowInterface {
    
    /**
     * Launches a window
     *
     * @throws IOException
     */
    void play() throws IOException;
    
    /**
     * Returns the pressed button
     *
     * @return KeyStroke
     * @throws IOException
     */
    KeyStroke getKeyPressed() throws IOException;
    
    /**
     * Closes the terminal
     *
     * @throws IOException
     */
    void closeTerminal() throws IOException;
    
    /**
     * Cleans the screen
     *
     * @throws IOException
     */
    void clearScreen() throws IOException;
    
    /**
     * Performs screen updates
     *
     * @throws IOException
     */
    void refreshScreen() throws IOException;
}
