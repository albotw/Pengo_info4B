package com.generic.core.blocks;


import com.generic.core.MapObject;

public class PlaceholderBlock extends MapObject {
    private char orientation = 'N';
    private String variante;
    private String type;

    public PlaceholderBlock(int x, int y, String type, char orientation, String variante) {
        super(x, y);
        this.type = type;
        this.orientation = orientation;
        this.variante = variante;
    }

    public void destroy(MapObject killer) {

    }

    public String getOrientation() {
        return "" + this.orientation;
    }

    public void setOrientation(char direction) {
        this.orientation = direction;
    }

    public String getVariante(){return this.variante;}

    public void setVariante(String variante){
        this.variante = variante;
    }
}
