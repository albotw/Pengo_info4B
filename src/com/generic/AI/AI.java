package com.generic.AI;

import com.generic.coreClasses.Map;
import com.generic.coreClasses.MapObject;
import com.generic.gameplayClasses.Game;

import static com.generic.utils.CONFIG.*;
import static com.generic.utils.Random.RandomizedInt;
import static com.generic.utils.Random.VectorialDistance;

public class AI {

    private MapObject target;
    private MapObject controlledObject;

    public AI()
    {

    }

    public void process()
    {
        if (!isNextToTarget())
        {
            MoveToTarget();
        }
    }

    public boolean isNextToTarget()
    {
        Map m = Game.instance.getMap();
        int posX = controlledObject.getX();
        int posY = controlledObject.getY();

        boolean output = false;
        if (m.getAt(posX + 1, posY) != null)
            if (m.getAt(posX + 1, posY).getType().equals("Penguin"))
                output = true;

        if (m.getAt(posX - 1, posY) != null)
            if (m.getAt(posX - 1, posY).getType().equals("Penguin"))
                output = true;

        if (m.getAt(posX, posY + 1) != null)
            if (m.getAt(posX, posY + 1).getType().equals("Penguin"))
                output = true;

        if (m.getAt(posX, posY - 1) != null)
            if (m.getAt(posX, posY - 1).getType().equals("Penguin"))
                output = true;

        return output;
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
         */
        int posX = controlledObject.getX();
        int posY = controlledObject.getY();

        Map m = Game.instance.getMap();

        double d_up = INFINI;
        double d_down = INFINI;
        double d_left = INFINI;
        double d_right = INFINI;

        //test et mesure case a gauche
        if (posX > 0)
        {
            if (m.getAt(posX - 1, posY) == null) d_left = VectorialDistance(posX - 1, target.getX(), posY, target.getY());
        }

        //test et mesure case a droite
        if (posX < GRID_WIDTH - 1)
        {
            if (m.getAt(posX + 1, posY) == null) d_right = VectorialDistance(posX + 1, target.getX(), posY, target.getY());
        }

        //test et mesure case au dessus
        if (posY > 0)
        {
            if(m.getAt(posX, posY - 1) == null) d_up = VectorialDistance(posX, target.getX(), posY - 1, target.getY());
        }

        //test et mesure en dessous
        if (posY < GRID_HEIGHT - 1)
        {
            if (m.getAt(posX, posY + 1) == null) d_down = VectorialDistance(posX, target.getX(), posY + 1, target.getY());
        }

        System.out.println("h = " + d_up + " | b = " + d_down + " | g = " + d_left + " | d = " + d_right);

        //on prend la plus petite valeur et on se déplace sur la case
        if (d_up < d_down && d_up < d_left && d_up < d_right)
        {
            System.out.println("Décide d'aller en haut");
            controlledObject.goUp();
        }
        else if (d_down < d_up && d_down < d_left && d_down < d_right)
        {
            System.out.println("Décide d'aller en bas");
            controlledObject.goDown();
        }
        else if (d_left < d_up && d_left < d_right && d_left < d_down)
        {
            System.out.println("Décide d'aller a gauche");
            controlledObject.goLeft();
        }
        else if (d_right < d_up && d_right < d_down && d_right < d_left)
        {
            System.out.println("Décide d'aller a droite");
            controlledObject.goRight();
        }
        else
        {
            System.out.println("état indécisif");

            boolean loop = true;
            do
            {
                int d_rand = RandomizedInt(0, 3);

                if (d_up != INFINI && d_rand == 0)
                {
                    controlledObject.goUp();
                    loop = false;
                    System.out.println("Après tirage décide d'aller en haut");
                }
                else if (d_down != INFINI && d_rand == 1)
                {
                    controlledObject.goDown();
                    loop = false;
                    System.out.println("Après tirage décide d'aller en bas");
                }
                else if (d_left != INFINI && d_rand == 2)
                {
                    controlledObject.goLeft();
                    loop = false;
                    System.out.println("Après tirage décide d'aller à gauche");
                }
                else if (d_right != INFINI && d_rand == 3)
                {
                    controlledObject.goRight();
                    loop = false;
                    System.out.println("Après tirage décide d'aller a droite");
                }
                else
                {
                    System.out.println("Tirage invalide");
                }
            }while(loop);
        }
    }

    public MapObject getTarget() {
        return target;
    }

    public void setTarget(MapObject target) {
        this.target = target;
    }

    public MapObject getControlledObject() {
        return controlledObject;
    }

    public void setControlledObject(MapObject controlledObject) {
        this.controlledObject = controlledObject;
    }
}


