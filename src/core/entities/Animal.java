package core.entities;

import core.Direction;
import core.MapObject;
import core.blocks.DiamondBlock;
import events.types.AnimalKilledEvent;
import graphics.TextureID;
import core.blocks.IceBlock;
import gameplay.GameController;
import events.ThreadID;

public class Animal extends MapEntity{
    private boolean isStun;
    private TextureID variante = TextureID.DARKNUT;

    public Animal(int x, int y) {
        super(x, y);
        this.isStun = false;
        sprite.setTexture(variante);
    }

    public void action() {
    }

    public void activateStun() {
        this.isStun = true;
    }

    public void deactivateStun() {
        this.isStun = false;
    }

    public boolean isStun() {
        return this.isStun;
    }

    public void destroy(MapObject source) {
        m.release(this.x, this.y);
        GameController.publish(new AnimalKilledEvent(this.controller), ThreadID.Controller);
        System.out.println("published event");
        clean();
    }

    public void checkCollisions(int x, int y)
    {
        if (m.getAt(x, y) instanceof Penguin)
        {
            m.getAt(x, y).destroy(this);
        }
        else if (m.getAt(x, y) instanceof IceBlock)
        {
            m.getAt(x, y).destroy(this);
        }
    }

    public void goLeft() {
        super.setOrientation(Direction.LEFT);
        if (!isStun) {
            checkCollisions(x - 1, y);
            if (!(m.getAt(x - 1, y) instanceof DiamondBlock))
            {
                super.goLeft();
            }
        }
    }

    public void goRight() {
        super.setOrientation(Direction.RIGHT);
        if (!isStun) {
            checkCollisions(x + 1, y);
            if (!(m.getAt(x + 1, y) instanceof DiamondBlock))
            {
                super.goRight();
            }
        }
    }

    public void goUp() {
        super.setOrientation(Direction.UP);
        if (!isStun) {
            checkCollisions(x, y - 1);
            if (!(m.getAt(x, y - 1) instanceof DiamondBlock))
            {
                super.goUp();
            }
        }
    }

    public void goDown() {
        super.setOrientation(Direction.DOWN);
        if (!isStun) {
            checkCollisions(x, y + 1);
            if (!(m.getAt(x, y + 1) instanceof DiamondBlock))
            {
                super.goDown();
            }
        }
    }

    public void setVariante(TextureID var) {
        this.variante = var;
        sprite.setTexture(variante);

        if (var == TextureID.LEECHER)
        {
            sprite.toggleOrientation(false);
        }
        else
        {
            sprite.toggleOrientation(true);
        }
    }

    public TextureID getVariante() {
        return this.variante;
    }
}
