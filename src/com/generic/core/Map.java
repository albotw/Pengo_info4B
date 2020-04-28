package com.generic.core;

public class Map {
    //pattern singleton utilisé.
    //faire en sorte de "sémaphoriser" la classe.
    public static Map instance = null;
    private MapObject tab[][];
    private int width;
    private int height;

    private Map(int width, int height)
    {
        this.tab = new MapObject[width][height];
        this.instance = this;
        this.width = width;
        this.height = height;
    }

    public static Map createMap(int width, int height)
    {
        if (instance == null)
        {
            instance = new Map(width, height);
        }

        return instance;
    }

    public MapObject getAt(int x, int y)
    {
        if (x < 0 ||x >= width || y < 0 || y >= height)
        {
            return null;
        }
        else
        {
            return tab[x][y];
        }
    }

    public synchronized void place(MapObject o, int x, int y)
    {
        if (x >= 0 && x < width && y >= 0 && y < height)
        {
            tab[x][y] = o;
        }
    }

    public synchronized void release(int x, int y)
    {
        if (x >= 0 && x < width && y >= 0 && y < height)
        {
            tab[x][y] = null;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String toString()
    {
        String s = "";
        for (int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                if (tab[j][i] == null)
                {
                    s += ".";
                }
                else
                {
                    s += "X";
                }
            }
            s += "\n";
        }

        return s;
    }

    public static void deleteMap(){
        instance = null;
    }
}
