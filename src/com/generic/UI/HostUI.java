package com.generic.UI;

import com.generic.launcher.OnlineDialog;

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

    private OnlineDialog manager;

    public HostUI(OnlineDialog od) {
        super();
        this.manager = od;
        setLayout(new BorderLayout());
        startGame = new JButton("Lancer la partie");
        Settings  = new JButton("Paramètres");
        Join1     = new JButton("Rejoindre Equipe1");
        Join2     = new JButton("Rejoindre Equipe2");
        equipe1   = new JList();
        equipe2   = new JList();
        close     = new JButton("Retour");

        modE1 = new DefaultListModel();
        modE2 = new DefaultListModel();

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
        north.add(Join1);
        north.add(Join2);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(1, 2));
        center.setBorder(BorderFactory.createTitledBorder("Composition des équipes"));
        center.add(equipe1);
        center.add(equipe2);

        JPanel south = new JPanel();
        JPanel southTop = new JPanel();
        southTop.setLayout(new GridLayout(1, 2));
        southTop.add(startGame);
        southTop.add(Settings);
        south.setLayout(new GridLayout(2, 1));
        south.add(southTop);
        south.add(close);

        add(north, BorderLayout.NORTH);
        add(south, BorderLayout.SOUTH);
        add(center, BorderLayout.CENTER);
    }

    public JButton getStartGame() {
        return startGame;
    }

    public void setStartGame(JButton startGame) {
        this.startGame = startGame;
    }

    public JButton getSettings() {
        return Settings;
    }

    public void setSettings(JButton settings) {
        Settings = settings;
    }

    public JButton getJoin1() {
        return Join1;
    }

    public void setJoin1(JButton join1) {
        Join1 = join1;
    }

    public JButton getJoin2() {
        return Join2;
    }

    public void setJoin2(JButton join2) {
        Join2 = join2;
    }

    public JList getEquipe1() {
        return equipe1;
    }

    public void setEquipe1(JList equipe1) {
        this.equipe1 = equipe1;
    }

    public DefaultListModel getModE1() {
        return modE1;
    }

    public void setModE1(DefaultListModel modE1) {
        this.modE1 = modE1;
    }

    public DefaultListModel getModE2() {
        return modE2;
    }

    public void setModE2(DefaultListModel modE2) {
        this.modE2 = modE2;
    }

    public JList getEquipe2() {
        return equipe2;
    }

    public void setEquipe2(JList equipe2) {
        this.equipe2 = equipe2;
    }

    public JButton getClose() {
        return close;
    }

    public void setClose(JButton close) {
        this.close = close;
    }

}
