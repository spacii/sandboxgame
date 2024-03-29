package server;

import com.company.Game.GameWorld;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main2 {

    static private ArrayList<ServerConnection> connectionsList = new ArrayList<>();
    static private GameWorld gameWorldServer;


    static void spawnNewInAll(int id){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() != id){
                connectionsList.get(i).getPrintWriter().println("newConnected::" + id + "::");
            }
        }
    }

    static void spawnOldInNew(int id){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() != id){
                connectionsList.get(connectionsList.size()-1).getPrintWriter().println("oldFag::" + connectionsList.get(i).getConnectionId() + "::"
                + connectionsList.get(i).getPosX() + "::" + connectionsList.get(i).getPosY() + "::");
            }
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8189);
            System.out.println("Server started");
            gameWorldServer = new GameWorld();
            gameWorldServer.generateWorld();

            while (true){
                Socket socket = serverSocket.accept();
                ServerConnection serverConnection = new ServerConnection(socket, connectionsList, gameWorldServer);
                connectionsList.add(serverConnection);
                connectionsList.get((connectionsList.size()-1)).getThread().start();

                //spawnNewInAll(connectionsList.get((connectionsList.size()-1)).getConnectionId());
                //spawnOldInNew(connectionsList.get((connectionsList.size()-1)).getConnectionId());

                System.out.println("Player connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class ServerConnection implements Runnable{
    private Thread thread;
    private Socket socket;
    private Scanner scanner;
    private PrintWriter printWriter;
    private int connectionId;
    private int posX, posY;
    private ArrayList<ServerConnection> connectionsList;
    private GameWorld gameWorldServer;

    ServerConnection(Socket socket, ArrayList<ServerConnection> connectionsList, GameWorld gameWorldServer){
        this.connectionsList = connectionsList;
        this.thread = new Thread(this);
        this.socket = socket;
        this.gameWorldServer = gameWorldServer;
        this.connectionId = (int)(Math.random() * 1000 + 0);
        try{
            this.scanner = new Scanner(this.socket.getInputStream());
            this.printWriter = new PrintWriter(this.socket.getOutputStream(), true);
            scanner.useDelimiter("::");
            this.printWriter.println("generatedID::"+connectionId+"::");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Thread getThread() {
        return thread;
    }

    int getConnectionId() {
        return connectionId;
    }

    PrintWriter getPrintWriter() {
        return printWriter;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void updateMyPos(int x, int y){
        posX = x;
        posY = y;
    }

    public void giveCoordsToPlayer(){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() != connectionId){
                printWriter.println("otherPlayerCoords::"+ (int)connectionsList.get(i).getConnectionId() + "::" + (int)connectionsList.get(i).posX + "::" + (int)connectionsList.get(i).posY + "::");
            }
        }
    }

    public void giveWorldToPlayer(){
        printWriter.println("blockFromServerWorld::");
/*
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(gameWorldServer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
*/

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(gameWorldServer.getWorldsBlocks());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(gameWorldServer.getWorldCollision());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    void spawnNewInAll(int id){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() != id){
                connectionsList.get(i).getPrintWriter().println("newConnected::" + id + "::");
            }
        }
    }

    void spawnOldInNew(int id){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() != id){
                connectionsList.get(connectionsList.size()-1).getPrintWriter().println("oldFag::" + connectionsList.get(i).getConnectionId() + "::"
                        + connectionsList.get(i).getPosX() + "::" + connectionsList.get(i).getPosY() + "::");
            }
        }
    }

    @Override
    public void run() {
        while(scanner.hasNextLine()){
            while (scanner.hasNext()) {
                String command = scanner.next();
                switch (command) {
                    case "coords" :
                        int x = scanner.nextInt();
                        int y = scanner.nextInt();
                        updateMyPos(x, y);
                        break;
                    case "giveMeCoords" :
                        giveCoordsToPlayer();
                        break;
                    case "giveMeWorld" :
                        giveWorldToPlayer();
                        break;
                    case "giveMePlayersAndSendMeToPlayers" :
                        spawnNewInAll(connectionsList.get((connectionsList.size()-1)).getConnectionId());
                        spawnOldInNew(connectionsList.get((connectionsList.size()-1)).getConnectionId());
                        break;
                    default:
                        break;
                }
                scanner.nextLine();
                break;
            }
        }
    }
}
