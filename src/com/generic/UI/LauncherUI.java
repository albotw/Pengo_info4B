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

    public LauncherUI() {
        super();
        setLayout(null);

        manager = Launcher.instance;
        SoloMode = new ImageButton("Solo", "ressources/button_focus_small.png", "ressources/button_noFocus_small.png");
        SoloSettings = new ImageButton("Réglages", "ressources/button_focus_small.png", "ressources/button_noFocus_small.png");
        MultiMode = new ImageButton("Multijoueur", "ressources/button_focus_large.png", "ressources/button_noFocus_large.png");
        Profile = new ImageButton("Profils", "ressources/button_focus_large.png", "ressources/button_noFocus_large.png");
        leaderboard = new ImageButton("Leaderboard", "ressources/button_focus_large.png", "ressources/button_noFocus_large.png");

        Quit = new ImageButton("Quitter", "ressources/button_focus_large.png", "ressources/button_noFocus_large.png");

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
                manager.quit();
            }
        });

        ImageIcon pengoLogo = new ImageIcon("ressources/LOGO.png");
        Pengo = new ImagePanel(pengoLogo.getImage());
        Pengo.setOpaque(false);

        ImageIcon bg = new ImageIcon("ressources/menuBg.png");
        ImagePanel background = new ImagePanel(bg.getImage());

        background.setBounds(0, 0, 600, 450);

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

        add(background);
        repaint();
    }

    public void updateProfileMode(String pseudo) {
        Profile.setText("Profils (" + pseudo + ")");
    }
}

/**
 * On a une fenêtre de 580 par 370
 * on met une marge de 15 pixels en dessous du logo -> y=185 de base
 * <p>
 * 580 / 2 = 290
 * 290 / 2 = 145
 * <p>
 * ce qui nous donne 145(vide) | 290(bouton) | 145(vide)
 * OU 145(vide) | 140(bouton) | 10(vide) | 140(bouton) | 145(vide)
 */