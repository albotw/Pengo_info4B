package com.generic.UI;

import com.generic.launcher.Online;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HostSettingsUI extends JPanel {
    private ButtonGroup bg1;
    private JRadioButton pve;
    private JRadioButton pvp;

    private ButtonGroup bg2;
    private JRadioButton equipe1animaux;
    private JRadioButton equipe2animaux;        //changer le nom pour IA quand pve séléctionné.

    private JComboBox nbIA;                     //actif que si pve séléctionné.

    private JButton save;

    private Online manager;

    public HostSettingsUI(Online manager) {
        super();

        this.manager = manager;

        setLayout(new GridLayout(4, 1));

        JPanel jp2 = new JPanel();
        jp2.setLayout(new GridLayout(1, 2));
        jp2.setBorder(BorderFactory.createTitledBorder("Mode de jeu"));
        pve = new JRadioButton("PvE");
        pvp = new JRadioButton("PvP");
        bg1 = new ButtonGroup();
        bg1.add(pve);
        bg1.add(pvp);

        JPanel jp3 = new JPanel();
        jp3.setLayout(new GridLayout(1, 2));
        jp3.setBorder(BorderFactory.createTitledBorder("Entités contrôlées"));
        equipe1animaux = new JRadioButton("Equipe 1 = animaux");
        equipe2animaux = new JRadioButton("Equipe 2 = animaux");
        bg2 = new ButtonGroup();
        bg2.add(equipe1animaux);
        bg2.add(equipe2animaux);

        jp2.add(pve);
        jp2.add(pvp);
        jp3.add(equipe1animaux);
        jp3.add(equipe2animaux);
        add(jp2);
        add(jp3);

        nbIA = new JComboBox();
        add(nbIA);

        save = new JButton("Sauvegarder");
        add(save);

        for (int i = 1; i <= 4; i++) {
            nbIA.addItem("" + i + " bots");
        }

        pve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pve.isSelected()) {
                    equipe2animaux.setText("IA = animaux");
                    nbIA.setEnabled(true);
                }
            }
        });

        pvp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pvp.isSelected()) {
                    equipe2animaux.setText("Equipe 2 = animaux");
                    nbIA.setEnabled(false);
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean team1animal = equipe1animaux.isSelected();
                boolean modePvp = pvp.isSelected();
                int nombreIA = nbIA.getSelectedIndex() + 3;

                manager.saveGameSettings(modePvp, team1animal, nombreIA);
            }
        });

        pve.setSelected(true);
        equipe2animaux.setSelected(true);
    }
}

/**
 * | REPARTITION |
 * ° pvp ° pve
 * °equipe1 animaux ° equipe2
 * | nbIA |
 * |NbNiveaux|
 * | sauvegarder |
 */
