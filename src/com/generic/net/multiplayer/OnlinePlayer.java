package com.generic.net.multiplayer;

import com.generic.core.MapEntity;
import com.generic.gameplay.AbstractPlayer;
import com.generic.net.Command;

import java.io.*;
import java.net.Socket;

import static com.generic.gameplay.CONFIG_GAME.TEAM_1_IS_ANIMAL;
import static com.generic.gameplay.CONFIG_GAME.TEAM_2_IS_ANIMAL;


/**
 * TODO: refactor -> OnlinePlayer
 */
public class OnlinePlayer extends AbstractPlayer {
    private Socket socket;
    private ObjectOutputStream commandOut;
    private ObjectInputStream commandIn;

    private int equipe = 0;

    private Serveur srv = Serveur.instance;

    public OnlinePlayer(Socket s) {
        super("");
        socket = s;
        System.out.println("CONNEXION [SERVER] => " + s.toString());
        try {
            commandOut = new ObjectOutputStream(s.getOutputStream());
            commandIn = new ObjectInputStream(s.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            boolean loop = true;
            while (loop) {
                Command cmd = (Command) (commandIn.readObject());
                System.out.println("SERVER | " + cmd.toString());

                if (cmd.getVal().equals("DISCONNECT")) {
                    commandOut.writeObject(new Command("DISCONNECT", null));
                    if (equipe == 1)
                        srv.removeFromTeam1(this);
                    else if (equipe == 2)
                        srv.removeFromTeam2(this);

                    srv.removePlayer(this.commandOut);
                    loop = false;
                } else if (cmd.getVal().equals("SET PSEUDO")) {
                    this.pseudo = cmd.getParam(0);
                } else if (cmd.getVal().equals("JOIN TEAM 1")) {
                    if (equipe == 2) {
                        srv.removeFromTeam2(this);
                    }
                    srv.putOnTeam1(this, pseudo);
                    this.equipe = 1;
                } else if (cmd.getVal().equals("JOIN TEAM 2")) {
                    if (equipe == 1) {
                        srv.removeFromTeam1(this);
                    }
                    srv.putOnTeam2(this, pseudo);
                    this.equipe = 2;
                } else if (cmd.getVal().equals("SET HOST")) {
                    srv.setHost(this);
                } else if (cmd.getVal().equals("START GAME")) {
                    srv.startGame();
                } else if (cmd.getVal().equals("MOVE UP")) {
                    if (controlledObject != null)
                        controlledObject.goUp();
                } else if (cmd.getVal().equals("MOVE DOWN")) {
                    if (controlledObject != null)
                        controlledObject.goDown();
                } else if (cmd.getVal().equals("MOVE LEFT")) {
                    if (controlledObject != null)
                        controlledObject.goLeft();
                } else if (cmd.getVal().equals("MOVE RIGHT")) {
                    if (controlledObject != null)
                        controlledObject.goRight();
                }
                else if (cmd.getVal().equals("ACTION"))
                {
                    if (controlledObject != null)
                    {
                        controlledObject.action();
                    }
                }
            }
            commandOut.close();
            commandIn.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeLive()
    {
        currentLives--;
        if (currentLives <= 0)
        {
            Serveur.instance.getGame().playerKilled(this);
        }
        else
        {
            if (equipe == 1 && TEAM_1_IS_ANIMAL)
            {
                OnlineGame.instance.respawnAnimal(this);
            }
            else if (equipe == 1 && TEAM_2_IS_ANIMAL)
            {
                OnlineGame.instance.respawnPenguin(this);
            }
            else if (equipe == 2 && TEAM_1_IS_ANIMAL)
            {
                OnlineGame.instance.respawnPenguin(this);
            }
            else if (equipe == 2 && TEAM_2_IS_ANIMAL)
            {
                OnlineGame.instance.respawnAnimal(this);
            }
        }
    }

    public int getEquipe(){
        return this.equipe;
    }

    public ObjectOutputStream getCommandOut() {
        return this.commandOut;
    }
}
