package org.itmo.ui.windows;

import java.io.IOException;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import org.itmo.game.map.Map;

public class GameWindow extends WindowImpl {
    Map map;
    private String steps = "Steps: ";
    private String time = "Time: ";
    private static int hours = 0;
    private static int minutes = 0;
    private static int seconds = 0;
    private Optional<Timer> timerForPrintTime;
    private Optional<PrintTime> task;
    
    @Override
    public void play() throws IOException {
        screenPrinting.printString(0, 1, "Name: file_name");
        updateNumberSteps(0);
        refreshScreen();
        timerForPrintTime = Optional.of(new Timer());
        task = Optional.of(new PrintTime());
        timerForPrintTime.get().schedule(task.get(), 0, 1000);
        //вывод карты
        //вывод справки
        //вывод времени игры - 00:00 по дефолту
    }
    
    public void updateNumberSteps(int amount) {
        String strAmount = Integer.toString(amount);
        screenPrinting.printString(columnSize - steps.length() - strAmount.length() - 1,
            2, steps + strAmount);
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
