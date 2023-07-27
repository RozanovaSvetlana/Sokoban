package org.itmo.ui.windows;

import com.googlecode.lanterna.TerminalRectangle;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import lombok.Getter;
import org.itmo.game.logic.Direction;
import org.itmo.game.map.Map;
import org.itmo.ui.view.BoxView;
import org.itmo.ui.view.GameObjectView;
import org.itmo.ui.view.MapView;
import org.itmo.ui.view.PlayerView;

public class GameWindow extends WindowImpl {
    private MapView map;
    private List<TerminalRectangle> endpointPositions;
    private static String steps = "Steps: ";
    private static String time = "Time: ";
    private static String fileName = "Name: ";
    private static String win = "You win! Press enter or esc to exit!";
    
    /**
     * One column indented from the edge of the terminal on the left,
     * one on the right. Eight characters for time and ten spaces between entries.
     */
    public static final int minColumnSize = fileName.length() + time.length() + 20;
    public static final int winLength = win.length();
    public static final int minRowSize = 7;
    
    @Getter
    private static final int rowShift = 5;
    
    @Getter
    private static int columnShift = 1;
    private static int hours = 0;
    private static int minutes = 0;
    private static int seconds = 0;
    private Optional<Timer> timerForPrintTime;
    private Optional<PrintTime> task;
    
    public GameWindow(int columnSize, int rowSize, String fileName, Map map) {
        super(columnSize, rowSize);
        if (map.getWidth() * 2 + 2 < columnSize) {
            columnShift = (columnSize - map.getWidth() * 2) / 2;
        }
        this.map = new MapView(map, rowShift, columnShift);
        this.fileName += fileName;
        endpointPositions =
            this.map.getEndpoints().stream().map(GameObjectView::getTerminalRectangle).collect(
            Collectors.toList());
    }
    
    @Override
    public void play() throws IOException {
        screenPrinting.printString(0, 1, fileName);
        refreshScreen();
        timerForPrintTime = Optional.of(new Timer());
        task = Optional.of(new PrintTime());
        timerForPrintTime.get().schedule(task.get(), 0, 1000);
        printMap(0, null);
        //вывод справки
    }
    
    /**
     * Redraws the number of steps
     *
     * @param amount - new number of steps
     */
    public void updateNumberSteps(int amount) {
        String strAmount = Integer.toString(amount);
        screenPrinting.printString(columnSize - steps.length() - strAmount.length() - 1,
            2, steps + strAmount);
    }
    
    /**
     * Prints the map
     */
    public void printMap(int numberStep, Direction direction) {
        screenPrinting.wipeOut(new TerminalRectangle(columnShift, rowShift, map.getWidth(),
            map.getHeight()));
        printListGameObjects(map.getWalls());
        printListGameObjects(map.getEndpoints());
        updateViewBox();
        printListGameObjects(map.getBoxes());
        updatePlayerView(direction);
        map.getPlayer().print(screenPrinting);
        updateNumberSteps(numberStep);
    }
    
    /**
     * Prints the list of GameObjects
     * @param gameObjects - printable list
     */
    private void printListGameObjects(List<? extends GameObjectView> gameObjects) {
        gameObjects.forEach(this::printGameObject);
    }
    
    /**
     * Updates the boxes view depending on whether they are on the endpoint or not
     */
    private void updateViewBox() {
        map.getBoxes().stream().filter((x) -> isEndpoint(x.getTerminalRectangle()))
            .forEach((x) -> ((BoxView) x).setEndpointBox());
        map.getBoxes().stream().filter((x) -> !isEndpoint(x.getTerminalRectangle()))
            .forEach((x) -> ((BoxView) x).setNotEndpointBox());
    }
    
    /**
     * Updates the player's appearance depending on the direction of movement
     * @param direction
     */
    private void updatePlayerView(Direction direction) {
        if (direction != null) {
            switch (direction) {
                case RIGHT -> ((PlayerView) map.getPlayer()).setRightTurn();
                case LEFT -> ((PlayerView) map.getPlayer()).setLeftTurn();
                case UP, DOWN -> ((PlayerView) map.getPlayer()).setStraight();
            }
        }
    }
    
    /**
     * Checks if the endpoint is in the specified position
     * @param position - test position
     * @return true - if there is a endpoint in the specified position, false -
     * otherwise
     */
    private boolean isEndpoint(TerminalRectangle position) {
        return endpointPositions.contains(position);
    }
    
    /**
     * Prints GameObject
     * @param object - printable object
     */
    private void printGameObject(GameObjectView object) {
        object.print(screenPrinting);
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
    
    /**
     * Draws a victory notice
     */
    public void setWin() {
        task.ifPresent(TimerTask::cancel);
        timerForPrintTime.ifPresent(Timer::cancel);
        screenPrinting.printString((columnSize - win.length()) / 2, rowsSize - 2, win);
    }
    
    /**
     * Background shuffle for printing the time display
     */
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
