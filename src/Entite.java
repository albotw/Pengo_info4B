abstract class Entite {
    int x;
    int y;

    public Entite(int x, int y) {
        this.x = x;
        this.y = y;
    }

    abstract void moove();
    abstract void isDead();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
