package com.generic.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.generic.launcher.Online;

public class OnlineUI extends JPanel {
    private JButton joinGame;
    private JButton hostGame;
    private JButton Close;

    private Online manager;

    public OnlineUI(Online od) {
        super();
        this.manager = od;

        setLayout(new BorderLayout());

        joinGame = new JButton("Se connecter à une partie");
        hostGame = new JButton("héberger une partie");
        Close    = new JButton("Fermer");

        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.joinGameSelected();
            }
        });
        hostGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.hostGameSelected();
            }
        });
        Close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.CloseSelected();
            }
        });

        JPanel choix = new JPanel();
        choix.setBorder(BorderFactory.createTitledBorder("Que voulez vous faire ?"));
        choix.setLayout(new GridLayout(1, 2));
        choix.add(joinGame);
        choix.add(hostGame);

        JPanel south = new JPanel();
        south.setLayout(new GridLayout(1, 1));
        south.add(Close);

        add(choix, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
    }

}
