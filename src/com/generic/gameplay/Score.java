package com.generic.gameplay;

import com.generic.core.Animal;

import java.io.IOException;

/**
 * TODO: gestion des scores indépendante pour chaque joueur
 * TODO: Envoi des scores
 * TODO: gestion des scores
 * TODO: Leaderboard
 */

public class Score {

    private int points = 0;

    public Score(){}


    public void setPoints(String context, int time){
        switch (context)
        {
            case "AnimalKilled":
                points = points + 400;
                break;

            case "GameEnd":
                if(time <= 20){
                    points = points + 5000;
                }
                if(time > 20 && time <= 29){
                    points = points + 2000;
                }
                if(time > 29 && time <= 39){
                    points = points + 1000;
                }
                if(time > 39 && time <= 60){
                    points = points + 500;
                }
                else
                    points = points + 150;

                break;

        }
        System.out.println("Points = " + points);
    }

    public int getPoints() {
        return points;
    }

    //gestion des scores locaux
    //leaderboard pseudo + valeur
    //envoi automatique si connecté -> ScoreUpdater dans net
    //interfacée au launcher
    //plus tard, rendre le score indépendant en fonction de chaque joueur.
}
