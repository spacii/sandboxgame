package com.company.Game;

import com.company.engine.AbstractGame;
import com.company.engine.GameLoop;
import com.company.engine.Renderer;

import java.util.ArrayList;

public class GameManager extends AbstractGame {

    MainMenu mainMenu;
    EscapeMenu escapeMenu;
    Player player;
    GameWorld gameWorld;
    Camera camera;
    ClientSocket clientSocket;
    ArrayList<OtherPlayer> players;

    private int gameStatus;

    public GameManager(){
            mainMenu = new MainMenu(this);
            gameStatus = 0; // 0 - MainMenu, 1 - NewGame running, 2 - game pause, 3 - multiplayer
    }

    public Player getPlayer(){
        return player;
    }

    public void startNewGame(){
        gameWorld = new GameWorld();
        camera = new Camera();
        escapeMenu = new EscapeMenu(this);
        player = new Player(400,50,16,16, 100);
        gameWorld.generateWorld();
        gameStatus = 1;
    }

    public void takeWorldFromServer(ClientSocket clientSocket){
        clientSocket.getPrintWriter().println("giveMeWorld::");
    }

    public void connectToServer(){
        clientSocket = new ClientSocket(this);
        clientSocket.getThread().start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Connected to server");

        gameWorld = new GameWorld();
        camera = new Camera();
        //escapeMenu = new EscapeMenu(this);
        clientSocket.getPrintWriter().println("giveMeWorld::");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        players = new ArrayList<>();
        clientSocket.getPrintWriter().println("giveMePlayersAndSendMeToPlayers::");
        player = new Player(400,50,16,16, 100);

        gameStatus = 3;
    }

    public void loadWorld(){
        gameWorld = new GameWorld();
        camera = new Camera();
        escapeMenu = new EscapeMenu(this);
        player = new Player(400,50,16,16, 100);
    }

    public void updateThisId(int id, int x, int y){
        for(int i = 0; i < players.size(); i++){
            if(players.get(i).getID() == id){
                players.get(i).setPosX(x);
                players.get(i).setPosY(y);
            }
        }
    }

    public void spawnNew(int id){
        players.add(new OtherPlayer(400,50,16,16, 100, id));
        System.out.println("New Spawned");
    }

    public void spawnOldFag(int id, int XXX, int YYY){
        players.add(new OtherPlayer(XXX,YYY,16,16, 100, id));
    }

    @Override
    public void update(GameLoop gameLoop, float dt) {
        switch (gameStatus){
            case 0 :
                mainMenu.update(gameLoop);
                break;
            case 1 :
                player.update(gameLoop, this, dt);
                camera.update(gameLoop, this, dt);
                break;
            case 2 :
                escapeMenu.update(gameLoop);
                break;
            case 3 :
                player.update(gameLoop, this, dt);
                camera.update(gameLoop, this, dt);
                player.sendMyCoords(clientSocket);
                player.getCoords(clientSocket);
                for(int i = 0; i < players.size(); i++){
                    players.get(i).update(gameLoop,this, dt);
                }
            default:
                break;
        }
        //player.update(gameLoop, this, dt);
        //camera.update(gameLoop, this, dt);
    }

    float temp = 0;

    @Override
    public void render(GameLoop gameLoop, Renderer renderer) {

        switch (gameStatus){
            case 0 :
                mainMenu.render(renderer);
                break;
            case 1 :
                camera.render(renderer);
                gameWorld.render(gameLoop,renderer);
                player.render(gameLoop, renderer);
                break;
            case 2 :
                renderer.setCamX(0);
                renderer.setCamY(0);
                escapeMenu.render(renderer);
                break;
            case 3 :
                camera.render(renderer);
                gameWorld.render(gameLoop,renderer);
                player.render(gameLoop, renderer);
                for(int i = 0; i < players.size(); i++){
                    players.get(i).render(gameLoop, renderer);
                }
            default:
                break;

        }
        //camera.render(renderer);
        //gameWorld.render(gameLoop,renderer);
        //player.render(gameLoop, renderer);

    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public Camera getCamera() {
        return camera;
    }

    public int getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public static void main(String[] args) {
        GameLoop gameLoop = new GameLoop(new GameManager());
        gameLoop.start();
    }
}
