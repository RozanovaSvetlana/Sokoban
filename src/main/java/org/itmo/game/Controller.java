package org.itmo.game;

import com.googlecode.lanterna.input.KeyStroke;
import java.io.IOException;
import org.itmo.game.logic.Game;

/**
 * A class that responds to the user's click in an appropriate way
 */
public class Controller {
    
    private static final Game gameLogic = new Game();
    
    public void play() throws IOException {
        if(startGame()) {
            gameLogic.toGameWindow();
            while (true) {
                KeyStroke input = gameLogic.getKeyPressed();
                if(input != null) {
                    switch (input.getKeyType()) {
                        case ArrowUp -> {
                        
                        }
                        case ArrowRight -> {
                        
                        }
                        case ArrowDown -> {
                        
                        }
                        case ArrowLeft -> {
                        
                        }
                        case Escape -> {
                            gameLogic.closeWindow();
                            return;
                        }
                        case Character -> {
                            //для обработки WASD
                        }
                    }
                }
            }
        }
    }
    
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
}
