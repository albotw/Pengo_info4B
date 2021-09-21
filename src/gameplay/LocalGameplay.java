package gameplay;

import Bot.Bot;
import core.MapObject;
import core.MapObjectFactory;
import core.blocks.IceBlock;
import core.entities.Animal;
import core.entities.MapEntity;
import core.entities.Penguin;
import events.Event;
import events.ThreadID;

import java.util.HashMap;

import static config.CONFIG.GRID_HEIGHT;
import static config.CONFIG.GRID_WIDTH;
import static config.CONFIG_GAME.*;
import static java.lang.Thread.sleep;
import static utils.Equations.RandomizedInt;

public class LocalGameplay implements Gameplay{
    private GameMap map = GameController.instance.map;
    private HashMap<MapEntity, Bot> bots;
    private Player p;

    private int botLives = AI_INIT_LIVES;

    public LocalGameplay()
    {
        bots = new HashMap<MapEntity, Bot>();
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
        for (int i = 0; i < 3; i++)
        {
            int[] coords = getEmptyCell(2, true);
            MapObjectFactory.createDiamondBlock(coords[0], coords[1], this.map);
        }
    }

    @Override
    public void checkDiamondBlocks() {
        //TODO
    }

    @Override
    public void initPlayers() {
        int[] position = getEmptyCell(1, false);
        p = new Player();

        MapEntity controlledEntity = null;
        if (PLAYER_IS_PENGUIN)
        {
            controlledEntity = MapObjectFactory.createPenguin(position[0], position[1], this.map);
        }
        else if (PLAYER_IS_ANIMAL)
        {
            controlledEntity = MapObjectFactory.createAnimal(position[0], position[1], this.map);
        }

        p.setControlledObject(controlledEntity);
    }

    @Override
    public void initIA() {
        for (int i = 0; i < N_AI; i++)
        {
            int[] position = getEmptyCell(1, false);
            Bot bot = new Bot(ThreadID.getAvailableBotID());

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
            bot.setTarget(p.getControlledObject()); //TODO: déplacer ailleurs ?
        }
    }

    @Override
    public void respawnAnimal(MapObjectController owner) {
        int[] coords = getEmptyCell(1, true);
        Animal a = MapObjectFactory.createAnimal(coords[0], coords[1], this.map);
        owner.setControlledObject(a);
        //AIs.put(a, bot);
    }

    @Override
    public void respawnPenguin(MapObjectController owner) {
        int[] coords = getEmptyCell(1, false);
        Penguin p = MapObjectFactory.createPenguin(coords[0], coords[1], this.map);
        owner.setControlledObject(p);
    }

    @Override
    public void onAnimalKilled(MapObjectController owner) {
        owner.clearControlledObject();
        if (PLAYER_IS_PENGUIN)
        {
            p.addKillPoints();

            botLives -= 1;
            if (botLives == 0)
            {
                //TODO: faire évènement.
                //GameController.publish(new Event(ThreadID.Game), ThreadID.Game);
            }
            else
            {
                respawnAnimal(owner);
            }
        }
        else if (PLAYER_IS_ANIMAL)
        {
            p.removeLive();
        }
    }

    @Override
    public void onPenguinKilled(MapObjectController owner) {
        owner.clearControlledObject();
        if (PLAYER_IS_PENGUIN)
        {
            p.removeLive();
        }else if (PLAYER_IS_ANIMAL)
        {
            p.addKillPoints();

            botLives -= 1;
            if (botLives == 0) {
                //TODO: modifier event
                GameController.publish(new Event(ThreadID.Game), ThreadID.Game);
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
        map = null;
        p = null;
    }

    @Override
    public void clean() {

    }
}
