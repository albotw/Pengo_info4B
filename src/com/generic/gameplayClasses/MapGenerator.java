package com.generic.gameplayClasses;

import com.generic.coreClasses.IceBlock;
import com.generic.coreClasses.Map;
import com.generic.graphics.SpriteManager;
import com.generic.utils.Paire;
import com.generic.utils.Pile;

import static com.generic.utils.CONFIG.GRID_HEIGHT;
import static com.generic.utils.CONFIG.GRID_WIDTH;
import static com.generic.utils.Random.RandomizedInt;


/**
 * a chaque fois que case double impaire --> changement direction. ??
 *
 * utilisation de l'algorithme d"exploration exhaustive
 *
 * la map est remplie de IceBlocks
 * toutes les cases du tableau de vérification sont a false.
 * on prend une case de départ au hasard.
 * on entre dans la boucle
 * on met la case en cours de traitement comme étant visitée.
 * on vérifie qu'elle a au moins un voisin pas visité et au moins 2 IceBlocks adjacents (pour éviter la suppression totale.
 *      on prend un entier tiré au hasard.
 *      en fonction de l'entier et si les coordonnées le permettent
 *      si la position ou l'on veut se déplacer contient un IceBlock et qu'il n'a pas déja été traité
 *      on supprime le IceBlock à la case en cours de traitement
 *      on stocke les coordonnées dans la pile pour pouvoir dépiler dans le cas ou aucun mouvement n'est possible
 *      on met a jour les coordonnées sur la position ou l'on veut de déplacer
 * sinon (la case sur laquelle on se situe ne doit pas être modifiée)
 *      on récupère les valeurs précédentes dans la pile
 *      on met a jour les coordonnées
 * sinon (toutes les cases voisines ont été visitées)
 *      on récupère les valeurs précédentes dans la pile
 *      on met a jour les coordonnées
 *
 * si les coordonnées mises a jour correspondent à la case de départ, on s'arrète.
 */

/**
 * ALGO A MODIFIER URGEEEEEEENNNNTT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */
public class MapGenerator {
    public static Map generate()
    {
        Map m = Game.instance.getMap();
        boolean[][] visited = new boolean[GRID_WIDTH][GRID_HEIGHT];
        Pile historique = new Pile(GRID_HEIGHT * GRID_WIDTH);

        //on remplit la grille de blocs
        for (int i = 0; i < GRID_WIDTH; i++)
        {
            for (int j = 0; j < GRID_HEIGHT; j++)
            {
                m.place(new IceBlock(i, j), i, j);
                visited[i][j] = false;
            }
        }

        //position initiale
        int initX = RandomizedInt(0, GRID_WIDTH - 1);
        int initY = RandomizedInt(0, GRID_HEIGHT - 1);
        System.out.println("initX = " + initX + " initY = " + initY);

        int x = initX;
        int y = initY;
        boolean loop;
        do {
            loop = true;

            visited[x][y] = true;

            boolean left = true;
            boolean right = true;
            boolean up = true;
            boolean down = true;

            if (x - 1 >= 0) left = visited[x - 1][y];
            if (x + 1 < GRID_WIDTH) right = visited[x + 1][y];
            if (y - 1 >= 0) up = visited[x][y - 1];
            if (y + 1 < GRID_HEIGHT) down = visited[x][y+1];

            if (!left || !right || !up || !down)
            {
                //si le bloc a la position x y a plus d'un bloc adjacent
                // a ajuster en fonction du résultat de la génération.
                int neighbors = 0;
                if (m.getAt(x-1, y) != null) neighbors++;
                if (m.getAt(x+1, y) != null) neighbors++;
                if (m.getAt(x, y-1) != null) neighbors++;
                if (m.getAt(x, y+1) != null) neighbors++;

                //System.out.println("neighbors = " + neighbors);

                if (neighbors > 1)
                {
                    boolean retry = true;
                    do
                    {
                        int dir = RandomizedInt(0, 3);
                        //System.out.println("dir = " + dir);

                        //HAUT
                        if (dir == 0 && y - 1 >= 0)
                        {
                            if (m.getAt(x, y-1) != null)
                            //if (!up)
                            {
                                m.release(x, y);
                                historique.push(new Paire(x, y));
                                y--;
                                retry = false;
                            }
                        }

                        //BAS
                        if (dir == 1 && y + 1 < GRID_HEIGHT)
                        {
                            if (m.getAt(x, y+1) != null)
                            //if (!down)
                            {
                                m.release(x, y);
                                historique.push(new Paire(x, y));
                                y++;
                                retry = false;
                            }
                        }

                        //GAUCHE
                        if (dir == 2 && x - 1 >= 0)
                        {
                            if (m.getAt(x-1, y) != null)
                            //if (!left)
                            {
                                m.release(x, y);
                                historique.push(new Paire(x, y));
                                x--;
                                retry = false;
                            }
                        }

                        //DROITE
                        if (dir == 3 && x + 1 < GRID_WIDTH)
                        {
                            if (m.getAt(x+1, y) != null)
                            //if (!right)
                            {
                                m.release(x, y);
                                historique.push(new Paire(x, y));
                                x++;
                                retry = false;
                            }
                        }
                    }while(retry);


                }
                else
                {
                    Paire pos = (Paire)(historique.pull());
                    x = (Integer)pos.getKey();
                    y = (Integer)pos.getValue();
                }
            }
            else
            {
                Paire pos = (Paire)(historique.pull());
                x = (Integer)pos.getKey();
                y = (Integer)pos.getValue();
            }

            //System.out.println("x = " + x + " y = " + y);
            //System.out.println(m.toString());

            //vérification si toutes les cases possibles ont été parcourues
            if (visited[initX][initY] == true)
            {
                if (x == initX && y == initY)
                {
                    loop = false;
                }
            }

            SpriteManager.instance.transfer(m, Game.instance.getRenderer());

            try
            {
                Thread.currentThread().sleep(10);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }while(loop);
        return m;
    }
}
