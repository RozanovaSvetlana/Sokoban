package org.itmo.ui;

import static org.itmo.game.Symbols.SPACE;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;

public class PrintAndManage {
    
    private static Terminal terminal;
    private static TerminalScreen screen;
    
    private static TextGraphics textGraphics;
    
    public PrintAndManage(Terminal terminal) throws IOException {
        PrintAndManage.terminal = terminal;
        screen = new TerminalScreen(terminal);
        screen.startScreen();
        PrintAndManage.terminal.setCursorVisible(false);
        textGraphics = screen.newTextGraphics();
    }
    
    /**
     * Prints the transferred string at the specified position
     *
     * @param column -- index of the column to start printing
     * @param row -- index of the row to start printing
     * @param stringForPrint -- printable string
     */
    public void printString(int column, int row, String stringForPrint) {
        textGraphics.putString(new TerminalPosition(column, row), stringForPrint);
    }
    
    public void printLineSingleCharacter(TerminalPosition start, TerminalPosition end,
                                         TextCharacter character) {
        textGraphics.drawLine(start, end, character);
    }
    
    /**
     *  Refreshes the screen
     *
     * @throws IOException
     */
    public void refreshScreen() throws IOException {
        screen.refresh();
    }
    
    /**
     * Clears screen
     *
     * @throws IOException
     */
    public void clearScreen() {
        screen.clear();
    }
    
    public KeyStroke getKeyPressed() throws IOException {
        return screen.readInput();
    }
    
    public void closeTerminal() throws IOException {
        terminal.close();
    }
    
    /**
     * Replaces the characters printed in the range with the SPACE character set in the system
     * @param columnStart - wipe start column
     * @param rowStart - wipe start row
     * @param columnEnd - wipe end column
     * @param rowEnd - - wipe start row
     */
    public void wipeOut(int columnStart, int rowStart, int columnEnd, int rowEnd) {
        textGraphics.drawLine(new TerminalPosition(columnStart, rowStart),
            new TerminalPosition(columnEnd, rowEnd), SPACE);
    }
    
}
