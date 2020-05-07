package com.generic.launcher;

import com.generic.net.NetworkManager;
import com.generic.player.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlineDialog extends JDialog {

    private JButton joinGame;
    private JButton hostGame;
    private JButton Close;

    private HostDialog hd;
    private JoinDialog jd;

    private NetworkManager nm;

    private Launcher l = Launcher.instance;

    public OnlineDialog(Frame parent, boolean modal)
    {
        super(parent,modal);

        nm = new NetworkManager(this);

        joinGame = new JButton("Se connecter à une partie");
        hostGame = new JButton("Héberger une partie");
        Close = new JButton("Fermer");
        jd = new JoinDialog(l, false, this);
        hd = new HostDialog(l, false, this);

        jd.setVisible(false);
        hd.setVisible(false);

        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                jd.setVisible(true);
                setVisible(true);
            }
        });
        hostGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                hd.setVisible(true);
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

    public void addToTeam1(String pseudo)
    {
        jd.addToTeam1(pseudo);
    }

    public void addToTeam2(String pseudo)
    {
        jd.addToTeam2(pseudo);
    }

    public void gameStart()
    {

    }

    public void team1Full()
    {

    }

    public void team2Full()
    {

    }

    public void CloseSelected()
    {
        this.setVisible(false);
        this.dispose();
    }

    public NetworkManager getNm() {
        return nm;
    }

    public void setNm(NetworkManager nm) {
        this.nm = nm;
    }
}
