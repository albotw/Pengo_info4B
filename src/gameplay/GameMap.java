package gameplay;

import core.MapObject;

public class GameMap {
    private volatile MapObject tab[][];
    private int width;
    private int height;
    private boolean local;

    public GameMap(int width, int height) {
        this.tab = new MapObject[width][height];
        this.width = width;
        this.height = height;
    }

    public void setLocal(boolean val) {
        this.local = val;
    }

    public MapObject getAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height || tab == null || tab[x][y] == null) {
            return null;
        } else {
            return tab[x][y];
        }
    }

    public synchronized void place(MapObject o, int x, int y) {
        if (inBounds(x, y)) {
            tab[x][y] = o;
        }
    }

    public synchronized void release(int x, int y) {
        if (inBounds(x, y)) {
            tab[x][y] = null;
        }
    }

    public synchronized void delete(int x, int y)
    {
        if (inBounds(x, y) && tab[x][y] != null)
        {
            tab[x][y].clean();
            tab[x][y] = null;
        }
    }

    public boolean inBounds(int x, int y)
    {
        return (x >= 0 && x < width
            && y >= 0 && y < height
            && tab != null);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}