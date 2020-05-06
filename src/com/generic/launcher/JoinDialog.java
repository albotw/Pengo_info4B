package com.generic.launcher;

import com.generic.UI.PlaceHolderTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinDialog extends JDialog {
    private PlaceHolderTextField IP;
    private PlaceHolderTextField port;
    private JButton connecter;

    private JButton join1;
    private JButton join2;

    private JList equipe1;
    private JList equipe2;

    private DefaultListModel modE1;
    private DefaultListModel modE2;

    private JButton deconnecter;

    public JoinDialog(Frame parent, boolean modal)
    {
        super(parent, modal);

        IP = new PlaceHolderTextField("Adresse IP");
        port = new PlaceHolderTextField("Port");
        connecter = new JButton("Rejoindre");
        deconnecter = new JButton("Se déconnecter");
        join1 = new JButton("Rejoindre l'équipe 1");
        join2 = new JButton("Rejoindre l'équipe 2");

        equipe1 = new JList();
        equipe2 = new JList();

        modE1 = new DefaultListModel();
        modE2 = new DefaultListModel();

        equipe1.setModel(modE1);
        equipe2.setModel(modE2);

        deconnecter.setEnabled(false);
        join1.setEnabled(false);
        join2.setEnabled(false);

        connecter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deconnecter.setEnabled(true);
                join1.setEnabled(true);
                join2.setEnabled(true);

                connecter.setEnabled(false);
                IP.setEnabled(false);
                port.setEnabled(false);
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

                connecter.setEnabled(true);
                IP.setEnabled(true);
                port.setEnabled(true);
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
        south.setLayout(new GridLayout(2, 1));

        JPanel southTop = new JPanel();
        southTop.setLayout(new GridLayout(1, 2));
        southTop.add(join1);
        southTop.add(join2);

        south.add(southTop);
        south.add(deconnecter);

        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);

        setTitle("Rejoindre une partie");
        toFront();
        requestFocus();
        setSize(350, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }
}
