package org.itmo.game;

import static org.itmo.game.logic.Direction.DOWN;
import static org.itmo.game.logic.Direction.LEFT;
import static org.itmo.game.logic.Direction.RIGHT;
import static org.itmo.game.logic.Direction.UP;

import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;
import org.itmo.game.logic.Direction;
import org.itmo.game.logic.Game;
import org.itmo.ui.GameEngine;

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
    public void play() throws IOException {
        if(startGame()) {
            gameEngine.toGameWindow(gameLogic.setMap(fileName), fileName);
            while (true) {
                KeyStroke input = gameEngine.getKeyPressed();
                if(input != null) {
                    switch (input.getKeyType()) {
                        case ArrowUp -> {
                            if(takeStep(UP, gameEngine)) {
                                return;
                            }
                            gameEngine.printMap(gameLogic.getNumberStep(), UP);
                        }
                        case ArrowRight -> {
                            if(takeStep(RIGHT, gameEngine)) {
                                return;
                            }
                            gameEngine.printMap(gameLogic.getNumberStep(), RIGHT);
                        }
                        case ArrowDown -> {
                            if(takeStep(DOWN, gameEngine)) {
                                return;
                            }
                            gameEngine.printMap(gameLogic.getNumberStep(), DOWN);
                        }
                        case ArrowLeft -> {
                            if(takeStep(LEFT, gameEngine)) {
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
                                    if(takeStep(UP, gameEngine)) {
                                        return;
                                    }
                                    gameEngine.printMap(gameLogic.getNumberStep(), UP);
                                }
                                case 'd', 'в' -> {
                                    if(takeStep(RIGHT, gameEngine)) {
                                        return;
                                    }
                                    gameEngine.printMap(gameLogic.getNumberStep(), RIGHT);
                                }
                                case 's', 'ы' -> {
                                    if(takeStep(DOWN, gameEngine)) {
                                        return;
                                    }
                                    gameEngine.printMap(gameLogic.getNumberStep(), DOWN);
                                }
                                case 'a', 'ф' -> {
                                    if(takeStep(LEFT, gameEngine)) {
                                        return;
                                    }
                                    gameEngine.printMap(gameLogic.getNumberStep(), LEFT);
                                }
                            }
                        }
                    }
                }
            }
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
    private boolean takeStep(Direction direction, GameEngine gameEngine) throws IOException {
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
