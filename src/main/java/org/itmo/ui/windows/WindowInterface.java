package org.itmo.ui.windows;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;

public interface WindowInterface {
    
    void play() throws IOException;
    KeyStroke getKeyPressed() throws IOException;
    void closeTerminal() throws IOException;
    void clearScreen() throws IOException;
    void refreshScreen() throws IOException;
}
