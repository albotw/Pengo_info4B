package com.generic.UI;

import com.generic.launcher.Launcher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LauncherUI extends JPanel {
    private Launcher manager;

    private JButton SoloMode;
    private JButton SoloSettings;
    private JButton MultiMode;
    private JButton Profile;
    private JButton Quit;
    private JButton leaderboard;

    private ImagePanel Pengo;

    public LauncherUI()
    {
        super();
        setLayout(null);

        manager      = Launcher.instance;
        System.out.println(manager.toString());
        SoloMode     = new JButton("Mode Solo");
        SoloSettings = new JButton("Réglages (Solo)");
        MultiMode    = new JButton("Mode Réseau");
        Profile      = new JButton("Profils");
        leaderboard  = new JButton("Leaderboard");
        Quit         = new JButton("Quitter");

        SoloMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.SoloModeSelected();
            }
        });

        SoloSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.SoloSettingsSelected();
            }
        });

        MultiMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.MultiModeSelected();
            }
        });

        Profile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.ProfileModeSelected();
            }
        });

        leaderboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.leaderboardSelected();
            }
        });

        Quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        ImageIcon pengoLogo = new ImageIcon("src/ressources/Pengo-Logo.png");
        Pengo = new ImagePanel(pengoLogo.getImage());

        Pengo.setBounds(0, 0, 580, 170);

        SoloMode.setBounds(145, 185, 140, 30);
        SoloSettings.setBounds(295, 185, 140, 30);

        MultiMode.setBounds(145, 225, 290, 30);

        Profile.setBounds(145, 265, 290, 30);

        leaderboard.setBounds(145, 305, 290, 30);

        Quit.setBounds(145, 345, 290, 30);

        JLabel copyright = new JLabel("(c) Sega 1982 | (c) Yann Trou, Wassim Djellat, 2020");
        copyright.setBounds(5, 380, 300, 30);

        add(Pengo);
        add(SoloMode);
        add(SoloSettings);
        add(MultiMode);
        add(Profile);
        add(leaderboard);
        add(Quit);
        add(copyright);
        repaint();
    }

    public void updateProfileMode(String pseudo)
    {
        Profile.setText("Profils (" + pseudo + ")");
    }
}

/**
 * On a une fenêtre de 580 par 370
 * on met une marge de 15 pixels en dessous du logo -> y=185 de base
 *
 * 580 / 2 = 290
 * 290 / 2 = 145
 *
 * ce qui nous donne 145(vide) | 290(bouton) | 145(vide)
 * OU 145(vide) | 140(bouton) | 10(vide) | 140(bouton) | 145(vide)
 *
 */