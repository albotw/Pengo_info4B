package com.generic.launcher;

import com.generic.player.Player;
import com.generic.player.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HostDialog extends JDialog {

    private JButton lancer;
    private JButton Setting;
    private JButton Join1;
    private JButton Join2;
    private JList Equipe1;
    private DefaultListModel modE1;
    private DefaultListModel modE2;
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

        modE1 = new DefaultListModel();
        modE2 = new DefaultListModel();

        Equipe1.setModel(modE1);
        Equipe2.setModel(modE2);

        lancer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l.net_lancerSelected();
            }
        });

        Setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l.net_SettingsSelected();
            }
        });

        Join1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l.net_Join1Selected();
            }
        });

        Join2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l.net_Join2Selected();
            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeSelected();
            }
        });

        setLayout(new BorderLayout());
        setTitle("Hebergement de partie");

        JPanel north = new JPanel();
        north.setLayout(new GridLayout(1,2));
        north.add(Join1);
        north.add(Join2);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(1,2));
        center.setBorder(BorderFactory.createTitledBorder("Composition des équipes"));
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

        add(north, BorderLayout.NORTH);
        add(south, BorderLayout.SOUTH);
        add(center, BorderLayout.CENTER);

        modE1.addElement("Yann");
        modE2.addElement("Wassim");

        setSize(350, 300);
        setLocationRelativeTo(null);
        toFront();
        requestFocus();
        setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void refreshTeams(ArrayList<Player> al)
    {
        modE1.removeAllElements();
        modE2.removeAllElements();

        for (Player p : al)
        {
            if (p.getTeam().equals("Team1"))
            {
                modE1.addElement(p.getPseudo());
            }
            else if (p.getTeam().equals("Team2"))
            {
                modE2.addElement(p.getPseudo());
            }
        }
    }

    public void closeSelected()
    {
        this.setVisible(false);
        this.dispose();
    }

}
