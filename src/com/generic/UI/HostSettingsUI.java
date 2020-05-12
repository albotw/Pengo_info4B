package com.generic.UI;

import com.generic.launcher.Online;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HostSettingsUI extends JPanel {

    private JComboBox repartition;              //2v2 , 3v3, 4v4, etc..

    private ButtonGroup bg1;
    private JRadioButton pve;
    private JRadioButton pvp;

    private ButtonGroup bg2;
    private JRadioButton equipe1animaux;
    private JRadioButton equipe2animaux;        //changer le nom pour IA quand pve séléctionné.

    private JComboBox nbIA;                     //actif que si pve séléctionné.
    private JComboBox nbNiveaux;

    private JButton save;

    private Online manager;

    public HostSettingsUI(Online manager)
    {
        super();

        this.manager = manager;

        setLayout(new GridLayout(5, 1));

        repartition = new JComboBox();
        add(repartition);

        JPanel jp2 = new JPanel();
        jp2.setLayout(new GridLayout(2, 2));
        pve = new JRadioButton("PvE");
        pvp = new JRadioButton("PvP");
        bg1 = new ButtonGroup();
        bg1.add(pve);
        bg1.add(pvp);

        equipe1animaux = new JRadioButton("Equipe 1 = animaux");
        equipe2animaux = new JRadioButton("Equipe 2 = animaux");
        bg2 = new ButtonGroup();
        bg2.add(equipe1animaux);
        bg2.add(equipe2animaux);

        jp2.add(pve);
        jp2.add(pvp);
        jp2.add(equipe1animaux);
        jp2.add(equipe2animaux);
        add(jp2);

        nbIA = new JComboBox();
        add(nbIA);

        nbNiveaux = new JComboBox();
        add(nbNiveaux);

        save = new JButton("Sauvegarder");
        add(save);

        for (int i = 1; i < 5; i++)
        {
            repartition.addItem("" + i + " vs " + i);
        }

        for (int i = 3; i < 8; i++)
        {
            nbIA.addItem(""+i+ " instances");
        }

        nbNiveaux.addItem("1 niveau");
        for (int i = 2; i < 6; i++)
        {
            nbNiveaux.addItem("" + i + " niveaux");
        }

        pve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pve.isSelected())
                {
                    equipe2animaux.setText("IA = animaux");
                    nbIA.setEnabled(true);
                }
            }
        });

        pvp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pvp.isSelected())
                {
                    equipe2animaux.setText("Equipe 2 = animaux");
                    nbIA.setEnabled(false);
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean team1animal = equipe1animaux.isSelected();
                int compo = repartition.getSelectedIndex() + 1;
                boolean modePvp = pvp.isSelected();
                int nombreNiveaux = nbNiveaux.getSelectedIndex() + 1;
                int nombreIA = nbIA.getSelectedIndex() + 3;

                manager.saveGameSettings(compo, modePvp, team1animal, nombreNiveaux, nombreIA);
            }
        });
    }
}

/***
 * | REPARTITION |
 * ° pvp ° pve
 * °equipe1 animaux ° equipe2
 * | nbIA |
 * |NbNiveaux|
 * | sauvegarder |
 */
