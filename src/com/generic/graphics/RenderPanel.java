package com.generic.graphics;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderPanel extends JPanel implements Runnable {
    private FPSCounter fps;
    private ArrayList<Sprite> RenderPile;

    public RenderPanel(Window output){
        fps = new FPSCounter();
        RenderPile = new ArrayList<Sprite>();

        output.add(this);
        this.setSize(output.getWidth(), output.getHeight());
    }

    public void run(){
        fps.start();
        while (true)
        {
            repaint();
            try{
                Thread.sleep(16);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private synchronized void draw(Graphics g){
        fps.frame();
        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.black);

        for (Sprite sp : RenderPile){
            g2d.drawImage(sp.getTexture(), sp.getX(), sp.getY(), this);
        }
        g2d.setColor(Color.RED);
        g2d.drawString("FPS: "+fps.get(), 0, 10);

    }

    public void addToRenderPile(Sprite spr){
        RenderPile.add(spr);
    }

    public void removeInRenderPile(Sprite spr) {
        RenderPile.remove(spr);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
        Toolkit.getDefaultToolkit().sync();
    }
}