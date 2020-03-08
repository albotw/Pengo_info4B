package com.generic.coreClasses;

public class Animal extends Entite {
    boolean stun;
    public Animal(int x, int y) {
        super(x, y);
        stun = false;
    }

    @Override
    void move() {

    }

    @Override
    void destroy() {

    }

    public void setStun(){
        /*
        Etat = "normal";                       // On met son état normal au départ

        if(Animal.NearMur && Pengouin.stun){   // Si l'animal et prêt du mur et que le penguoin déclenche le stun alors l'état de l'animal va passer de normal -> Stun;
            Etat = "stun";                     // on rajoutera un delay comme quoi le stun durera que 3 secondes puis passera en mode normal
            delay(3000);
            Etat = "normal";
        }

        */

        this.stun = !this.stun;
    }

    public boolean getStunStatus()
    {
        return this.stun;
    }

    public void Respawn(){
                             // si l'animal meurt, il doit respawn quelques secondes après sur un bloc de glace.



    }

    public void destruction(BlocGlace B){


    }
}
