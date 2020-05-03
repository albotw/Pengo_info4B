package com.generic.launcher;

import com.generic.player.Player;
import com.generic.player.PlayerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileDialog extends JDialog {
    private JList<String> profileList;
    private DefaultListModel mod;
    private JButton addProfile;
    private JButton deleteProfile;
    private JTextField profileName;
    private JButton selectProfile;
    private JButton Close;

    private Launcher l = Launcher.instance;

    private PlayerManager pm = PlayerManager.instance;

    public ProfileDialog(Frame parent, boolean modal)
    {
        super(parent, modal);

        profileList = new JList<String>();
        mod = new DefaultListModel();
        profileList.setModel(mod);

        addProfile = new JButton("Ajouter un profil");
        deleteProfile = new JButton("Supprimer un profil");
        profileName = new JTextField();
        profileName.setText("Entrez le nom du profil ici");
        selectProfile = new JButton("Séléctionner le profil");

        setLayout(new BorderLayout());
        setTitle("Gérer les profils");

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(1, 2));
        center.add(profileList);

        JPanel droite = new JPanel();
        droite.setLayout(new GridLayout(4, 1));
        droite.add(profileName);
        droite.add(addProfile);
        droite.add(deleteProfile);
        droite.add(selectProfile);

        center.add(droite);

        add(center, BorderLayout.CENTER);

        Close = new JButton("Fermer");
        add(Close, BorderLayout.SOUTH);

        Close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CloseSelected();
            }
        });

        addProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProfileSelected();
            }
        });

        deleteProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProfileSelected();
            }
        });

        selectProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectProfileSelected();
            }
        });

        refreshList();

        setSize(350, 300);
        setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void refreshList()
    {
        mod.removeAllElements();
        for (Player p : pm.getPlayers())
        {
            mod.addElement(p.getPseudo());
        }
    }

    public void CloseSelected()
    {
        this.setVisible(false);
        this.dispose();
    }

    public void addProfileSelected()
    {
        if (!profileName.getText().equals(""))
        {
            pm.addPlayer(profileName.getText());
            refreshList();
        }
    }

    public void deleteProfileSelected()
    {
        int index = profileList.getSelectedIndex();
        if (index != -1)
        {
            String pseudo = (String)mod.get(index);
            pm.removePlayer(pseudo);
            refreshList();
        }
    }

    public void selectProfileSelected()
    {
        int index = profileList.getSelectedIndex();
        if (index != -1)
        {
            String pseudo = (String)mod.get(index);
            pm.setMainProfile(pseudo);
        }
    }
}
