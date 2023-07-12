package org.itmo.game;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import java.io.IOException;
import org.itmo.ui.GameWindow;
import org.itmo.ui.LogoWindow;

/**
 * A class that responds to the user's click in an appropriate way
 */
public class Controller {
    
    public void play() throws IOException, InterruptedException {
        if(startGame()) {
            GameWindow gameWindow = new GameWindow();
            gameWindow.clearScreen();
            while (true) {
                KeyStroke input = gameWindow.getKeyPressed();
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
                            gameWindow.closeTerminal();
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
    
    private boolean startGame() throws IOException, InterruptedException {
        LogoWindow logo = new LogoWindow();
        logo.clearScreen();
        logo.play();
        while (true) {
            logo.enterPressed();
            KeyStroke input = logo.getKeyPressed();
            if(input != null) {
                switch (input.getKeyType()) {
                    case Enter -> {
                        logo.closeTerminal();
                        return true;
                    }
                    case Escape -> {
                        logo.closeTerminal();
                        return false;
                    }
                }
            }
        }
    }
}
