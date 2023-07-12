package org.itmo.ui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import java.io.IOException;

public class WindowImpl implements WindowInterface {
    static PrintAndManage screenPrinting;

    public WindowImpl() {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        try {
            screenPrinting = new PrintAndManage(defaultTerminalFactory
                .setInitialTerminalSize(new TerminalSize(columnSize, rowsSize))
                .createTerminal());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void play() throws IOException {
    
    }
    
    /**
     * Returns the pressed key
     *
     * @return KeyStroke
     * @throws IOException
     */
    @Override
    public KeyStroke getKeyPressed() throws IOException {
        return screenPrinting.getKeyPressed();
    }
    
    /**
     * Close terminal
     *
     * @throws IOException
     */
    @Override
    public void closeTerminal() throws IOException {
        screenPrinting.closeTerminal();
    }
    
    @Override
    public void clearScreen() throws IOException {
        screenPrinting.clearScreen();
    }
    
    @Override
    public void refreshScreen() throws IOException {
        screenPrinting.refreshScreen();
    }
    
    
}
