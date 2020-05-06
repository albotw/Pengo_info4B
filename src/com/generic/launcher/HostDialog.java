package com.generic.launcher;

import javax.swing.*;
import java.awt.*;

public class HostDialog extends JDialog {

    private JButton lancer;
    private JButton Setting;
    private JButton Join1;
    private JButton Join2;
    private JList Equipe1;
    private JList Equipe2;
    private JButton close;

    private Launcher l = Launcher.instance;

    public HostDialog(Frame parent, boolean modal)
    {
        super(parent,modal);

        lancer = new JButton("Lancer la partie");
        Setting = new JButton("Paramètres");
        Join1 = new JButton("Rejoindre Equipe1");
        Join2 = new JButton("Rejoindre Equipe2");
        Equipe1 = new JList();
        Equipe2 = new JList();
        close = new JButton("Fermer");

        setLayout(new BorderLayout());
        setTitle("Hebergement de partie");

        JPanel north = new JPanel();
        north.setLayout(new GridLayout(1,2));
        north.add(Join1);
        north.add(Join2);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(1,2));
        center.add(Equipe1);
        center.add(Equipe2);

        JPanel south = new JPanel();
        JPanel southTop = new JPanel();
        southTop.setLayout(new GridLayout(1,2));
        southTop.add(lancer);
        southTop.add(Setting);
        south.setLayout(new GridLayout(2,1));
        south.add(southTop);
        south.add(close);






    }



}
