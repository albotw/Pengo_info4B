package com.generic.utils;

import java.util.Arrays;

public class Pile {
    int sommet;
    Object[] pile;

    public Pile(int size)
    {
        pile = new Object[size];
        sommet = -1;
    }

    public boolean isEmpty()
    {
        return sommet == -1 ? true : false;
    }

    public boolean isFull()
    {
        return sommet == pile.length ? true : false;
    }

    public void push(Object o)
    {
        if (isFull())
        {
            System.out.println("Erreur: pile pleine");
        }
        else
        {
            pile[++sommet] = o;
        }
    }

    public Object pull()
    {
        if (isEmpty())
        {
            System.out.println("Erreur: pile vide");
            return null;
        }
        else
        {
            return pile[sommet--];
        }
    }

    @Override
    public String toString() {
        return "Pile{" +
                "sommet=" + sommet +
                ", pile=" + Arrays.toString(pile) +
                '}';
    }
}
