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

        SearchParty = new JButton("Chercher une partie");
        HebergerParty = new JButton("Heberger une partie");
        Close = new JButton("Fermer");


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

        setSize(350, 300);
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
