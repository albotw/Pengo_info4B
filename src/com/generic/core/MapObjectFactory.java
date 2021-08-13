package com.generic.core;

import com.generic.core.blocks.DiamondBlock;
import com.generic.core.blocks.IceBlock;
import com.generic.core.blocks.PlaceholderBlock;
import com.generic.core.entities.Animal;
import com.generic.core.entities.Penguin;
import com.generic.gameplay.GameMap;

public class MapObjectFactory<T> {

    private static void linkMap(MapObject mo, GameMap m) {
        mo.setMap(m);
        m.place(mo, mo.x, mo.y);
    }

    public static IceBlock createIceBlock(int x, int y, GameMap m) {
        IceBlock ib = new IceBlock(x, y);
        linkMap(ib, m);
        return ib;
    }

    public static DiamondBlock createDiamondBlock(int x, int y, GameMap m) {
        DiamondBlock db = new DiamondBlock(x, y);
        linkMap(db, m);
        return db;
    }

    public static Penguin createPenguin(int x, int y, GameMap m) {
        Penguin p = new Penguin(x, y);
        linkMap(p, m);
        return p;
    }

    public static Animal createAnimal(int x, int y, GameMap m) {
        Animal a = new Animal(x, y);
        linkMap(a, m);
        return a;
    }

    public static PlaceholderBlock createPlaceholder(int x, int y, GameMap m, String type, String orientation, String variante) {

        if (orientation.length() == 0) {
            PlaceholderBlock phb = new PlaceholderBlock(x, y, type, '\0', variante);
            linkMap(phb, m);
            return phb;
        } else {
            PlaceholderBlock phb = new PlaceholderBlock(x, y, type, orientation.charAt(0), variante);
            linkMap(phb, m);
            return phb;
        }
    }
}
