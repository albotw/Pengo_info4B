package com.generic.core;

public class Void extends MapObject {
    public Void(int x, int y) {
        super(x, y);
        this.type = "void";
    }

    public void destroy(MapObject killer) {}
}