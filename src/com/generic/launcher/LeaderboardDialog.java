package com.generic.launcher;

import com.generic.UI.LeaderboardDialogUI;
import com.generic.gameplay.Leaderboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaderboardDialog extends JDialog{
    private Leaderboard l = Launcher.instance.getLeaderboard();
    private LeaderboardDialogUI UI;

    public LeaderboardDialog(Frame parent, boolean modal)
    {
        super(parent, modal);

        UI = new LeaderboardDialogUI(this);

        refreshSelected();

        add(UI);
        setTitle("Leaderboard");
        setSize(350, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void refreshSelected()
    {
        l.push();
        l.pull();

        JPanel center = UI.getCenterPanel();

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

        pack();
        setSize(350, 600);
    }

    public void closeSelected()
    {
        setVisible(false);
        dispose();
    }


}
