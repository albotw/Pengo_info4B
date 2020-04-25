package com.generic.net;

import java.io.*;
import java.net.Socket;

public class Connexion extends Thread {
    private int ID;
    private Socket s;
    private BufferedReader textIn;
    private PrintWriter textOut;
    private ObjectInputStream commandIn;
    private ObjectOutputStream commandOut;

    public Connexion(int ID, Socket s)
    {
        this.ID = ID;
        this.s = s;

        try{
            commandIn = new ObjectInputStream(s.getInputStream());
            commandOut = new ObjectOutputStream(s.getOutputStream());
            textIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
            textOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())), true);

            // Texte d'accueil ici
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try
        {
            while(true)
            {
                Command cmd = (Command)(commandIn.readObject());
                if (cmd.c.equals("QUIT")) break;
                //PARSING COMMANDE ICI
            }

            //FIN DE CONNEXION ICI
            textOut.close();
            textIn.close();
            s.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
