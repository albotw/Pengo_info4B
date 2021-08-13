package graphics;

import core.Direction;

import javax.swing.*;
import java.awt.*;

import static config.CONFIG.SPRITE_SIZE;

public class Sprite {
    public int x;    //position X sur l'écran
    public int y;    //position Y sur l'écran
    private TextureID texture;
    private Direction orientation;
    private boolean hasOrientation;

    public Sprite(){
        this.hasOrientation = false;
        this.orientation = Direction.UP;
    }

    public Sprite(int x, int y, TextureID texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.orientation = Direction.UP;
        this.hasOrientation = false;
    }

    //ATTENTION: conversion de la position sur la GameMap en position sur l'écran.
    public void setPosition(int x, int y)
    {
        // on rajoute sprite size pour avoir de la place pour la bordure.
        this.x = SPRITE_SIZE + x * SPRITE_SIZE;
        this.y = SPRITE_SIZE + y * SPRITE_SIZE;
    }

    public void setOrientation(Direction orientation)
    {
        if (hasOrientation)
        {
            this.orientation = orientation;
        }
    }

    public TextureID getTexture() {
        if (hasOrientation)
        {
            return TextureID.valueOf(texture.name() + "_" + orientation.name());
        }
        else return texture;
    }

    public void setTexture(TextureID tID)
    {
        this.texture = tID;
    }

    public void toggleOrientation(boolean value) {
        this.hasOrientation = value;
    }
}