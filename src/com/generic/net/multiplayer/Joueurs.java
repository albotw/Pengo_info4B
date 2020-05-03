package com.generic.net.multiplayer;

import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class Joueurs implements Runnable {

    private Socket sock;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;

    public Joueurs(Socket S){
        //Object socket = s;
    }


    @Override
    public void run() {

    }
}
