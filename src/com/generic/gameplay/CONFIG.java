/**
 * Nom de la classe: CONFIG
 * <p>
 * Description: Classe utilitaire contenant des constantes pour l'éxécution du programme.
 *
 * @version 1.0
 * @Date 08/03/2020
 * @Author Yann
 */
package com.generic.gameplay;

import java.awt.*;

public class CONFIG {
    public final static int INFINI = 999999;

    public final static String WINDOW_TITLE = "Pengo Remake ";

    public static boolean LOW_RES_MODE = false;

    public final static int WH_HIGH_RES = 940;
    public final static int WW_HIGH_RES = 765;

    public final static int WH_LOW_RES = 520;
    public final static int WW_LOW_RES = 400;

    public final static int AI_TICK_RATE = 350;
    public final static int STUN_TIME = 2000;

    public final static int GRID_WIDTH = 13;    //650px
    public final static int GRID_HEIGHT = 15;    //750px

    public final static int SPRITE_SIZE_HD = 50;
    public final static int SPRITE_SIZE_SD = 25;

    public final static Color BG_DEFAULT_COLOR = new Color(0, 24, 24);

    public static void setLowResMode(boolean val) {
        LOW_RES_MODE = val;
    }
}
