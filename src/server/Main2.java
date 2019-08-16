package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main2 {

    static private ArrayList<ServerConnection> connectionsList = new ArrayList<>();
    static private GameWorldServer gameWorldServer;


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

    /*
    static void spawnOldInNew(int id){
        for(int i = 0; i < connectionsList.size(); i++){
            if(connectionsList.get(i).getConnectionId() == id){
                for(int j = 0; j < connectionsList.size(); j++ ){
                    if(connectionsList.get(j).getConnectionId() != id){
                        connectionsList.get(i).getPrintWriter().println("oldFag::"+connectionsList.get(j).getConnectionId()+"::");
                    }
                }
            }
        }
    }
    */

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8189);
            System.out.println("Server started");
            gameWorldServer = new GameWorldServer();
            gameWorldServer.generateWorld();
            System.out.println("World Generated!");

            while (true){
                Socket socket = serverSocket.accept();
                ServerConnection serverConnection = new ServerConnection(socket, connectionsList);
                connectionsList.add(serverConnection);
                connectionsList.get((connectionsList.size()-1)).getThread().start();

                spawnNewInAll(connectionsList.get((connectionsList.size()-1)).getConnectionId());
                spawnOldInNew(connectionsList.get((connectionsList.size()-1)).getConnectionId());

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

    ServerConnection(Socket socket, ArrayList<ServerConnection> connectionsList){
        this.connectionsList = connectionsList;
        this.thread = new Thread(this);
        this.socket = socket;
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
               // System.out.println("otherPlayerCoords::"+ connectionsList.get(i).getConnectionId() + "::" + connectionsList.get(i).posX + "::" + connectionsList.get(i).posY + "::");
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
                    default:
                        break;
                }
                scanner.nextLine();
                break;
            }
        }
    }
}
