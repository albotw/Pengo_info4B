package com.generic.core;


public class PlaceholderBlock extends MapObject implements Variante{
    private char orientation = 'N';
    private String variante;

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
