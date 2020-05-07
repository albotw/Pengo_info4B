package com.generic.launcher;

import com.generic.UI.JoinUI;
import com.generic.net.NetworkManager;
import com.generic.player.PlayerManager;

import javax.swing.*;
import java.awt.*;

public class JoinDialog extends JDialog {

    private JoinUI UI;

    private OnlineDialog manager;

    public JoinDialog(Frame parent, boolean modal, OnlineDialog od)
    {
        super(parent, modal);
        this.manager = od;
        this.UI = new JoinUI(this);
        add(UI);

        setTitle("Rejoindre une partie");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void connectSelected()
    {
        manager.getNm().connect("127.0.0.1", 8080);
    }

    public void closeSelected()
    {
        this.setVisible(false);
        this.dispose();
    }

    public void join1Selected()
    {
        manager.getNm().joinTeam1(PlayerManager.instance.getMainProfile().getPseudo());
    }

    public void join2Selected()
    {
        manager.getNm().joinTeam2(PlayerManager.instance.getMainProfile().getPseudo());
    }

    public void disconnectSelected()
    {
        manager.getNm().disconnect();
    }

    public void addToTeam1(String pseudo)
    {
        UI.addToTeam1(pseudo);
    }

    public void addToTeam2(String pseudo)
    {
        UI.addToTeam2(pseudo);
    }
}
