package com.generic.net;

import java.io.Serializable;

public class Command implements Serializable {
    String c;
    String param;

    public Command(String c, String param)
    {
        this.c = c;
        this.param = param;
    }

    @Override
    public String toString() {
        return "Command{" +
                "c='" + c + '\'' +
                ", param='" + param + '\'' +
                '}';
    }
}
