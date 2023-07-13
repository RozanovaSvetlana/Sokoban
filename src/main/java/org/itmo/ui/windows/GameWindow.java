package org.itmo.ui.windows;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import org.itmo.game.map.Map;
import org.itmo.game.objects.GameObject;

public class GameWindow extends WindowImpl {
    Map map;
    private static String steps = "Steps: ";
    private static String time = "Time: ";
    private static String fileName = "Name: ";
    
    /**
     * One column indented from the edge of the terminal on the left,
     * one on the right. Eight characters for time and ten spaces between entries.
     */
    public static final int minColumnSize = fileName.length() + time.length() + 20;
    public static final int minRowSize = 5;
    private static final int rowIndentation = 4;
    private static int columnIndentation = 1;
    private static int hours = 0;
    private static int minutes = 0;
    private static int seconds = 0;
    private Optional<Timer> timerForPrintTime;
    private Optional<PrintTime> task;
    
    public GameWindow(int columnSize, int rowSize, String fileName, Map map) {
        super(columnSize, rowSize);
        this.map = map;
        if (map.getWidth() + 2 < columnSize) {
            columnIndentation = (columnSize - map.getWidth()) / 2;
        }
        this.fileName += fileName;
    }
    
    @Override
    public void play() throws IOException {
        screenPrinting.printString(0, 1, fileName);
        updateNumberSteps(0);
        refreshScreen();
        timerForPrintTime = Optional.of(new Timer());
        task = Optional.of(new PrintTime());
        timerForPrintTime.get().schedule(task.get(), 0, 1000);
        printMap();
        //вывод справки
    }
    
    public void updateNumberSteps(int amount) {
        String strAmount = Integer.toString(amount);
        screenPrinting.printString(columnSize - steps.length() - strAmount.length() - 1,
            2, steps + strAmount);
    }
    
    private void printMap() {
        printListGameObjects(map.getWalls());
        printListGameObjects(map.getBoxes());
        printListGameObjects(map.getEndpoints());
        map.getPlayer().print(screenPrinting, rowIndentation, columnIndentation);
    }
    
    private void printListGameObjects(List<? extends GameObject> gameObjects) {
        gameObjects.forEach((x) -> x.print(screenPrinting, rowIndentation, columnIndentation));
    }
    
    /**
     * Adds a unit to time
     */
    private void updateTime() {
        seconds++;
        if(seconds >= 60) {
            minutes += seconds / 60;
            seconds = seconds % 60;
        }
        if(minutes >= 60) {
            hours += minutes / 60;
            minutes = minutes % 60;
        }
    }
    
    @Override
    public void closeTerminal() throws IOException {
        task.ifPresent(TimerTask::cancel);
        timerForPrintTime.ifPresent(Timer::cancel);
        screenPrinting.clearScreen();
        screenPrinting.closeTerminal();
    }
    
    private class PrintTime extends TimerTask {
        
        @Override
        public void run() {
            String strHours = getTimeStr(hours);
            String strMinutes = getTimeStr(minutes);
            String strSeconds = getTimeStr(seconds);
            screenPrinting.printString(columnSize - time.length() - strHours.length()
                                       - strMinutes.length() - strSeconds.length() - 3, 1,
                time + strHours + ":" + strMinutes + ":" + strSeconds);
            updateTime();
            try {
                refreshScreen();
            } catch (IOException e) {
            }
        }
        
        /**
         * Converts a unit of time into a string
         * @param amount - number of seconds/minutes/hours
         * @return
         */
        private String getTimeStr(int amount) {
            if(amount < 10) {
                return "0" + amount;
            }
            return Integer.toString(amount);
        }
    }
}
