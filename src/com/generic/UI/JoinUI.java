package com.generic.UI;

import com.generic.launcher.Online;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinUI extends JPanel {
    private PlaceholderTextField IP;
    private PlaceholderTextField port;
    private JButton connecter;

    private JButton join1;
    private JButton join2;

    private JList equipe1;
    private JList equipe2;

    private DefaultListModel modE1;
    private DefaultListModel modE2;

    private JButton deconnecter;

    private JButton close;

    private Online manager;

    public JoinUI(Online od) {
        super();

        this.manager = od;

        setLayout(new BorderLayout());

        IP = new PlaceholderTextField("Adresse IP");
        port = new PlaceholderTextField("Port");
        connecter = new JButton("Rejoindre");
        deconnecter = new JButton("Se déconnecter");
        join1 = new JButton("Rejoindre l'équipe 1");
        join2 = new JButton("Rejoindre l'équipe 2");
        close = new JButton("Fermer");
        equipe1 = new JList();
        equipe2 = new JList();
        modE1 = manager.getE1();
        modE2 = manager.getE2();

        equipe1.setModel(modE1);
        equipe2.setModel(modE2);

        deconnecter.setEnabled(false);
        join1.setEnabled(false);
        join2.setEnabled(false);

        join1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                join2.setEnabled(true);
                manager.join1Selected();
                join1.setEnabled(false);
            }
        });

        join2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                join1.setEnabled(true);
                manager.join2Selected();
                join2.setEnabled(false);
            }
        });

        connecter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!IP.getText().equals("") && !port.getText().equals("") && !IP.getText().equals(IP.getPHText()) && !port.getText().equals(port.getPHText())) {
                    manager.connectSelected(IP.getText(), Integer.parseInt(port.getText()));
                }
            }
        });

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.closeSelected();
            }
        });

        deconnecter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modE1.removeAllElements();
                modE2.removeAllElements();
                join1.setEnabled(false);
                join2.setEnabled(false);
                IP.flush();
                port.flush();
                deconnecter.setEnabled(false);
                close.setEnabled(true);

                connecter.setEnabled(true);
                IP.setEnabled(true);
                port.setEnabled(true);

                manager.disconnectSelected();
            }
        });

        JPanel north = new JPanel();
        north.setLayout(new GridLayout(1, 3));
        north.setBorder(BorderFactory.createTitledBorder("Connexion"));
        north.add(IP);
        north.add(port);
        north.add(connecter);

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(1, 2));
        center.setBorder(BorderFactory.createTitledBorder("Composition des équipes"));
        center.add(equipe1);
        center.add(equipe2);

        JPanel south = new JPanel();
        south.setLayout(new GridLayout(3, 1));

        JPanel southTop = new JPanel();
        southTop.setLayout(new GridLayout(1, 2));
        southTop.add(join1);
        southTop.add(join2);

        south.add(southTop);
        south.add(deconnecter);
        south.add(close);

        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);
    }

    public void connected() {
        deconnecter.setEnabled(true);
        join1.setEnabled(true);
        join2.setEnabled(true);
        connecter.setEnabled(false);
        IP.setEnabled(false);
        port.setEnabled(false);
        close.setEnabled(false);
    }


    public JButton getClose() {
        return close;
    }

    public void setClose(JButton close) {
        this.close = close;
    }
}
