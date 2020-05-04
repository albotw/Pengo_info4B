package com.generic.UI;

import com.generic.launcher.LeaderboardDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaderboardDialogUI extends JPanel{
    private JButton refresh;
    private JButton close;
    private JPanel center;

    private LeaderboardDialog manager;

    public LeaderboardDialogUI(LeaderboardDialog ld)
    {
        super();

        this.manager = ld;

        setLayout(new BorderLayout());

        JPanel north = new JPanel();
        center = new JPanel();
        JPanel south = new JPanel();


        north.setLayout(new GridLayout(1, 1));
        south.setLayout(new GridLayout(1, 1));

        refresh = new JButton("Actualiser");
        close = new JButton("Fermer");

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.refreshSelected();
            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.closeSelected();
            }
        });

        north.add(refresh);
        south.add(close);

        add(north, BorderLayout.NORTH);
        add(south, BorderLayout.SOUTH);
        add(center, BorderLayout.CENTER);
    }

    public JPanel getCenterPanel()
    {
        return this.center;
    }
}
