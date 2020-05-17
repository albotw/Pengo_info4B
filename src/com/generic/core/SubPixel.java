package com.generic.core;

public interface SubPixel {
    int step = 8;

    void setSubpixel(int val);
    int getSubpixel();
    boolean getTransitionState();
    void setTransitionState(boolean val);
}
