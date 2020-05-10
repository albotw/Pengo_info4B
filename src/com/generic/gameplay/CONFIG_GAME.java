package com.generic.gameplay;

/**
 * Classe permettant la configuration de la partie en qui va être jouée. lecture
 * par les objets du jeu, modification par le launcher. TODO vérifier
 * application de la config
 */

public class CONFIG_GAME {
    public static int PLAYER_INIT_LIVES = 1;
    public static int AI_INIT_LIVES = 1;
    public static boolean PLAYER_IS_ANIMAL = false;
    public static boolean PLAYER_IS_PENGUIN = true;
    public static int N_PLAYERS = 1;
    public static int N_AI = 1;
    public static int N_NIVEAUX = 1;
    public static boolean CLIENT = false;

    public static boolean TEAM_1_IS_ANIMAL = false;
    public static boolean TEAM_2_IS_ANIMAL = true;

    public static boolean PvE = true;
    public static boolean PvP = false;


    public static void setPlayerIsPenguin(boolean val) {
        PLAYER_IS_ANIMAL = !val;
        PLAYER_IS_PENGUIN = val;
    }

    public static void setPlayerInitLives(int val) {
        PLAYER_INIT_LIVES = val;
    }

    public static void setAiInitLives(int val) {
        AI_INIT_LIVES = val;
    }


    public static void setnAi(int val) {
        N_AI = val;
    }

    public static void setnNiveaux(int val) {
        N_NIVEAUX = val;
    }

    public static void setCLIENT(boolean val){
        CLIENT = val;}

    public static void setPvP(boolean val)
    {
        PvP = val;
        PvE = !val;
    }

    public static void setTeam1IsAnimal(boolean val)
    {
        TEAM_1_IS_ANIMAL = val;
        TEAM_2_IS_ANIMAL = !val;
    }
}
