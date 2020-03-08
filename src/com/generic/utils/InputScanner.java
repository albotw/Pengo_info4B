package com.generic.utils;

import java.awt.event.*;

public class InputScanner implements KeyListener {
    Object receiver;
    public InputScanner(Object receiver)
    {
        this.receiver = receiver;
    }
    public void keyPressed(KeyEvent key)
    {
        int code = key.getKeyCode();
        switch(code)
        {
            case KeyEvent.VK_UP:
                //code
                break;

            case KeyEvent.VK_DOWN:
                //code
                break;

            case KeyEvent.VK_LEFT:
                //code
                break;

            case KeyEvent.VK_RIGHT:
                //code
                break;

            case KeyEvent.VK_ESCAPE:
                //code
                break;
        }
    }

    public void keyTyped(KeyEvent key)
    {

    }

    public void keyReleased(KeyEvent key)
    {

    }
}
