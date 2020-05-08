package com.generic.net;

import java.io.Serializable;

public class Command implements Serializable {
    String c;           //valeur de la commande
    String param0;       //set de paramètres
    String param1;      //string
    String param2;

    public Command(String c, String param, String param1, String param2)
    {
        this.c = c;
        this.param0 = param;
        this.param1 = param1;
        this.param2 = param2;
    }

    public String getVal()
    {
        return this.c;
    }

    public String getParam0()
    {
        return this.param0;
    }

    public String getParam1()
    {
        return this.param1;
    }

    public String getParam2() { return this.param2;}

    @Override
    public String toString() {
        return "Command{" +
                "c='" + c + '\'' +
                ", param='" + param0 + '\'' +
                '}';
    }
}
