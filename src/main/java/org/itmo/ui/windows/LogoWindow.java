package org.itmo.ui.windows;

import static org.itmo.game.Symbols.SPACE;

import com.googlecode.lanterna.TerminalPosition;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import org.itmo.utils.FileUtils;

public class LogoWindow extends WindowImpl {
    private static final String fileName = "logo";
    private int pressEnterForStartColumn = 0;
    private int pressEnterForStartRow = 0;
    private String pressEnterForStartString = "press enter to start game";
    private Optional<Timer> timerForPrintInscription;
    private Optional<PrintInscriptionPressEnter> task;
    
    /**
     * Print logo
     * @throws IOException
     */
    @Override
    public void play() throws IOException {
        try (BufferedReader reader =
                 new BufferedReader(new InputStreamReader(
                     FileUtils.getFileFromResource(fileName)))) {
            String line;
            int column, row;
            line = reader.readLine();
            if(line == null) {
                throw new RuntimeException("Error print logo");
            }
            String[] size = line.split(" ");
            try {
                int columnLogo = Integer.parseInt(size[0]);
                column = (columnSize - columnLogo) / 2;
                pressEnterForStartColumn = column + (columnLogo - pressEnterForStartString.length()) / 2;
                row = (rowsSize - Integer.parseInt(size[1])) / 2;
            } catch (Exception e) {
                column = 0;
                row = 0;
            }
            while ((line = reader.readLine()) != null) {
                screenPrinting.printString(column, row, line);
                row++;
            }
            pressEnterForStartRow = row + 2;
            timerForPrintInscription = Optional.of(new Timer());
            task = Optional.of(new PrintInscriptionPressEnter());
            timerForPrintInscription.get().schedule(task.get(), 0, 1000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refreshScreen();
    }
    
    /**
     * Imitates blinking of line
     */
    public void enterPressed() throws IOException, InterruptedException {
        screenPrinting.printLineSingleCharacter(new TerminalPosition(0, pressEnterForStartRow),
            new TerminalPosition(columnSize, pressEnterForStartRow), SPACE);
        refreshScreen();
        Thread.sleep(1000);
        screenPrinting.printString(pressEnterForStartColumn, pressEnterForStartRow,
            pressEnterForStartString);
        refreshScreen();
        Thread.sleep(1000);
    }
    
    @Override
    public void closeTerminal() throws IOException {
        super.closeTerminal();
        task.ifPresent(TimerTask::cancel);
        timerForPrintInscription.ifPresent(Timer::cancel);
    }
    
    /**
     * Class for schedule task - print inscription
     */
    private class PrintInscriptionPressEnter extends TimerTask {
        @Override
        public void run() {
            try {
                enterPressed();
            } catch (Exception e) {
            }
        }
    }
}
