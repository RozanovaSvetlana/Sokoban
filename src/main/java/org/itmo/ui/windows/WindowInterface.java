package org.itmo.ui.windows;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;

public interface WindowInterface {
    static final int columnSize = 150;
    static final int rowsSize = 40;
    static final TerminalSize size = new TerminalSize(columnSize, rowsSize);
    
    void play() throws IOException;
    KeyStroke getKeyPressed() throws IOException;
    void closeTerminal() throws IOException;
    void clearScreen() throws IOException;
    void refreshScreen() throws IOException;
}
