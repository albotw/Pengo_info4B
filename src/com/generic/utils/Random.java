package com.generic.utils;

public class Random {

    public static int RandomizedInt(int a, int b)
    {
        if (a < b)
        {
            b++;
            return (int)(a+(b-a)*Math.random());
        }
        else{
            a++;
            return (int)(b+(a-b)*Math.random());
        }
    }
}
