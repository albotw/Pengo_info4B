package com.generic.launcher;

import com.generic.player.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlineDialog extends JDialog {

    private JButton SearchParty;
    private JButton HebergerParty;
    private JButton Close;

    private Launcher l = Launcher.instance;

    private PlayerManager pm = PlayerManager.instance;

    public OnlineDialog(Frame parent, boolean modal)
    {
        super(parent,modal);

        SearchParty = new JButton("Se connecter une partie");
        HebergerParty = new JButton("Heberger une partie");
        Close = new JButton("Fermer");

        SearchParty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                JoinDialog jd = new JoinDialog(l.instance, true);
                setVisible(true);
            }
        });
        HebergerParty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                HostDialog hd = new HostDialog(l.instance, true);
                setVisible(true);
            }
        });

        setLayout(new BorderLayout());
        setTitle("Gérer les réglages");

        JPanel choix = new JPanel();
        choix.setBorder(BorderFactory.createTitledBorder("Choix Heberger/Rejoindre"));
        choix.setLayout(new GridLayout(1,2));
        choix.add(SearchParty);
        choix.add(HebergerParty);

        JPanel south = new JPanel();
        south.setLayout(new GridLayout(1,1));
        south.add(Close);

        add(choix,BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        Close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CloseSelected();
            }
        });

        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    }

    public void CloseSelected()
    {
        this.setVisible(false);
        this.dispose();
    }



}
