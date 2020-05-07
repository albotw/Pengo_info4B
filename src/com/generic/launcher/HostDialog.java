package com.generic.launcher;

import com.generic.UI.HostUI;
import com.generic.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HostDialog extends JDialog {

    private Launcher l = Launcher.instance;
    private HostUI UI;
    private OnlineDialog manager;

    public HostDialog(Frame parent, boolean modal, OnlineDialog od)
    {
        super(parent,modal);

        this.manager = od;

        this.UI = new HostUI(this);
        add(UI);

        setTitle("Hebergement de partie");
        UI.getModE1().addElement("Yann");
        UI.getModE2().addElement("Wassim");

        setSize(350, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void startGameSelected()
    {

    }

    public void settingsSelected()
    {

    }

    public void join1Selected()
    {

    }

    public void join2Selected()
    {

    }

    public void refreshTeams(ArrayList<Player> al)
    {
        UI.getModE1().removeAllElements();
        UI.getModE2().removeAllElements();

        for (Player p : al)
        {
            if (p.getTeam().equals("Team1"))
            {
                UI.getModE1().addElement(p.getPseudo());
            }
            else if (p.getTeam().equals("Team2"))
            {
                UI.getModE2().addElement(p.getPseudo());
            }
        }
    }

    public void closeSelected()
    {
        this.setVisible(false);
        this.dispose();
    }

}
