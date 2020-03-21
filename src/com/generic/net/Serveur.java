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
       public static void main(String[]args) throws IOException {
           ServerSocket s = new ServerSocket(port);
           Socket soc = s.accept();
           System.out.println("SOCKET "+s);
           System.out.println("SOCKET "+soc);
           BufferedReader sisr = new BufferedReader(new InputStreamReader(soc.getInputStream()));
           PrintWriter sisw = new PrintWriter(());
       }


    }




