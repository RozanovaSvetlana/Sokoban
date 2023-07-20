package org.itmo.ui;

import static org.itmo.ui.Symbols.SPACE;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalRectangle;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import lombok.Getter;

public class PrintAndManage {
    
    private static Terminal terminal;
    private static TerminalScreen screen;
    
    @Getter
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
    
    /**
     * Returns the pressed button
     *
     * @return KeyStroke
     * @throws IOException
     */
    public KeyStroke getKeyPressed() throws IOException {
        return screen.readInput();
    }
    
    /**
     * Closes the terminal
     *
     * @throws IOException
     */
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
    
    /**
     * Replaces the characters printed in the range with the SPACE character set in the system
     * @param position - wipe out rectangle
     */
    public void wipeOut(TerminalRectangle position) {
        textGraphics.fillRectangle(position.position, position.size, SPACE);
    }
    
    /**
     * Fills the rectangle with the passed character
     *
     * @param position - filling rectangle
     * @param character - fill-in symbol
     */
    public void drawRectangle(TerminalRectangle position, TextCharacter character) {
        textGraphics.drawRectangle(position.position, position.size, character);
    }
    
    /**
     * Draws objects consisting of 4 different characters
     *
     * @param position - drawing aid
     * @param character - character set
     */
    public void drawSpecialObject(TerminalRectangle position, TextCharacter[] character) {
        textGraphics.setCharacter(position.x, position.y, character[0]);
        textGraphics.setCharacter(position.x + 1, position.y, character[1]);
        textGraphics.setCharacter(position.x, position.y + 1, character[2]);
        textGraphics.setCharacter(position.x + 1, position.y + 1, character[3]);
    }
    
}
