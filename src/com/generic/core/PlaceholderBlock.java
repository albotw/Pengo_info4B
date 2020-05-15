package com.generic.core;

public class PlaceholderBlock extends MapObject implements Orientation {
    private char orientation = 'N';

    public PlaceholderBlock(int x, int y, String type, char orientation) {
        super(x, y);
        this.type = type;
        this.orientation = orientation;
    }

    public void destroy(MapObject killer) {

    }

    public String getOrientation() {
        return "" + this.orientation;
    }

    public void setOrientation(char direction) {
        this.orientation = direction;
    }
}
