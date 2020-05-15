package com.generic.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ImageButton extends JButton {
    private Image focus;
    private Image noFocus;
    private Font police;
    private String text;

    private boolean hasFocus;

    public ImageButton(String text, String focusDir, String noFocusDir) {
        super();
        this.text = text;

        ImageIcon tmp = new ImageIcon(focusDir);
        focus = tmp.getImage();

        tmp = new ImageIcon(noFocusDir);
        noFocus = tmp.getImage();


        try {
            police = Font.createFont(Font.TRUETYPE_FONT, new File("ressources/police.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setBorder(null);
        hasFocus = false;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if (!hasFocus) {
                    hasFocus = true;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if (hasFocus) {
                    hasFocus = false;
                }
            }
        });
    }


    public void draw(Graphics g) {
        if (hasFocus) {
            g.drawImage(focus, 0, 0, this);
        } else {
            g.drawImage(noFocus, 0, 0, this);
        }
        g.setColor(Color.WHITE);
        g.setFont(police.deriveFont(14f));
        g.drawString(text, 16, 23);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
}
