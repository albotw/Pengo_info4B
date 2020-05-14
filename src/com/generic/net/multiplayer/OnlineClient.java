package com.generic.net.multiplayer;

import com.generic.UI.GameEndDialog;
import com.generic.UI.GameOverlay;
import com.generic.core.*;
import com.generic.graphics.RenderThread;
import com.generic.graphics.SpriteManager;
import com.generic.launcher.Launcher;
import com.generic.utils.InputHandler;

import static com.generic.gameplay.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.CONFIG.GRID_WIDTH;
import static com.generic.gameplay.CONFIG_GAME.*;

public class OnlineClient extends Thread {
    private NetworkManager nm;
    private InputHandler ih;
    private RenderThread rt;
    private GameMap m;
    private SpriteManager sm;

    public static OnlineClient instance;

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
        this.ih = new InputHandler(rt.getWindow());
        while (!stopClient) {
            if (ih.UP)
                nm.UP();
            else if (ih.DOWN)
                nm.DOWN();
            else if (ih.LEFT)
                nm.LEFT();
            else if (ih.RIGHT)
                nm.RIGHT();

            if (ih.ACTION)
                nm.ACTION();

            ih.flush();

            try {
                sleep(16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void updateUI(String[] params)
    {
        System.out.println("UI UPDATE RECEIVED ###");
        GameOverlay go = rt.getGameOverlay();
        if (params[0].equals("POINTS"))
        {
            go.setScore(Integer.parseInt(params[1]));
        }
        else if (params[0].equals("VIES"))
        {
            go.setVies(Integer.parseInt(params[1]));
        }
        else if (params[0].equals("HIDE"))
        {
            if (params[1].equals("ENEMI"))
            {
                go.setShowRemainingEnemies(false);
            }
        }
        else if (params[0].equals("ENEMI"))
        {
            go.setRemainigEnemies(Integer.parseInt(params[1]));
        }
        else if (params[0].equals("PSEUDO"))
        {
            go.setPseudo(params[1]);
        }
    }

    public GameMap getMap() {
        return this.m;
    }

    public void overrideMap(String[] params) {
        System.out.println("OVERRIDE MAP ### | " + params[2]);
        if (params[2].equals(""))
        {
            m.release(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
        }
        else
        {
            int x= Integer.parseInt(params[0]);
            int y= Integer.parseInt(params[1]);
            if (params.length == 4) //l'objet possède une orientation
            {
                MapObject tmp = MapObjectFactory.createPlaceholder(x, y, m, params[2], params[3]);
                m.place(tmp, x, y);
            }
            else
            {
                MapObject tmp = MapObjectFactory.createPlaceholder(x, y, m, params[2], "");
                m.place(tmp, x, y);
            }
        }
    }

    public void gameEnd(String[] params) {
        stopClient = true;
        boolean victoire = false;
        if (params[0].equals("VICTORY"))
            victoire = true;
        GameEndDialog GED = new GameEndDialog(rt.getWindow(), false, victoire);
        try {
            sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GED.Fermer();
        nm.sendCommand("DISCONNECT", null);
        rt.stopRendering();
        ih.flush();

        Launcher.instance.onGameEnded();

    }
}
