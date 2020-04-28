package com.generic.gameplay;

import com.generic.core.*;
import com.generic.graphics.Window;
import com.generic.player.*;
import com.generic.graphics.*;
import com.generic.AI.*;
import com.generic.utils.CONFIG;

import java.util.HashMap;

import static com.generic.utils.CONFIG.GRID_HEIGHT;
import static com.generic.utils.CONFIG.GRID_WIDTH;
import static com.generic.utils.Equations.RandomizedInt;


public class Game {
    public static Game instance;
    private Map m;
    private HashMap<MapEntity, Player> players;
    private HashMap<MapEntity, AI> AIs;

    private RenderThread renderer;
    private SpriteManager sm;
    private Window w;

    private Player p1;

    private GameTimer time;

    public Game()
    {

        instance = this;

        w = new Window(CONFIG.WINDOW_WIDTH,  CONFIG.WINDOW_HEIGHT);
        sm = SpriteManager.createSpriteManager();
        renderer = new RenderThread(w);

        renderer.start();

        players = new HashMap<MapEntity, Player>();
        AIs = new HashMap<MapEntity, AI>();
        m = Map.createMap(GRID_WIDTH, GRID_HEIGHT);
        m = MapGenerator.generate();

        p1 = new Player();

        time = new GameTimer();

        time.start();
        initDiamondBlocks();
        initPlayers();
        initIA();
    }

    public void initDiamondBlocks()
    {
        boolean loop = true;
        int cpt = 0;
        for(int k = 0; k<3; k++){
            loop = true;
            do {
                int initX = RandomizedInt(1, GRID_WIDTH - 2);
                int initY = RandomizedInt(1, GRID_HEIGHT - 2);

                if (m.getAt(initX, initY) == null) {
                    loop = false;
                    DiamondBlock d = new DiamondBlock(initX, initY);
                    m.place(d, initX, initY);
                }
            } while (loop && cpt != 3);
        }
    }

    public void initIA()
    {
        // A ADAPTER POUR PLUSIEURS JOUEURS ET CONTEXTES
        boolean loop = true;
        for (int i = 0; i < 1; i++)
        {
            loop = true;
            do
            {
                int initX = RandomizedInt(0, GRID_WIDTH - 1);
                int initY = RandomizedInt(0, GRID_HEIGHT - 1);

                if (m.getAt(initX, initY) == null)
                {
                    loop = false;
                    Animal a = new Animal(initX, initY);
                    m.place(a, initX, initY);
                    AI ai = new AI();
                    ai.setControlledObject(a);
                    ai.setTarget(p1.getControlledObject());
                    ai.start();

                    AIs.put(a, ai);
                }
            }while(loop);
        }
    }

    public void initPlayers()
    {
        //A ADAPTER POUR PLUSIEURS JOUEURS ET CONTEXTES
        boolean loop = true;
        do
        {
            int initX = RandomizedInt(0, GRID_WIDTH - 1);
            int initY = RandomizedInt(0, GRID_HEIGHT - 1);


            if (m.getAt(initX, initY) == null)
            {
                loop = false;
                Penguin p = new Penguin(initX, initY);
                m.place(p, initX, initY);
                p1.setControlledObject(p);
                p1.start();

                players.put(p, p1);
            }
        }while(loop);
    }

    public void gameOver()
    {
        //a ajouter: déréférencement dans les objets
        AIs.clear();
        players.clear();
        Map.deleteMap();
        System.out.println("Score");
        System.out.println("GAME OVER");
        //supprime le plateau
        //supprime toutes les instances de tous les objets
        //sauf le rendu et le renderThread
        //affiche le score
        //affiche le texte Game Over
        //propose de rejouer ==> reset()
        //sinon ==> quit()
    }

    public void victory()
    {
        //a ajouter: déréférencement dans les objets.
        AIs.clear();
        players.clear();
        Map.deleteMap();
        System.out.println("Score");
        System.out.println("VICTOIRE");

        //supprime le plateau
        //supprime toutes les instances de tous les objets
        //sauf le rendu et le renderThread
        //affiche le score
        //affiche le texte de victoire
        //==> init(2);
    }

    public void animalKilled(Animal a)
    {
        AI owner = AIs.get(a);
        owner.setControlledObject(null);
        System.out.println("Animal Tué");
        //methode appellee quand un animal meurt.
        //verifie qu'il reste des animaux
        //si c'est le cas ==> respawnAnimal()
        //sinon ==> victory()
    }

    public void checkDiamondBlocks()
    {
        // OPTI ?
        Map m = Game.instance.getMap();
        for (int i = 0; i < GRID_WIDTH; i++) {
            for (int j = 0; j < GRID_HEIGHT; j++) {
                MapObject tmp = m.getAt(i, j);

                if ( m.getAt(i,j) != null)
                {
                    if (m.getAt(i, j).getType().equals("DiamondBlock"))
                    {
                        if (m.getAt(i + 1, j) != null && m.getAt(i + 2, j) != null)
                        {
                            if (((m.getAt(i + 1, j).getType().equals("DiamondBlock") && m.getAt(i + 2, j).getType().equals("DiamondBlock"))))
                            {
                                victory();
                            }
                        }
                        if (m.getAt(i, j + 1) != null && m.getAt(i, j + 2) != null)
                        {
                            if (((m.getAt(i, j + 1).getType().equals("DiamondBlock") && m.getAt(i, j + 2).getType().equals("DiamondBlock"))))
                            {
                                victory();
                            }

                        }

                    }
                }
            }
        }
    }

    public void stunTriggered(char dirMur)
    {
        // OPTI ?
        System.out.println("STUN!");
        //méthode appelée quand un pingouin est façe au mur et appelle son action.
        //vérifie les X = 0 | X = GRID_MAX, Y = 0 | y = GRID_MAX pour trouver des animaux.
        //si animal il y a alors a.activateStun();
        //GESTION TIMER A DETERMINER.

        if (dirMur == 'G')      //x == 0
        {
            for (int i = 0; i < GRID_HEIGHT; i++)
            {
                MapObject mo = m.getAt(0, i);
                if (mo != null)
                {
                    if (mo.getType().equals("Animal"))
                    {
                        ((Animal)(mo)).activateStun();
                    }
                }
            }
        }
        else if (dirMur == 'D')     // x == GRID_WIDTH - 1
        {
            for (int i = 0; i < GRID_HEIGHT; i++)
            {
                MapObject mo = m.getAt(GRID_WIDTH - 1, i);
                if (mo != null)
                {
                    if (mo.getType().equals("Animal"))
                    {
                        ((Animal)(mo)).activateStun();
                    }
                }
            }
        }
        else if (dirMur == 'H')     //y == 0
        {
            for (int i = 0; i < GRID_WIDTH; i++)
            {
                MapObject mo = m.getAt(i, 0);
                if (mo != null)
                {
                    if (mo.getType().equals("Animal"))
                    {
                        ((Animal)(mo)).activateStun();
                    }
                }
            }
        }
        else if (dirMur == 'B')     //y == GRID_HEIGHT - 1
        {
            for (int i = 0; i < GRID_WIDTH; i++)
            {
                MapObject mo = m.getAt(i, GRID_HEIGHT - 1);
                if (mo != null)
                {
                    if (mo.getType().equals("Animal"))
                    {
                        ((Animal)(mo)).activateStun();
                    }
                }
            }
        }
    }

    public void penguinKilled(Penguin p)
    {
        System.out.println("Pingouin tué");
        Player owner = players.get(p);
        owner.setControlledObject(null);
        owner.removeLive();
        players.remove(p, owner);
    }
    public void respawnAnimal()
    {
        boolean loop = true;
        do {

            int initX = RandomizedInt(0, GRID_WIDTH - 1);
            int initY = RandomizedInt(0, GRID_HEIGHT - 1);


            if (m.getAt(initX, initY).getType().equals("IceBlock"))
            {
                loop = false;
                Animal a = new Animal(initX, initY);
                m.place(a, initX, initY);
                AI ai = new AI();
                ai.setControlledObject(a);
                ai.setTarget(p1.getControlledObject());
                ai.start();
                AIs.put(a, ai);
                }

        }while (loop);
        //methode appellée quand un animal est mort
        //prend un bloc de glace au hasard
        //le detruit
        //cree une instance intermediaire d'animal
        //genere un timer
        //si au bout de X secondes l'instance intermediare n'est pas détruite
        //un nouvel animal est crée
        //son sprite est envoyé au rendu
        //et il est liée à une IA.
    }

    public void respawnPenguin(Player owner)
    {
        boolean loop = true;
        do {
            int initX = RandomizedInt(0, GRID_WIDTH - 1);
            int initY = RandomizedInt(0, GRID_HEIGHT - 1);


            if (m.getAt(initX, initY) == null)
            {
                loop = false;
                Penguin p = new Penguin(initX, initY);
                m.place(p, initX, initY);
                owner.setControlledObject(p);

                players.put(p, owner);
            }
        }while (loop);

        //methode appellée si le joueur est mort et si il lui reste des vies.
        //prend un espace vide de la map
        //cree une instance de penguin
        //envoie son sprite au rendu
        //l'associe au joueur
    }


    public Map getMap()
    {
        return this.m;
    }

    public RenderThread getRenderer()
    {
        return this.renderer;
    }

    public SpriteManager getSpriteManager()
    {
        return this.sm;
    }

    public Window getWindow()
    {
        return this.w;
    }

}
