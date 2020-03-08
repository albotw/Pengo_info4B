/**
 * Nom de la classe: FPSCounter
 *
 * Description: Calcul du nombre d'images par secondes a partir d'un timer.
 *
 * Version: 1.0
 *
 * Date: 08/03/2020
 *
 * Auteur: Yann
 */

package com.generic.graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class FPSCounter implements ActionListener {
    private final Timer resetTimer;
    private int current, last;

    public FPSCounter() {
        resetTimer = new Timer(1000, this);
    }

    public  void start() {
        resetTimer.start();
        current = 0;
        last = -1;
    }

    public  void stop() {
        resetTimer.stop();
        current = -1;
    }

    public  void frame() {
        ++current;
    }

    @Override
    public  void actionPerformed(final ActionEvent e) {
        last = current;
        current = 0;
    }

    public int get() {
        return last;
    }
}