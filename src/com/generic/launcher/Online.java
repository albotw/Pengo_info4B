package com.generic.launcher;

import com.generic.UI.HostUI;
import com.generic.UI.JoinUI;
import com.generic.UI.OnlineUI;
import com.generic.net.multiplayer.OnlineClient;
import com.generic.net.multiplayer.NetworkManager;
import com.generic.net.multiplayer.Serveur;
import com.generic.player.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;

public class Online extends JDialog {

    private OnlineUI onlineUI;
    private HostUI hostUI;
    private JoinUI joinUI;
    private int stage;
    /**
     * 0 = onlineUI 1 = hostUI 2 = joinUI
     */
    private OnlineClient Client;
    private CardLayout card;
    private JPanel cardPanel;

    private NetworkManager net;
    private Thread networkThread;

    private DefaultListModel modE1;
    private DefaultListModel modE2;

    private Launcher l = Launcher.instance;
    private Serveur srv;

    public Online(Frame parent, boolean modal) {
        super(parent, modal);
        stage = 0;

        modE1 = new DefaultListModel();
        modE2 = new DefaultListModel();

        onlineUI = new OnlineUI(this);
        hostUI = new HostUI(this);
        joinUI = new JoinUI(this);

        // this.getContentPane().add(onlineUI);
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

    public void addToTeam1(String pseudo) {
        modE1.addElement(pseudo);

    }

    public void addToTeam2(String pseudo) {
        modE2.addElement(pseudo);
    }

    // méthode pour le client
    public void gameStart() {
        setVisible(false);
        Launcher.instance.setVisible(false);

        Client = new OnlineClient(net);
    }

    public void team1Full() {
        /**
         * * optionnelle, pour bloquer automatiquement le bouton de l'équipe 1
         */
    }

    public void team2Full() {
        /**
         * * optionnelle, pour bloquer automatiquement le bouton de l'équipe 2
         */
    }

    public void joinGameSelected() {
        stage = 2;
        card.show(cardPanel, "2");
    }

    public void hostGameSelected() {
        stage = 1;
        try {
            setTitle("Multijoueur | " + InetAddress.getLocalHost().getHostAddress());
        } catch (Exception e) {
        }
        ;
        card.show(cardPanel, "1");

        srv = new Serveur();

        net.connect("127.0.0.1", 8080);
        networkThread = new Thread(net);
        networkThread.start();
        net.sendPseudo(PlayerManager.instance.getMainProfile().getPseudo());
        net.setHost();

    }

    // * options uniquement valable pour l'hote
    public void startGameSelected() {
        net.startGame();
    }

    public void settingsSelected() {
        /**
         * Quand l'hôte clique sur réglages.
         */
    }

    public void connectSelected() {

        net.connect("127.0.0.1", 8080);
        networkThread = new Thread(net);
        networkThread.start();
        net.sendPseudo(PlayerManager.instance.getMainProfile().getPseudo());
    }

    public void closeSelected() {
        if (stage == 1) {
            net.disconnect();
            srv.stopServer();
            modE1.removeAllElements();
            modE2.removeAllElements();
        }
        if (stage != 0) {
            card.show(cardPanel, "0");
        } else {
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

    public void removeTeam1(String pseudo) {
        modE1.removeElement(pseudo);
    }

    public void removeTeam2(String pseudo) {
        modE2.removeElement(pseudo);
    }

    public void disconnectSelected() {
        net.disconnect();
        modE1.removeAllElements();
        modE2.removeAllElements();
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

    public DefaultListModel getE1() {
        return this.modE1;
    }

    public DefaultListModel getE2() {
        return this.modE2;
    }

    public OnlineClient getClient() {
        return Client;
    }
}
