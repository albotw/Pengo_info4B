package com.generic.gameplay.v2;

import com.generic.AI.AI;
import com.generic.core.*;
import com.generic.core.blocks.IceBlock;
import com.generic.core.entities.Animal;
import com.generic.core.entities.MapEntity;
import com.generic.core.entities.Penguin;
import com.generic.gameplay.AbstractPlayer;
import com.generic.gameplay.GameMap;
import com.generic.gameplay.events.BotLostEvent;
import com.generic.gameplay.events.ThreadID;

import java.util.HashMap;

import static com.generic.gameplay.config.CONFIG.GRID_HEIGHT;
import static com.generic.gameplay.config.CONFIG.GRID_WIDTH;
import static com.generic.gameplay.config.CONFIG_GAME.*;
import static com.generic.utils.Equations.RandomizedInt;
import static java.lang.Thread.sleep;

public class LocalGameplay implements Gameplay{
    private GameMap map = GameController.instance.map;
    private HashMap<MapEntity, AI> bots;

    private int botLives = AI_INIT_LIVES;

    public LocalGameplay()
    {
        bots = new HashMap<MapEntity, AI>();
    }

    // wallOffset -> 1 par défaut, 2 pour les DiamondBlocks
    public int[] getEmptyCell(int wallOffset, boolean spawnOnIceBlock)
    {
        int[] coords = new int[2];
        boolean loop = true;
        do {
            int x = RandomizedInt(0, GRID_WIDTH - wallOffset);
            int y = RandomizedInt(0, GRID_HEIGHT - wallOffset);

            if ((map.getAt(x, y) != null && !spawnOnIceBlock)
            || (map.getAt(x, y) instanceof IceBlock) && spawnOnIceBlock)
            {
                loop = false;
                coords[0] = x;
                coords[1] = y;
            }

        }while(loop);

        return coords;
    }

    @Override
    public void initDiamondBlocks() {

    }

    @Override
    public void checkDiamondBlocks() {

    }

    @Override
    public void initPlayers() {

    }

    @Override
    public void initIA() {
        for (int i = 0; i < N_AI; i++)
        {
            int[] position = getEmptyCell(1, false);
            AI bot = new AI(ThreadID.AI_1);

            MapEntity controlledEntity = null;
            if (PLAYER_IS_PENGUIN)
            {
                controlledEntity = MapObjectFactory.createAnimal(position[0], position[1], this.map);
            }
            else if (PLAYER_IS_ANIMAL)
            {
                controlledEntity = MapObjectFactory.createPenguin(position[0], position[1], this.map);
            }

            bot.setControlledObject(controlledEntity);
            bots.put(controlledEntity, bot);
            bot.setTarget(null); //TODO: déplacer ailleurs ?
        }
    }

    //TODO: faire interface MapObjectController pour AI et Player
    @Override
    public void respawnAnimal(MapObjectController owner) {
        int[] coords = getEmptyCell(1, true);
        Animal a = MapObjectFactory.createAnimal(coords[0], coords[1], this.map);
        owner.setControlledObject(a);
        //AIs.put(a, bot);
    }

    @Override
    public void respawnPenguin(MapObjectController owner) {
        try{
            sleep(500);
        }catch(Exception e) { e.printStackTrace(); }

        int[] coords = getEmptyCell(1, false);
        Penguin p = MapObjectFactory.createPenguin(coords[0], coords[1], this.map);
        owner.setControlledObject(p);
    }

    @Override
    public void onAnimalKilled(MapObjectController owner, MapObjectController killer) {
        if (PLAYER_IS_PENGUIN)
        {
            owner.setControlledObject(null);
            ((AbstractPlayer)killer).addKillPoints();

            botLives -= 1;
            if (botLives == 0)
            {
                GameController.publish(new BotLostEvent(ThreadID.Game), ThreadID.Game);
            }
            else
            {
                respawnAnimal(owner);
            }
        }
        else if (PLAYER_IS_ANIMAL)
        {
            owner.setControlledObject(null);
            ((AbstractPlayer)killer).removeLive();
        }
    }

    @Override
    public void onPenguinKilled(MapObjectController owner, MapObjectController killer) {
        owner.setControlledObject(null);
        if (PLAYER_IS_PENGUIN)
        {
            ((AbstractPlayer) owner).removeLive();
        }else if (PLAYER_IS_ANIMAL)
        {
            ((AbstractPlayer) killer).addKillPoints();

            botLives -= 1;
            if (botLives == 0) {
                GameController.publish(new BotLostEvent(ThreadID.Game), ThreadID.Game);
            }
        }
    }

    @Override
    public void onStunTriggered(char dirMur) {
        //TODO: optimiser en envoyant un message ?
        switch (dirMur) {
            case 'G':
                for (int i = 0; i < GRID_HEIGHT; i++) {
                    MapObject mo = map.getAt(0, i);
                    if (mo instanceof Animal) {
                        ((Animal) (mo)).activateStun();
                    }
                }
                break;

            case 'D':
                for (int i = 0; i < GRID_HEIGHT; i++) {
                    MapObject mo = map.getAt(GRID_WIDTH - 1, i);
                    if (mo instanceof Animal) {
                        ((Animal) (mo)).activateStun();
                    }
                }
                break;

            case 'H':
                for (int i = 0; i < GRID_WIDTH; i++) {
                    MapObject mo = map.getAt(i, 0);
                    if (mo instanceof Animal) {
                        ((Animal) (mo)).activateStun();
                    }
                }
                break;

            case 'B':
                for (int i = 0; i < GRID_WIDTH; i++) {
                    MapObject mo = map.getAt(i, GRID_HEIGHT - 1);
                    if (mo instanceof Animal) {
                        ((Animal) (mo)).activateStun();
                    }
                }
                break;
        }
    }

    @Override
    public void stop() {

    }

    @Override
    public void clean() {

    }
}
