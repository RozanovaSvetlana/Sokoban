package org.itmo.game;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.io.IOException;
import org.itmo.game.logic.Direction;
import org.itmo.game.logic.Game;

/**
 * A class that responds to the user's click in an appropriate way
 */
public class Controller {
    
    private static final Game gameLogic = new Game();
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
            gameLogic.toGameWindow(fileName);
            while (true) {
                KeyStroke input = gameLogic.getKeyPressed();
                if(input != null) {
                    switch (input.getKeyType()) {
                        case ArrowUp -> {
                            if(takeStep(Direction.UP)) {
                                return;
                            }
                        }
                        case ArrowRight -> {
                            if(takeStep(Direction.RIGHT)) {
                                return;
                            }
                        }
                        case ArrowDown -> {
                            if(takeStep(Direction.DOWN)) {
                                return;
                            }
                        }
                        case ArrowLeft -> {
                            if(takeStep(Direction.LEFT)) {
                                return;
                            }
                        }
                        case Escape -> {
                            gameLogic.closeWindow();
                            return;
                        }
                        case Character -> {
                            Character key = input.getCharacter();
                            switch (key) {
                                case 'w', 'ц' -> {
                                    if(takeStep(Direction.UP)) {
                                        return;
                                    }
                                }
                                case 'd', 'в' -> {
                                    if(takeStep(Direction.RIGHT)) {
                                        return;
                                    }
                                }
                                case 's', 'ы' -> {
                                    if(takeStep(Direction.DOWN)) {
                                        return;
                                    }
                                }
                                case 'a', 'ф' -> {
                                    if(takeStep(Direction.LEFT)) {
                                        return;
                                    }
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
        gameLogic.toLogoWindow();
        while (true) {
            KeyStroke input = gameLogic.getKeyPressed();
            switch (input.getKeyType()) {
                case Enter -> {
                    gameLogic.closeWindow();
                    return true;
                }
                case Escape -> {
                    gameLogic.closeWindow();
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
            while (true) {
                KeyStroke input = gameLogic.getKeyPressed();
                switch (input.getKeyType()) {
                    case Enter, Escape -> {
                        gameLogic.closeWindow();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
