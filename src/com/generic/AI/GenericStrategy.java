package com.generic.AI;

import com.generic.core.GameMap;
import com.generic.core.MapObject;
import com.generic.gameplay.LocalGame;

import static com.generic.gameplay.CONFIG.*;
import static com.generic.utils.Equations.RandomizedInt;
import static com.generic.utils.Equations.VectorialDistance;

public class GenericStrategy implements Strategy{
    private MapObject target;
    private AI bot;
    private MapObject co;
    private char bannedDir;

    public GenericStrategy(AI bot)
    {
        this.co = bot.getControlledObject();
        this.bot = bot;
    }

    public void process()
    {
        if (target == null)
        {
            target = acquireTarget();
        }
        MoveToTarget();
        System.out.println("process");
    }

    public MapObject acquireTarget()
    {
        int x = 0;
        int y = 0;
        MapObject output = null;
        boolean loop = true;
        do {
            MapObject tmp = LocalGame.instance.getMap().getAt(x, y);
            if (tmp != null)
            {
                if (tmp.getType().equals("Penguin"))
                {
                    output = tmp;
                    loop = false;
                }
            }
            else
            {
                if (x != GRID_WIDTH) x++;
                else
                {
                    x = 0;
                    y++;
                }
            }
        }while(y != GRID_HEIGHT && loop);

        return output;
    }

    public void updateCo(MapObject co)
    {
        bannedDir = '\0';
        this.co = co;
    }

    public void MoveToTarget()
    {
        /**
         * Fonctionnement séléction de mouvement
         * (version vraiment allégée de l'algorithme a*)
         * on récupère les coords. de l'objet controllé
         * on teste les cases qui lui sont adjacentes (nulles et dans les bords de la map)
         * pour chaque case potentielle on va calculer sa distance vectorielle avec la cible
         * on prend la case avec la plus petite valeur et on appelle la méthode du mouvement associé.
         * pour éviter tout état indécisif, on fait un tirage aléatoire pour débloquer le système.
         *
         * TODO: rendre le système plus fonctionnel en évitant les allers retours
         */
        int x = co.getX();
        int y = co.getY();

        GameMap m = LocalGame.instance.getMap();

        double d_up = INFINI;
        double d_down = INFINI;
        double d_left = INFINI;
        double d_right = INFINI;

        //test et mesure case a gauche
        if (x > 0)
        {
            if (m.getAt(x - 1, y) == null) d_left = VectorialDistance(x - 1, target.getX(), y, target.getY());
            else if (m.getAt(x - 1, y).equals(target)) d_left = VectorialDistance(x - 1, target.getX(), y, target.getY());
        }

        //test et mesure case a droite
        if (x < GRID_WIDTH - 1)
        {
            if (m.getAt(x + 1, y) == null) d_right = VectorialDistance(x + 1, target.getX(), y, target.getY());
            else if (m.getAt(x + 1, y).equals(target)) d_right = VectorialDistance(x + 1, target.getX(), y, target.getY());
        }

        //test et mesure case au dessus
        if (y > 0)
        {
            if(m.getAt(x, y - 1) == null) d_up = VectorialDistance(x, target.getX(), y - 1, target.getY());
            else if (m.getAt(x, y - 1).equals(target)) d_up = VectorialDistance(x, target.getX(), y - 1, target.getY());
        }

        //test et mesure en dessous
        if (y < GRID_HEIGHT - 1)
        {
            if (m.getAt(x, y + 1) == null) d_down = VectorialDistance(x, target.getX(), y + 1, target.getY());
            else if (m.getAt(x, y + 1).equals(target)) d_down = VectorialDistance(x, target.getX(), y + 1, target.getY());
        }

        //System.out.println("h = " + d_up + " | b = " + d_down + " | g = " + d_left + " | d = " + d_right);

        //on prend la plus petite valeur et on se déplace sur la case
        if (d_up < d_down && d_up < d_left && d_up < d_right && bannedDir != 'H')
        {
            System.out.println("Décide d'aller en haut");
            co.goUp();
            bannedDir = 'B';
        }
        else if (d_down < d_up && d_down < d_left && d_down < d_right && bannedDir != 'B')
        {
            System.out.println("Décide d'aller en bas");
            co.goDown();
            bannedDir = 'H';
        }
        else if (d_left < d_up && d_left < d_right && d_left < d_down && bannedDir != 'G')
        {
            System.out.println("Décide d'aller a gauche");
            co.goLeft();
            bannedDir = 'D';
        }
        else if (d_right < d_up && d_right < d_down && d_right < d_left && bannedDir != 'D')
        {
            System.out.println("Décide d'aller a droite");
            co.goRight();
            bannedDir = 'G';
        }
        else
        {
            System.out.println("état indécisif");

            boolean loop = true;
            do
            {
                if (co == null)
                {
                    loop = false;
                }
                int d_rand = RandomizedInt(0, 3);

                if (d_up != INFINI && d_rand == 0 && bannedDir != 'H')
                {
                    co.goUp();
                    loop = false;
                    bannedDir = 'B';
                    System.out.println("Après tirage décide d'aller en haut");
                }
                else if (d_down != INFINI && d_rand == 1 && bannedDir != 'B')
                {
                    co.goDown();
                    loop = false;
                    bannedDir = 'H';
                    System.out.println("Après tirage décide d'aller en bas");
                }
                else if (d_left != INFINI && d_rand == 2 && bannedDir != 'G')
                {
                    co.goLeft();
                    loop = false;
                    bannedDir = 'D';
                    System.out.println("Après tirage décide d'aller à gauche");
                }
                else if (d_right != INFINI && d_rand == 3 && bannedDir != 'D')
                {
                    co.goRight();
                    loop = false;
                    bannedDir = 'G';
                    System.out.println("Après tirage décide d'aller a droite");
                }
                else
                {
                    System.out.println("Tirage invalide");
                }
            }while(loop);
        }
    }
}
