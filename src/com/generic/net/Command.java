package com.generic.net;

import java.io.Serializable;

public class Command implements Serializable {
    String c;           //valeur de la commande
    String param;       //set de paramètres
    String source;      //string

    public Command(String c, String param, String source)
    {
        this.c = c;
        this.param = param;
        this.source = source;
    }

    public String getVal()
    {
        return this.c;
    }

    public String getParam()
    {
        return this.param;
    }

    public String getSource()
    {
        return this.source;
    }

    @Override
    public String toString() {
        return "Command{" +
                "c='" + c + '\'' +
                ", param='" + param + '\'' +
                '}';
    }
}
