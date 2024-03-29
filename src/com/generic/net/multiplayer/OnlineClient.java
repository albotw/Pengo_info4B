package com.generic.net.multiplayer;

import com.generic.UI.GameEndDialog;
import com.generic.UI.GameOverlay;
import com.generic.gameplay.GameMap;
import com.generic.core.MapObjectFactory;
import com.generic.graphics.RenderThread;
import com.generic.graphics.SpriteManager;
import com.generic.launcher.Launcher;

import static com.generic.gameplay.config.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.config.CONFIG.GRID_WIDTH;

public class OnlineClient extends Thread {
    private NetworkManager nm;
    private RenderThread rt;
    private GameMap m;
    private SpriteManager sm;

    public static OnlineClient instance;

    private int points;

    private boolean stopClient;

    public OnlineClient(NetworkManager nm) {
        instance = this;
        Launcher.instance.incrementCurrentLevel();

        this.nm = nm;
        this.sm = SpriteManager.createSpriteManager();

        this.rt = new RenderThread();
        rt.setClient(true);
        rt.start();

        this.m = new GameMap(GRID_WIDTH, GRID_HEIGHT);
        m.setLocal(true);
        start();
    }

    public void run() {
        //TODO: refaire input clavier

            try {
                sleep(16);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void updateUI(String[] params) {
        GameOverlay go = rt.getGameOverlay();
        if (params[0].equals("POINTS")) {
            points = Integer.parseInt(params[1]);
            go.setScore(points);
        } else if (params[0].equals("VIES")) {
            go.setVies(Integer.parseInt(params[1]));
        } else if (params[0].equals("HIDE")) {
            if (params[1].equals("ENEMI")) {
                go.setShowRemainingEnemies(false);
            }
        } else if (params[0].equals("ENEMI")) {
            go.setRemainigEnemies(Integer.parseInt(params[1]));
        } else if (params[0].equals("PSEUDO")) {
            go.setPseudo(params[1]);
        }
    }

    public GameMap getMap() {
        return this.m;
    }

    public void overrideMap(String[] params) {
        if (params[2].equals("")) { //cas de la suppression
            m.release(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
        } else {
            int x = Integer.parseInt(params[0]);
            int y = Integer.parseInt(params[1]);
            if (params.length == 4) //l'objet possède une orientation mais pas de variante (lonk)
            {
                MapObjectFactory.createPlaceholder(x, y, m, params[2], params[3], "");
            }
            else if (params.length == 5) { //cas de la variante avec les animaux
                MapObjectFactory.createPlaceholder(x, y, m, params[2], params[3], params[4]);
            }
            else    //cas de tous les autres blocs
            {
                MapObjectFactory.createPlaceholder(x, y, m, params[2], "", "");
            }
        }
    }

    public void gameEnd(String[] params) {
        stopClient = true;
        boolean victoire = false;
        if (params[0].equals("VICTORY"))
            victoire = true;

        int temps = Integer.parseInt(params[1]);
        GameEndDialog GED = new GameEndDialog(rt.getWindow(), false, victoire, temps, points);
        try {
            sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GED.Fermer();
        nm.sendCommand("DISCONNECT", null);
        rt.stopRendering();

        Launcher.instance.onGameEnded();
    }
}
