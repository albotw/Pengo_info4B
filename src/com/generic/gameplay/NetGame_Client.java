package com.generic.gameplay;

import com.generic.core.*;
import com.generic.graphics.RenderThread;
import com.generic.net.NetworkManager;
import com.generic.net.multiplayer.Serveur;
import com.generic.player.InputHandler;

import static com.generic.gameplay.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.CONFIG.GRID_WIDTH;

public class NetGame_Client extends Thread{
    private NetworkManager nm;
    private InputHandler ih;
    private RenderThread rt;
    private GameMap m;

    public static NetGame_Client instance;

    public NetGame_Client(NetworkManager nm)
    {
        instance = this;

        this.nm = nm;

        this.rt = new RenderThread();

        this.m = GameMap.createMap(GRID_WIDTH, GRID_HEIGHT);
        start();
    }

    public void run()
    {
        this.ih = new InputHandler();
        while(true)
        {
            if (ih.UP) nm.UP();
            else if (ih.DOWN) nm.DOWN();
            else if (ih.LEFT) nm.LEFT();
            else if (ih.RIGHT) nm.RIGHT();
        }
    }

    public GameMap getMap()
    {
        return this.m;
    }

    public void overrideMap(int x, int y, String type)
    {
        switch(type)
        {
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
}
