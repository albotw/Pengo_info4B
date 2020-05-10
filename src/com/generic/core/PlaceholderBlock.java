package com.generic.core;

public class PlaceholderBlock extends MapObject{
    public PlaceholderBlock(int x, int y, String type)
    {
        super(x, y);
        this.type = type;
    }

    public void destroy(MapObject killer)
    {

    }
}
