package com.generic.net.multiplayer;

import com.generic.launcher.OnlineDialog;
import com.generic.net.Command;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Le network manager sert a l'envoi et la réception de commandes. il ne fait
 * PAS les actions liées au commandes, juste l'envoi
 */

public class NetworkManager implements Runnable {
    private Socket socket;
    private int port = 8080;
    private String IP = "127.0.0.1";
    private ObjectInputStream commandIn;
    private ObjectOutputStream commandOut;

    private boolean endConnexion;

    private OnlineDialog manager;

    public NetworkManager(OnlineDialog manager) {
        this.manager = manager;
    }

    public void run() {
        // récup commande et appel des fonctions liées
        try {
            while (!endConnexion) {
                Command cmd = (Command) (commandIn.readObject());
                System.out.println("CLIENT | " + cmd.toString());

                if (cmd.getVal().equals("ADD TO TEAM 1")) {
                    manager.addToTeam1(cmd.getParam(0));
                } else if (cmd.getVal().equals("ADD TO TEAM 2")) {
                    manager.addToTeam2(cmd.getParam(0));
                } else if (cmd.getVal().equals("GAME START")) {
                    manager.gameStart();
                } else if (cmd.getVal().equals("TEAM 1 FULL")) {
                    manager.team1Full();
                } else if (cmd.getVal().equals("TEAM 2 FULL")) {
                    manager.team2Full();
                } else if (cmd.getVal().equals("DISCONNECT")) {
                    endConnexion = true;
                } else if (cmd.getVal().equals("REMOVE TEAM 1")) {
                    manager.removeTeam1(cmd.getParam(0));
                } else if (cmd.getVal().equals("REMOVE TEAM 2")) {
                    manager.removeTeam2(cmd.getParam(0));
                }
            }

            commandOut.close();
            commandIn.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect(String IP, int port) {
        try {
            socket = new Socket(IP, port);
            System.out.println("CONNEXION [CLIENT] =>" + socket.toString());
            commandOut = new ObjectOutputStream(socket.getOutputStream());
            commandIn = new ObjectInputStream(socket.getInputStream());
            endConnexion = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendCommand(String val, String[] params) {
        Command cmd = new Command(val, params);
        try {
            commandOut.writeObject(cmd);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setHost() {
        sendCommand("SET HOST", null);
    }

    public void UP() {
        sendCommand("MOVE UP", null);
    }

    public void DOWN() {
        sendCommand("MOVE DOWN", null);
    }

    public void LEFT() {
        sendCommand("MOVE LEFT", null);
    }

    public void RIGHT() {
        sendCommand("MOVE RIGHT", null);
    }

    public void startGame() {
        sendCommand("START GAME", null);
    }

    public void sendPseudo(String pseudo) {
        sendCommand("SET PSEUDO", new String[] { pseudo });
    }

    public void joinTeam1(String pseudo) {
        sendCommand("JOIN TEAM 1", null);
    }

    public void joinTeam2(String pseudo) {
        sendCommand("JOIN TEAM 2", null);
    }

    public void disconnect() {
        sendCommand("DISCONNECT", null);
        endConnexion = true;
    }
}
