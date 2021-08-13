package graphics.effects;

import graphics.SpriteManager;
import graphics.TextureID;

import java.util.ArrayList;

public abstract class Effect {

    protected ArrayList<EffectFrame> frames;
    protected int currentFrame;
    protected long time;
    protected long totalTime;

    protected boolean loop;
    protected boolean done;

    protected int x;
    protected int y;

    public Effect(int x, int y, boolean loop)
    {
        this.x = x;
        this.y = y;
        this.loop = loop;

        this.frames = new ArrayList<EffectFrame>();

        setFrames();
        setTotalTime();
        start();

        SpriteManager.instance.addEffect(this);
    }

    abstract void setFrames();

    public void start()
    {
        this.time = 0;
        this.currentFrame = 0;
        this.done = false;
    }

    public void stop()
    {
        this.done = true;
    }

    public void setTotalTime()
    {
        /*
        this.totalTime = 0;
        for (EffectFrame ef : frames)
        {
            this.totalTime += ef.duration;
        }*/
        this.totalTime = frames.get(frames.size() - 1).duration;
    }

    public void clean()
    {
        frames.clear();
    }

    public void update(long elapsedTime)
    {
        if (frames.size() > 1 && !done) {
            this.time += elapsedTime;
            if (this.time >= this.totalTime) {
                if (loop) {
                    this.time = this.time % totalTime;
                    currentFrame = 0;
                } else {
                    this.time = 0;
                    currentFrame = 0;
                    done = true;
                }

            }
            else
            {
                while (time > frames.get(currentFrame).duration)
                {
                    currentFrame++;
                }
            }
        }
    }

    public TextureID getFrame()
    {
        return frames.get(currentFrame).frame;
    }

    public boolean isDone() { return done;}

    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public void setPosition(int x, int y) { this.x = x; this.y = y;}
}

