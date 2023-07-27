package org.itmo.ui.windows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import org.itmo.utils.FileUtils;

public class LogoWindow extends WindowImpl {
    final Object lock = new Object();
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
            timerForPrintInscription.get().schedule(task.get(), 0, 2500);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        refreshScreen();
    }
    
    @Override
    public void closeTerminal() throws IOException {
        synchronized(lock) {
            task.ifPresent((x) -> PrintInscriptionPressEnter.isClose = true);
            task.ifPresent(TimerTask::cancel);
            timerForPrintInscription.ifPresent(Timer::cancel);
            screenPrinting.clearScreen();
            screenPrinting.closeTerminal();
        }
    }
    
    /**
     * Class for schedule task - print inscription
     */
    private class PrintInscriptionPressEnter extends TimerTask {
        public static boolean isClose = false;
        
        @Override
        public void run() {
            try {
                screenPrinting.wipeOut(0, pressEnterForStartRow, columnSize,
                    pressEnterForStartRow);
                refreshScreen();
                Thread.sleep(1000);
                synchronized (lock) {
                    if(!isClose) {
                        screenPrinting.printString(pressEnterForStartColumn, pressEnterForStartRow,
                            pressEnterForStartString);
                        refreshScreen();
                    }
                }
            } catch (Exception e) {
            }
        }
    }
}
