package com.generic.gameplay;

import com.generic.UI.GameEndDialog;
import com.generic.core.*;
import com.generic.graphics.RenderThread;
import com.generic.graphics.SpriteManager;
import com.generic.launcher.Launcher;
import com.generic.net.multiplayer.NetworkManager;
import com.generic.player.InputHandler;

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

    public OnlineClient(NetworkManager nm) {
        setOnlineMode(true);
        instance = this;

        this.nm = nm;
        this.sm = SpriteManager.createSpriteManager();

        this.rt = new RenderThread();
        rt.start();

        this.m = new GameMap(GRID_WIDTH, GRID_HEIGHT);
        start();
    }

    public void run() {
        this.ih = new InputHandler(rt.getWindow());
        while (true) {
            if (ih.UP)
                nm.UP();
            else if (ih.DOWN)
                nm.DOWN();
            else if (ih.LEFT)
                nm.LEFT();
            else if (ih.RIGHT)
                nm.RIGHT();

            ih.flush();

            try {
                sleep(16);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public GameMap getMap() {
        return this.m;
    }

    public void overrideMap(int x, int y, String type) {
        switch (type) {
            case "IceBlock":
                m.place(new IceBlock(x, y), x, y);
                break;

            case "DiamondBlock":
                m.place(new DiamondBlock(x, y), x, y);
                break;

            case "Penguin":
                m.place(new Penguin(x, y), x, y);
                break;

            case "Animal":
                m.place(new Animal(x, y), x, y);
                break;

            case "":
                m.release(x, y);
                break;
        }
    }
    public void gameEnd(String[] tab){
        GameEndDialog GED = new GameEndDialog(rt.getWindow(), false, false);
        try{
            sleep(2000);
        }catch(Exception e){
            e.printStackTrace();
        }
        GED.Fermer();
        Launcher.instance.onGameEnded();

    }
}
