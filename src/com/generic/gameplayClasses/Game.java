package com.generic.gameplayClasses;

import com.generic.coreClasses.*;
import com.generic.player.*;
import com.generic.graphics.*;
import com.generic.AI.*;
import com.generic.utils.*;

import java.util.ArrayList;


public class Game {
    public static Map m;
    public static ArrayList<PlayerThread> players;
    public static ArrayList<AI> AIs;

    private RenderThread renderer;
    private Window w;

    public static void reset(){}

    public static void start(){}

    public static void gameOver(){}

    public static void victory(){}

    public static void initGame(){}

    public static void animalKilled(Animal a)
    {

    }

    public static void checkDiamondBlocks()
    {

    }

    public static void stunTriggered()
    {

    }

    public static void penguinKilled(Penguin p)
    {
        for(PlayerThread player : players)
        {
            if (player.getControlledObject().equals(p))
            {
                player.removeLive();
            }
        }
    }

    public static void iceBlockDestroyed(IceBlock ib)
    {

    }

    public static void respawnAnimal()
    {

    }
}
