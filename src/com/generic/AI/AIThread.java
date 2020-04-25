package com.generic.AI;

import com.generic.coreClasses.Map;
import com.generic.coreClasses.MapObject;
import com.generic.gameplayClasses.Game;

import java.util.Vector;

import static com.generic.utils.CONFIG.GRID_HEIGHT;
import static com.generic.utils.Equations.VectorialDistance;

public class AIThread {

    private MapObject target;
    private MapObject controlledObject;


    public void selectMovement()
    {
        /**
         * Fonctionnement séléction de mouvement
         * (version vraiment allégée de l'algorithme a*)
         * on récupère les coords. de l'objet controllé
         * on teste les cases qui lui sont adjacentes (nulles et dans les bords de la map)
         * pour chaque case potentielle on va calculer sa distance vectorielle avec la cible
         * on prend la case avec la plus petite valeur et on appelle la méthode du mouvement associé.
         */
        int posX = controlledObject.getX();
        int posY = controlledObject.getY();

        Map m = Game.instance.getMap();

        double d_up = -1;
        double d_down = -1;
        double d_left = -1;
        double d_right = -1;

        //test et mesure case au dessus
        if (posX > 0)
        {
            if (m.getAt(posX - 1, posY) == null) d_up = VectorialDistance(posX - 1, target.getX(), posY, target.getY());
        }

        //test et mesure case au dessous
        if (posX < GRID_HEIGHT - 1)
        {
            
        }
    }
}


