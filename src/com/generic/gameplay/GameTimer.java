package com.generic.gameplay;

/**
 * TODO: Permettre l'utilisation du timer comme chrono ou compteur.
 */
public class GameTimer extends Thread{
    private boolean stop;
    private int time;

    public GameTimer()
    {
        time = 0;
    }

    public void run()
    {
        while(stop == false)
        {
            time++;
            System.out.println("Timer = " + time);
            try
            {
                sleep(1000);
            }catch (Exception e) {e.printStackTrace();}
        }
    }

    public void stopTimer()
    {
        stop = true;
    }

    public int getTime()
    {
        return this.time;
    }
}
