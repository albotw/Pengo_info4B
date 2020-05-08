package com.generic.net;

import java.io.Serializable;

public class Command implements Serializable {
    String c;           //valeur de la commande
    String[] params;

    public Command(String c, String[] params)
    {
        this.c = c;
        this.params = params;
    }

    public String getVal()
    {
        return this.c;
    }

    public String[] getParams()
    {
        return this.params;
    }

    public String getParam(int index)
    {
        if (index >= 0 && index < params.length)
        {
            return params[index];
        }
        else return null;
    }
}
