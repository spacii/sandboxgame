package com.company.Game;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocket implements Runnable {

    private Thread thread;
    private Socket socket;
    private Scanner scanner;
    private static PrintWriter printWriter;
    private static int ClientID;
    private GameManager gameManager;
    ClientSocket(GameManager gameManager){
        thread = new Thread(this);
        this.gameManager = gameManager;
    }

    public Thread getThread() {
        return thread;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public int getClientID() {
        return ClientID;
    }

    public void setClientID(int clientID) {
        ClientID = clientID;
    }



    @Override
    public void run() {
        try{
            socket = new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 8189), 2000);

            scanner = new Scanner(socket.getInputStream());
            scanner.useDelimiter("::");
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            while(scanner.hasNextLine()){
                while(scanner.hasNext()){
                    String command = scanner.next();
                    switch(command){
                        case "generatedID" :
                            int id = scanner.nextInt();
                            setClientID(id);
                            break;
                        case "otherPlayerCoords" :
                            int idOther = Integer.parseInt(scanner.next());
                            int x = Integer.parseInt(scanner.next());
                            int y = Integer.parseInt(scanner.next());
                            gameManager.updateThisId(idOther, x, y);
                            break;
                        case "newConnected" :
                            int newId = scanner.nextInt();
                            gameManager.spawnNew(newId);
                            break;
                        case "oldFag" :
                            int oldFagId = scanner.nextInt();
                            System.out.println(oldFagId);
                            int XXX = scanner.nextInt();
                            System.out.println(XXX);
                            int YYY = scanner.nextInt();
                            System.out.println(YYY);
                            gameManager.spawnOldFag(oldFagId, XXX, YYY);
                            break;
                        default:
                            break;
                    }
                    scanner.nextLine();
                    break;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
