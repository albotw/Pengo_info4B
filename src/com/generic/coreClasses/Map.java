package com.generic.coreClasses;

public class Map {

    public static Map instance;
    MapObject tab[][];

    public Map(MapObject [][] tab) {
        this.tab = tab;
    }

    public MapObject getAt(int x, int y)
    {
        return tab[y][y];
    }

    public void place(MapObject o, int x, int y)
    {
        tab[x][y] = o;
    }

    public void release(int x, int y)
    {
        tab[x][y] = null;
    }
}
