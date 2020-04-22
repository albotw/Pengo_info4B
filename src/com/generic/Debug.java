package com.generic;

import java.util.Timer;
import java.util.TimerTask;

import com.generic.coreClasses.Map;
import com.generic.gameplayClasses.MapGenerator;
import com.generic.graphics.*;
import com.generic.utils.*;

public class Debug {

    public static RenderThread renderer;
    public static Map m;
    public static void main(String[] args) {

        Window w = new Window(CONFIG.WINDOW_WIDTH,  CONFIG.WINDOW_HEIGHT);
        renderer = new RenderThread(w);

        //Sprite spr1 = new Sprite(400, 400);
        //spr1.loadImage("src/resources/img1.png");
        //renderer.addToRenderPile(spr1, "foreground");

        renderer.start();

        m = MapGenerator.generate();
        System.out.println(m.toString());
        /*
        try{
            InitServer.init();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        Timer chrono = new Timer();        // Fonction Timer pour savoir le temps qu'il reste au joueur
        chrono.schedule(new TimerTask() {

            int time = 90;

            @Override
            public void run() {
                System.out.println("Time : " + time);

                if (time == 0) {
                    cancel();
                }
                time--;
            }
        }, 1000, 1000);

         */
    }

}