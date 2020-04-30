package com.generic.UI;

import com.generic.launcher.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LauncherUI extends JPanel {
    private Launcher manager = Launcher.instance;

    private JButton SoloMode;
    private JButton SoloSettings;
    private JButton MultiMode;
    private JButton Profile;
    private JButton Quit;

    private ImagePanel Pengo;

    public LauncherUI()
    {
        super();
        setLayout(null);

        SoloMode = new JButton("Mode Solo");
        SoloSettings = new JButton("Réglages (Solo)");
        MultiMode = new JButton("Mode Réseau");
        Profile = new JButton("Profils");
        Quit = new JButton("Quitter");

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

        Quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        ImageIcon pengoLogo = new ImageIcon("src/resources/Pengo-Logo.png");
        Pengo = new ImagePanel(pengoLogo.getImage());

        Pengo.setBounds(0, 0, 580, 170);

        SoloMode.setBounds(145, 185, 140, 30);
        SoloSettings.setBounds(295, 185, 140, 30);

        MultiMode.setBounds(145, 225, 290, 30);

        Profile.setBounds(145, 265, 290, 30);

        Quit.setBounds(145, 305, 290, 30);

        JLabel copyright = new JLabel("(c) Sega 1992 | (c) Yann Trou, Wassim Djellat, 2020");
        copyright.setBounds(5, 335, 300, 30);

        add(Pengo);
        add(SoloMode);
        add(SoloSettings);
        add(MultiMode);
        add(Profile);
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

/*
super();
        setLayout(new BorderLayout());

        SoloMode = new JButton("Mode Solo");
        SoloSettings = new JButton("Réglages (Solo)");
        MultiMode = new JButton("Mode Réseau");
        Profile = new JButton("Profils");

        ImageIcon pengoLogo = new ImageIcon("src/resources/Pengo-Logo.png");
        Pengo = new ImagePanel(pengoLogo.getImage());

        add(Pengo, BorderLayout.CENTER);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(3, 1));

        JPanel solo = new JPanel();
        solo.setLayout(new GridLayout(1, 2));

        solo.add(SoloMode);
        solo.add(SoloSettings);

        center.add(solo);
        center.add(MultiMode);
        center.add(Profile);

        add(center, BorderLayout.SOUTH);
 */