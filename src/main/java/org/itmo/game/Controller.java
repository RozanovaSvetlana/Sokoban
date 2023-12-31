package org.itmo.game;

import static org.itmo.game.logic.Direction.DOWN;
import static org.itmo.game.logic.Direction.LEFT;
import static org.itmo.game.logic.Direction.RIGHT;
import static org.itmo.game.logic.Direction.UP;

import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.itmo.game.logic.Direction;
import org.itmo.game.logic.Game;
import org.itmo.game.logic.Solver;
import org.itmo.game.map.Map;
import org.itmo.ui.GameEngine;
import org.itmo.utils.FileUtils;

/**
 * A class that responds to the user's click in an appropriate way
 */
public class Controller {
    
    private static final Game gameLogic = new Game();
    private static final GameEngine gameEngine = new GameEngine();
    private String fileName;
    
    public Controller(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * Runs and controls the game
     *
     * @throws IOException
     */
    public void play() throws IOException, InterruptedException {
        if(startGame()) {
            Map map = gameLogic.setMap(fileName);
            gameEngine.toGameWindow(map, fileName);
            while (true) {
                KeyStroke input = gameEngine.getKeyPressed();
                if(input != null) {
                    switch (input.getKeyType()) {
                        case ArrowUp -> {
                            if(takeStep(UP)) {
                                return;
                            }
                            gameEngine.printMap(gameLogic.getNumberStep(), UP);
                        }
                        case ArrowRight -> {
                            if(takeStep(RIGHT)) {
                                return;
                            }
                            gameEngine.printMap(gameLogic.getNumberStep(), RIGHT);
                        }
                        case ArrowDown -> {
                            if(takeStep(DOWN)) {
                                return;
                            }
                            gameEngine.printMap(gameLogic.getNumberStep(), DOWN);
                        }
                        case ArrowLeft -> {
                            if(takeStep(LEFT)) {
                                return;
                            }
                            gameEngine.printMap(gameLogic.getNumberStep(), LEFT);
                        }
                        case Escape -> {
                            gameEngine.closeWindow();
                            return;
                        }
                        case Character -> {
                            Character key = input.getCharacter();
                            switch (key) {
                                case 'w', 'ц' -> {
                                    if(takeStep(UP)) {
                                        return;
                                    }
                                    gameEngine.printMap(gameLogic.getNumberStep(), UP);
                                }
                                case 'd', 'в' -> {
                                    if(takeStep(RIGHT)) {
                                        return;
                                    }
                                    gameEngine.printMap(gameLogic.getNumberStep(), RIGHT);
                                }
                                case 's', 'ы' -> {
                                    if(takeStep(DOWN)) {
                                        return;
                                    }
                                    gameEngine.printMap(gameLogic.getNumberStep(), DOWN);
                                }
                                case 'a', 'ф' -> {
                                    if(takeStep(LEFT)) {
                                        return;
                                    }
                                    gameEngine.printMap(gameLogic.getNumberStep(), LEFT);
                                }
                                case 'r', 'к' -> {
                                    gameEngine.closeWindow();
                                    new Controller(gameLogic.writeMapToFile())
                                        .printSolution();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Seeks a solution for the transferred card
     *
     * @param isWriteToFile - if true - the solution will be written to the file,
     *                     otherwise - step-by-step display of the solution will be performed
     * @param fileNameForSolution - filename of the file to record the response
     *                            (if no recording is required, the argument is ignored)
     * @throws IOException
     * @throws InterruptedException
     */
    public void solve(boolean isWriteToFile, String fileNameForSolution)
        throws IOException, InterruptedException {
        if(isWriteToFile) {
            try (OutputStream fileForWrite = FileUtils.getFileForWrite(fileNameForSolution)) {
                Solver solver = new Solver();
                Map map = gameLogic.setMap(fileName);
                List<Direction> path =  solver.findSolution(map);
                if(path == null) {
                    fileForWrite.write("Solution not found!".getBytes(StandardCharsets.UTF_8));
                    System.out.println("Solution not found!");
                } else {
                    fileForWrite.write(("Solution for " + fileName + ": ").getBytes());
                    fileForWrite.write(path.toString().getBytes(StandardCharsets.UTF_8));
                    System.out.println("Solution is found and written to a file: "
                                       + fileNameForSolution);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            printSolutionWithStartWindow(true);
        }
    }
    
    private void printSolutionWithStartWindow(boolean isStartWindow) throws InterruptedException,
        IOException {
        if(isStartWindow) {
            if (startGame()) {
                printSolution();
            }
        } else {
            printSolution();
        }
    }
    
    private void printSolution() throws IOException, InterruptedException {
        Solver solver = new Solver();
        Map map = gameLogic.setMap(fileName);
        List<Direction> path =  solver.findSolution(map);
        if(path == null) {
            System.out.println("Solution not found!");
            return;
        }
        gameEngine.toGameWindow(map, fileName);
        Thread.sleep(1000);
        for (Direction direction : path) {
            takeStep(direction);
            gameEngine.printMap(gameLogic.getNumberStep(), direction);
            Thread.sleep(1000);
        }
    }
    
    /**
     * Starts and handles interaction with the logo window
     * @return true - if it is necessary to start the game,
     * false - if it is necessary to close the game
     * @throws IOException
     */
    private boolean startGame() throws IOException {
        gameEngine.toLogoWindow();
        while (true) {
            KeyStroke input = gameEngine.getKeyPressed();
            switch (input.getKeyType()) {
                case Enter -> {
                    gameEngine.closeWindow();
                    return true;
                }
                case Escape -> {
                    gameEngine.closeWindow();
                    return false;
                }
            }
        }
    }
    
    /**
     * Makes a move and handles the end of the game
     *
     * @param direction - direction of movement
     * @return true - if the game ended after the step, false - otherwise
     * @throws IOException
     */
    private boolean takeStep(Direction direction) throws IOException {
        if(gameLogic.takeStep(direction)) {
            gameEngine.setWin(gameLogic.getNumberStep(), direction);
            while (true) {
                KeyStroke input = gameEngine.getKeyPressed();
                switch (input.getKeyType()) {
                    case Enter, Escape -> {
                        gameEngine.closeWindow();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
