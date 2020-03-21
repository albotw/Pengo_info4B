package com.generic.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Serveur {

       static final int port = 8080;
       private boolean isRunning = true;
       public ServerSocket server = null;

       public static void main(String[]args) throws IOException {
           ServerSocket s = new ServerSocket(port);
           Socket soc = s.accept();
           System.out.println("SOCKET "+s);
           System.out.println("SOCKET "+soc);
           BufferedReader sisr = new BufferedReader(new InputStreamReader(soc.getInputStream()));
           PrintWriter sisw = new PrintWriter(());
       }

       public void launch(){
           Thread th = new Thread(new Runnable(){
               public void run(){
                   while(isRunning == true){
                       try {
                           Socket client = server.accept(); // on attend connexion joueur
                           System.out.println("Connexion etablit"); //Une fois reçue, on travaille dans un autre thread.
                           Thread th = new Thread(new JoueurThread(client));
                           th.start();

                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                           }

                           try {
                               server.ARRET();
                           } catch (IOException e) {
                               e.printStackTrace();
                               server = null;
                           }
                       }
                   });

                   th.start();
               }

               public void ARRET(){
                   isRunning = false;
               }
           }





