package com.generic.launcher;

import com.generic.UI.PlaceHolderTextField;
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
    private PlaceHolderTextField profileName;
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
        profileName = new PlaceHolderTextField("Entrez le nom du profil ici");
        selectProfile = new JButton("Séléctionner le profil");

        setLayout(new BorderLayout());
        setTitle("Gérer les profils");

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(1, 2));

        JPanel profilPanel = new JPanel();
        profilPanel.setLayout(new GridLayout(1, 1));
        profilPanel.setBorder(BorderFactory.createTitledBorder("Liste des profils"));
        profilPanel.add(profileList);

        center.add(profilPanel);

        JPanel droite = new JPanel();
        droite.setLayout(new GridLayout(4, 1));
        droite.setBorder(BorderFactory.createTitledBorder("Gestion"));
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
                refreshList();
            }
        });

        refreshList();

        setSize(350, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void refreshList()
    {
        mod.removeAllElements();
        for (Player p : pm.getPlayers())
        {
            if (p == pm.getMainProfile())
            {
                mod.addElement(p.getPseudo() + " (Séléctionné)");
            }
            else
            {
                mod.addElement(p.getPseudo());
            }
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
