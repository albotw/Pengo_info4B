package com.generic.AI;

import com.generic.core.Animal;
import com.generic.core.Map;
import com.generic.core.MapObject;
import com.generic.gameplay.Game;

import static com.generic.gameplay.CONFIG.*;
import static com.generic.utils.Equations.RandomizedInt;
import static com.generic.utils.Equations.VectorialDistance;

public class AI extends Thread{

    private MapObject target;
    private MapObject controlledObject;

    private boolean stunActive;     //a déplacer dans la classe de controle spé animal
    private int stunTimer;          //a déplacer dans la classe de controle spé animal (temps de stun, var locale basée sur la constante de CONFIG

    public AI()
    {

    }

    public void run()
    {
        while(true)
        {
            process();

            try
            {
                sleep(AI_TICK_RATE);
            }catch(Exception e) { e.printStackTrace(); }
        }
    }
    public void process()
    {
        if (controlledObject != null)   //si l'animal n'a pas été tué
        {
            checkStun();
            MoveToTarget();
        }
    }

    public void checkStun()
    {
        if (controlledObject.getType().equals("Animal"))
        {
            Animal a = (Animal)(controlledObject);
            if (stunActive == false && a.isStun())
            {
                stunActive = true;
                stunTimer = STUN_TIME;
                System.out.println("Stun Actif " + stunTimer + " ms restant");
            }

            if (stunActive == true)
            {
                stunTimer -= AI_TICK_RATE;
                System.out.println("Stun Actif " + stunTimer + " ms restant");
            }

            if (stunTimer == 0)
            {
                stunActive = false;
                a.deactivateStun();
            }
        }
    }

    public boolean isNextToTarget()         //a généraliser et déplacer dans les classes de controle spécifiques
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
        int x = controlledObject.getX();
        int y = controlledObject.getY();

        Map m = Game.instance.getMap();

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
        if (d_up < d_down && d_up < d_left && d_up < d_right)
        {
            //System.out.println("Décide d'aller en haut");
            controlledObject.goUp();
        }
        else if (d_down < d_up && d_down < d_left && d_down < d_right)
        {
            //System.out.println("Décide d'aller en bas");
            controlledObject.goDown();
        }
        else if (d_left < d_up && d_left < d_right && d_left < d_down)
        {
            //System.out.println("Décide d'aller a gauche");
            controlledObject.goLeft();
        }
        else if (d_right < d_up && d_right < d_down && d_right < d_left)
        {
            //System.out.println("Décide d'aller a droite");
            controlledObject.goRight();
        }
        else
        {
            //System.out.println("état indécisif");

            boolean loop = true;
            do
            {
                int d_rand = RandomizedInt(0, 3);

                if (d_up != INFINI && d_rand == 0)
                {
                    controlledObject.goUp();
                    loop = false;
                    //System.out.println("Après tirage décide d'aller en haut");
                }
                else if (d_down != INFINI && d_rand == 1)
                {
                    controlledObject.goDown();
                    loop = false;
                    //System.out.println("Après tirage décide d'aller en bas");
                }
                else if (d_left != INFINI && d_rand == 2)
                {
                    controlledObject.goLeft();
                    loop = false;
                    //System.out.println("Après tirage décide d'aller à gauche");
                }
                else if (d_right != INFINI && d_rand == 3)
                {
                    controlledObject.goRight();
                    loop = false;
                    //System.out.println("Après tirage décide d'aller a droite");
                }
                else
                {
                    //System.out.println("Tirage invalide");
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


