package com.generic.player;

import java.awt.*;
import java.awt.event.*;

public class InputHandler implements KeyListener {
    public boolean UP;
    public boolean DOWN;
    public boolean LEFT;
    public boolean RIGHT;
    public boolean ACTION;

    public void flush() {
        UP = false;
        DOWN = false;
        LEFT = false;
        RIGHT = false;
        ACTION = false;
    }

    public InputHandler(Window w) {
        flush();
        w.addKeyListener(this);
        System.out.println("-- Created Input Listener --");
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'z') {
            // System.out.println("Z");
            UP = true;
        } else if (e.getKeyChar() == 's') {
            // System.out.println("S");
            DOWN = true;
        } else if (e.getKeyChar() == 'q') {
            // System.out.println("Q");
            LEFT = true;
        } else if (e.getKeyChar() == 'd') {
            // System.out.println("D");
            RIGHT = true;
        } else if (e.getKeyChar() == ' ') {
            ACTION = true;
        }
    }

    public void keyPressed(KeyEvent key) {

    }

    public void keyReleased(KeyEvent key) {

    }
}
