package com.generic.gameplayClasses;

import com.generic.coreClasses.*;
import com.generic.graphics.Window;
import com.generic.player.*;
import com.generic.graphics.*;
import com.generic.AI.*;
import com.generic.utils.CONFIG;

import java.awt.*;
import java.util.HashMap;

import static com.generic.utils.CONFIG.GRID_HEIGHT;
import static com.generic.utils.CONFIG.GRID_WIDTH;
import static com.generic.utils.Equations.RandomizedInt;


public class Game {
    public static Game instance;
    private Map m;
    private HashMap<MapEntity, Player> players;
    private HashMap<MapEntity, AIThread> AIs;

    private RenderThread renderer;
    private SpriteManager sm;
    private Window w;

    private Player p1;
    private AIThread ai1;

    public Game()
    {

        instance = this;

        w = new Window(CONFIG.WINDOW_WIDTH,  CONFIG.WINDOW_HEIGHT);
        sm = SpriteManager.createSpriteManager();
        renderer = new RenderThread(w);

        renderer.start();

        players = new HashMap<MapEntity, Player>();
        AIs = new HashMap<MapEntity, AIThread>();
        m = Map.createMap(GRID_WIDTH, GRID_HEIGHT);
        m = MapGenerator.generate();

        p1 = new Player();
        ai1 = new AIThread();

        boolean loop = true;
        do
        {
            int initX = RandomizedInt(0, GRID_WIDTH -1);
            int initY = RandomizedInt(0, GRID_HEIGHT - 1);


            if (m.getAt(initX, initY) == null)
            {
                loop = false;
                Penguin p = new Penguin(initX, initY);
                m.place(p, initX, initY);
                p1.setControlledObject(p);
            }
        }while(loop);

<<<<<<< HEAD
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
                ai1.setControlledObject(a);
                ai1.setTarget(p1.getControlledObject());
            }
        }while(loop);
=======
        int cpt = 0;
        for(int k = 0; k<3; k++){
            loop = true;
            do {
                int initX = RandomizedInt(1, GRID_WIDTH - 2);
                int initY = RandomizedInt(1, GRID_HEIGHT - 2);

                if (m.getAt(initX, initY) == null) {
                    loop = false;
                    DiamondBlock d1 = new DiamondBlock(initX, initY);
                    m.place(d1, initX, initY);
                }
            } while (loop && cpt != 3);
        }
>>>>>>> master
        gameplayLoop();
        //lorsque tous les éléments sont instanciés
        //==> start()
    }

    private void gameplayLoop()
    {
        while (true)
        {
            p1.linkInput();
            ai1.selectMovement();
            try{
                Thread.currentThread().sleep(100);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            System.out.println("------------");
        }
    }

    public void reset(){}

    public void start()
    {
        //active le thread de rendu
        //active les thread des joueurs
        //active les thread des IAs
    }

    public void gameOver()
    {
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
        //supprime le plateau
        //supprime toutes les instances de tous les objets
        //sauf le rendu et le renderThread
        //affiche le score
        //affiche le texte de victoire
        //==> init(2);
    }

    public void animalKilled(Animal a)
    {
        //methode appellee quand un animal meurt.
        //verifie qu'il reste des animaux
        //si c'est le cas ==> respawnAnimal()
        //sinon ==> victory()
    }

    public void checkDiamondBlocks()
    {
        //methode appellée quand un bloc de diamant est déplacé.
        //parcourt la map pour trouver le bloc de diamant. ==> LOCK ICI
        //on prend le 1er bloc trouvé et on teste x + 1, x + 2 || y + 1, y + 2
        //si le test est validé, ==>victory()
        //sinon rien ne se passe.
    }

    public void stunTriggered()
    {
        System.out.println("STUN!");
        //méthode appelée quand un pingouin est façe au mur et appelle son action.
        //vérifie les X = 0 | X = GRID_MAX, Y = 0 | y = GRID_MAX pour trouver des animaux.
        //si animal il y a alors a.activateStun();
        //GESTION TIMER A DETERMINER.
    }

    public void penguinKilled(Penguin p)
    {
        //methode appellee quand un pingouin meurt
        //renderer.removeFromRenderPile(p.getSpr());  //on supprime le sprite du rendu
        Player owner = players.get(p);    //on recupere le joueur qui le controle
        owner.removeLive();     //on appelle la methode pour retirer une vie au joueur
    }

    public void respawnAnimal()
    {
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

    public void respawnPenguin()
    {
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
