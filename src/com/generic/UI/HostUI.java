package com.generic.UI;

import com.generic.launcher.Online;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HostUI extends JPanel {

    private JButton startGame;
    private JButton Settings;
    private JButton Join1;
    private JButton Join2;
    private JList equipe1;
    private DefaultListModel modE1;
    private DefaultListModel modE2;
    private JList equipe2;
    private JButton close;

    private Online manager;

    public HostUI(Online od) {
        super();
        this.manager = od;
        setLayout(new BorderLayout());
        startGame = new JButton("Lancer la partie");
        Settings = new JButton("Paramètres");
        Join1 = new JButton("Rejoindre Equipe1");
        Join2 = new JButton("Rejoindre Equipe2");
        equipe1 = new JList();
        equipe2 = new JList();
        close = new JButton("Retour");

        modE1 = manager.getE1();
        modE2 = manager.getE2();

        equipe1.setModel(modE1);
        equipe2.setModel(modE2);

        equipe1.setEnabled(false);
        equipe2.setEnabled(false);

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.startGameSelected();
            }
        });

        Settings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.settingsSelected();
            }
        });

        Join1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Join2.setEnabled(true);
                manager.join1Selected();
                Join1.setEnabled(false);
            }
        });

        Join2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Join1.setEnabled(true);
                manager.join2Selected();
                Join2.setEnabled(false);
            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.closeSelected();
            }
        });

        setLayout(new BorderLayout());

        JPanel north = new JPanel();
        north.setLayout(new GridLayout(1, 2));
        north.add(startGame);
        north.add(Settings);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(1, 2));
        center.setBorder(BorderFactory.createTitledBorder("Composition des équipes"));
        center.add(equipe1);
        center.add(equipe2);

        JPanel south = new JPanel();
        JPanel southTop = new JPanel();
        southTop.setLayout(new GridLayout(1, 2));
        southTop.add(Join1);
        southTop.add(Join2);
        south.setLayout(new GridLayout(2, 1));
        south.add(southTop);
        south.add(close);

        add(north, BorderLayout.NORTH);
        add(south, BorderLayout.SOUTH);
        add(center, BorderLayout.CENTER);
    }

    public JButton getClose() {
        return close;
    }

    public void setClose(JButton close) {
        this.close = close;
    }

}
