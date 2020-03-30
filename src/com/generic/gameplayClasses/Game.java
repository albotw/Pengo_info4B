package com.generic.gameplayClasses;

import com.generic.coreClasses.*;
import com.generic.player.*;
import com.generic.graphics.*;
import com.generic.AI.*;
import com.generic.utils.CONFIG;

import java.util.HashMap;


public class Game {
    public static Game instance;
    public static Map m;
    public static HashMap<MapEntity, PlayerThread> players;
    public static HashMap<MapEntity, AIThread> AIs;

    private RenderThread renderer;
    private Window w;

    public Game()
    {
        //! ATTENTION METHODE LONGUE.
        instance = this;

        //initialisation composantes jeu ici.

        w = new Window(CONFIG.WINDOW_WIDTH, CONFIG.WINDOW_HEIGHT);
        renderer = new RenderThread(w);

        players = new HashMap<MapEntity, PlayerThread>();
        AIs = new HashMap<MapEntity, AIThread>();

        //lorsque tous les éléments sont instanciés
        //==> start()
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
        //méthode appelée quand un pingouin est façe au mur et appelle son action.
        //vérifie les X = 0 | X = GRID_MAX, Y = 0 | y = GRID_MAX pour trouver des animaux.
        //si animal il y a alors a.activateStun();
        //GESTION TIMER A DETERMINER.
    }

    public void penguinKilled(Penguin p)
    {
        //methode appellee quand un pingouin meurt
        renderer.removeFromRenderPile(p.getSpr());  //on supprime le sprite du rendu
        PlayerThread owner = players.get(p);    //on recupere le joueur qui le controle
        owner.removeLive();     //on appelle la methode pour retirer une vie au joueur
    }

    public void iceBlockDestroyed(IceBlock ib)
    {
        //methode appellée quand un bloc est détruit (par un animal)
        m.release(ib.getX(), ib.getY());    //supprime le bloc de la map
        renderer.removeFromRenderPile(ib.getSpr());     //supprime le sprite du rendu
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
}
