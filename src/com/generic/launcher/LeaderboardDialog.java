package com.generic.launcher;

import com.generic.gameplay.Leaderboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaderboardDialog extends JDialog{
    private Leaderboard l = Launcher.instance.getLeaderboard();

    private JButton refresh;
    private JButton close;
    private JPanel center;

    public LeaderboardDialog(Frame parent, boolean modal)
    {
        super(parent, modal);

        setLayout(new BorderLayout());

        JPanel north = new JPanel();
        center = new JPanel();
        JPanel south = new JPanel();

        north.setLayout(new GridLayout(1, 1));
        center.setLayout(new GridLayout(l.getLadder().size(), 3));
        south.setLayout(new GridLayout(1, 1));

        refresh = new JButton("Actualiser");
        close = new JButton("Fermer");

        for (int i = 0; i < l.getLadder().size(); i++)
        {
            //JLabel tmp = new JLabel(i + " | " + l.getLadder().get(i).getPseudo() + " | " + l.getLadder().get(i).getScore());
            JLabel index = new JLabel(""+(i+1), JLabel.CENTER);
            JLabel pseudo = new JLabel(l.getLadder().get(i).getPseudo(), JLabel.CENTER);
            JLabel score = new JLabel(""+ l.getLadder().get(i).getScore(), JLabel.CENTER);
            center.add(index);
            center.add(pseudo);
            center.add(score);
        }

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });

        north.add(refresh);
        south.add(close);

        add(north, BorderLayout.NORTH);
        add(south, BorderLayout.SOUTH);
        add(center, BorderLayout.CENTER);

        setTitle("Leaderboard");
        setSize(350, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void refresh()
    {
        l.pull();

        center.removeAll();
        center.setLayout(new GridLayout(l.getLadder().size(), 3));

        for (int i = 0; i < l.getLadder().size(); i++)
        {
            //JLabel tmp = new JLabel(i + " | " + l.getLadder().get(i).getPseudo() + " | " + l.getLadder().get(i).getScore());
            JLabel index = new JLabel(""+(i+1), JLabel.CENTER);
            JLabel pseudo = new JLabel(l.getLadder().get(i).getPseudo(), JLabel.CENTER);
            JLabel score = new JLabel(""+ l.getLadder().get(i).getScore(), JLabel.CENTER);
            center.add(index);
            center.add(pseudo);
            center.add(score);
        }

        setSize(350, 600);
        pack();
    }
}
