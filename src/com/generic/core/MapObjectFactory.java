package com.generic.core;

public class MapObjectFactory {

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

    public static PlaceholderBlock createPlaceholder(int x, int y, GameMap m, String type, String orientation) {

        if (orientation.length() == 0) {
            PlaceholderBlock phb = new PlaceholderBlock(x, y, type, '\0');
            linkMap(phb, m);
            return phb;
        } else {
            PlaceholderBlock phb = new PlaceholderBlock(x, y, type, orientation.charAt(0));
            linkMap(phb, m);
            return phb;
        }
    }
}
