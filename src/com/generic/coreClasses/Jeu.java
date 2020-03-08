package com.generic.coreClasses;

import java.util.Timer;
import java.util.TimerTask;

public class Jeu {


    public void Reset(){

    }
    public void Start(){

    }
    public void GameOver(){
        /*if(Penguoin.isDead && Penguoin.Life=0 || Jeu.Timer = 0){
            return "GAMEOVER";
        }

         */
    }
    public void Victoire(){
        /*if(CptD = 3 || Animal.isDead=3){
            return "VICTOIRE";
        }

         */
    }
    public void Timer(){
        Timer chrono = new Timer();        // Fonction Timer pour savoir le temps qu'il reste au joueur
        chrono.schedule(new TimerTask() {

            int time = 90;
            @Override
            public void run() {
                System.out.println("Time : " + time);

                if(time==0){
                    cancel();
                }
                time--;
            }
        }, 1000, 1000);
    }
    public void UpdateScore(int Score){

    }

    private void InitGame(){
        /*
        Pengouin.Life = 5;
        Score = 0;
        InitMap();
        N_ANIMALS=3;
         */
    }

    private void InitMap(){
        /*int i;
        for(i = 0; i<N_BLOCKS*N_BLOCKS;i++){   // création de map bloc par bloc
        }

         */
    }


    //MULTI

    /*public void Classement(Joueur){

    }*/
}
