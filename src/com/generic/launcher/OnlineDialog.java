package com.generic.launcher;

import com.generic.player.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlineDialog extends JDialog {

    private JButton joinGame;
    private JButton hostGame;
    private JButton Close;

    private Launcher l = Launcher.instance;

    public OnlineDialog(Frame parent, boolean modal)
    {
        super(parent,modal);

        joinGame = new JButton("Se connecter à une partie");
        hostGame = new JButton("Héberger une partie");
        Close = new JButton("Fermer");

        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                JoinDialog jd = new JoinDialog(l, true);
                setVisible(true);
            }
        });
        hostGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                HostDialog hd = new HostDialog(l, true);
                setVisible(true);
            }
        });

        setLayout(new BorderLayout());
        setTitle("Multijoueur");

        JPanel choix = new JPanel();
        choix.setBorder(BorderFactory.createTitledBorder("Que voulez vous faire ?"));
        choix.setLayout(new GridLayout(1,2));
        choix.add(joinGame);
        choix.add(hostGame);

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
