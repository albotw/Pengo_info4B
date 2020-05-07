package com.generic.launcher;

import com.generic.UI.HostUI;
import com.generic.UI.JoinUI;
import com.generic.UI.OnlineUI;
import com.generic.net.NetworkManager;
import com.generic.player.PlayerManager;
import com.generic.player.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OnlineDialog extends JDialog {

    private OnlineUI onlineUI;
    private HostUI hostUI;
    private JoinUI joinUI;
    private int stage;
    /**
     * 0 = onlineUI 1 = hostUI 2 = joinUI
     */

    private CardLayout card;
    private JPanel cardPanel;

    private NetworkManager net;

    private Launcher l = Launcher.instance;

    public OnlineDialog(Frame parent, boolean modal) {
        super(parent, modal);
        stage = 0;

        onlineUI = new OnlineUI(this);
        hostUI   = new HostUI(this);
        joinUI   = new JoinUI(this);

        //this.getContentPane().add(onlineUI);
        cardPanel = new JPanel();
        add(cardPanel);

        card = new CardLayout();
        cardPanel.setLayout(card);

        cardPanel.add(onlineUI, "0");
        cardPanel.add(hostUI, "1");
        cardPanel.add(joinUI, "2");

        card.show(cardPanel, "0");
        net = new NetworkManager(this);

        setTitle("Multijoueur");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    }

    public void addToTeam1(String pseudo)
    {

    }

    public void addToTeam2(String pseudo)
    {

    }
    public void gameStart() 
    {
        /**
         * * est appelée lorsque le netManager a reçu la commande GAME START * doit * *
         * * doit instancier une netGame
         */
    }

    public void team1Full() 
    {
        /**
         * * optionnelle, pour bloquer automatiquement le bouton de l'équipe 1
         */
    }

    public void team2Full() {
        /**
         * * optionnelle, pour bloquer automatiquement le bouton de l'équipe 2
         */
    }

    public void joinGameSelected() 
    {
        stage = 2;
        card.show(cardPanel, "2");
    }

    public void hostGameSelected() 
    {
        stage = 1;
        card.show(cardPanel, "1");
    }

    //* options uniquement valable pour l'hote
    public void startGameSelected() {

    }

    public void settingsSelected() {

    }

    public void refreshTeams(ArrayList<Player> al) {
        // UI.getModE1().removeAllElements();
        // UI.getModE2().removeAllElements();

        for (Player p : al) {
            if (p.getTeam().equals("Team1")) {
                // UI.getModE1().addElement(p.getPseudo());
            } else if (p.getTeam().equals("Team2")) {
                // UI.getModE2().addElement(p.getPseudo());
            }
        }
    }

    public void connectSelected() {
        net.connect("127.0.0.1", 8080);
    }

    public void closeSelected() {
        if (stage != 0)
        {
            card.show(cardPanel, "0");
        }
        else 
        {
            this.setVisible(false);
            this.dispose();
        }
    }

    public void join1Selected() {
        net.joinTeam1(PlayerManager.instance.getMainProfile().getPseudo());
    }

    public void join2Selected() {
        net.joinTeam2(PlayerManager.instance.getMainProfile().getPseudo());
    }

    public void disconnectSelected() {
        net.disconnect();
    }

    public void CloseSelected() {
        this.setVisible(false);
        this.dispose();
    }

    public NetworkManager getNm() {
        return net;
    }

    public void setNm(NetworkManager nm) {
        this.net = nm;
    }
}
