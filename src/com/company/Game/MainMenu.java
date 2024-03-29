package com.company.Game;

import com.company.engine.GameLoop;
import com.company.engine.Renderer;
import com.company.engine.gfx.Image;

import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MainMenu {
    private Image image = new Image("/MainMServer.jpg");
    private GameManager gameManager;
    private boolean load1 = true;

    public MainMenu(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void update(GameLoop gameLoop){
        if((gameLoop.getInput().getMouseX() >= 224 && gameLoop.getInput().getMouseX() <= 414)
        && (gameLoop.getInput().getMouseY() >= 62 && gameLoop.getInput().getMouseY() <= 127)){
            if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
                startNewGame(gameManager);
            }
        }

        if((gameLoop.getInput().getMouseX() >= 224 && gameLoop.getInput().getMouseX() <= 414)
                && (gameLoop.getInput().getMouseY() >= 138 && gameLoop.getInput().getMouseY() <= 204)){
            if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
                loadGame();
            }
        }


        if((gameLoop.getInput().getMouseX() >= 427 && gameLoop.getInput().getMouseX() <= 616)
                && (gameLoop.getInput().getMouseY() >= 62 && gameLoop.getInput().getMouseY() <= 127)){
            if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
                connectToServer();
            }
        }

        if((gameLoop.getInput().getMouseX() >= 224 && gameLoop.getInput().getMouseX() <= 414)
                && (gameLoop.getInput().getMouseY() >= 216 && gameLoop.getInput().getMouseY() <= 281)){
            if(gameLoop.getInput().isButton(MouseEvent.BUTTON1)){
                exitGame();
            }
        }
    }

    public void render(Renderer renderer){
        renderer.drawImage(image, 0,0);
    }

    public void startNewGame(GameManager gameManager){
        gameManager.startNewGame();
    }

    public void connectToServer(){
        gameManager.connectToServer();
    }

    public void loadGame(){
        if(load1){
            load1 = false;
            try{
                ArrayList<Integer> IDs;
                ArrayList<Integer> Xs;
                ArrayList<Integer> Ys;

                FileInputStream fis = new FileInputStream("id.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);
                IDs = (ArrayList<Integer>)ois.readObject();
                ois.close();
                fis.close();

                FileInputStream fis1 = new FileInputStream("x.dat");
                ObjectInputStream ois1 = new ObjectInputStream(fis1);
                Xs = (ArrayList<Integer>)ois1.readObject();
                ois1.close();
                fis1.close();

                FileInputStream fis2 = new FileInputStream("y.dat");
                ObjectInputStream ois2 = new ObjectInputStream(fis2);
                Ys = (ArrayList<Integer>)ois2.readObject();
                ois2.close();
                fis2.close();
                gameManager.loadWorld();
                //gameManager.gameWorld.loadWorld(IDs, Xs, Ys);
                System.out.println("World Loaded");
                gameManager.setGameStatus(1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void exitGame(){
        System.exit(1);
    }
}
