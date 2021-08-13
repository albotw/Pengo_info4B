package com.generic.launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.generic.gameplay.config.CONFIG_GAME.*;

public class SettingsDialog extends JDialog {

    private JComboBox NbIA;
    private JRadioButton PenguinControl;
    private JRadioButton AnimalControl;
    private JComboBox NbNiveau;
    private JButton close;

    private Launcher L = Launcher.instance;

    public SettingsDialog(Frame parent, boolean modal) {
        super(parent, modal);

        ButtonGroup bg1 = new ButtonGroup();

        NbIA = new JComboBox();
        AnimalControl = new JRadioButton("Contrôler un animal");
        PenguinControl = new JRadioButton("Contrôler un penguin");
        NbNiveau = new JComboBox();
        close = new JButton("Fermer");

        bg1.add(AnimalControl);
        bg1.add(PenguinControl);

        setLayout(new BorderLayout());
        setTitle("Paramètres");

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(3, 1));

        JPanel paramsIA = new JPanel();
        paramsIA.setLayout(new GridLayout(1, 1));
        paramsIA.setBorder(BorderFactory.createTitledBorder("Réglages de l'IA"));
        paramsIA.add(NbIA);

        center.add(paramsIA);

        JPanel control = new JPanel();
        control.setBorder(BorderFactory.createTitledBorder("Contrôle"));
        control.setLayout(new GridLayout(1, 2));
        control.add(AnimalControl);
        control.add(PenguinControl);

        center.add(control);

        JPanel paramsNiveau = new JPanel();
        paramsNiveau.setLayout(new GridLayout(1, 1));
        paramsNiveau.setBorder(BorderFactory.createTitledBorder("Réglages des niveaux"));
        paramsNiveau.add(NbNiveau);

        center.add(paramsNiveau);

        JPanel south = new JPanel();
        south.setLayout(new GridLayout(1, 1));
        south.add(close);

        add(center, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CloseSelected();
            }
        });

        fillUI();

        setSize(350, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void fillUI() {
        PenguinControl.setSelected(true);
        AnimalControl.setSelected(false);

        for (int i = 1; i <= 4; i++) {
            NbIA.addItem("" + i + " instances");
        }

        NbNiveau.addItem("1 niveau");
        for (int i = 2; i <= 5; i++) {
            NbNiveau.addItem("" + i + " niveaux");
        }
    }

    public void CloseSelected() {
        int nAI = NbIA.getSelectedIndex() + 1;
        int nNiveaux = NbNiveau.getSelectedIndex() + 1;
        boolean playerIsPenguin = PenguinControl.isSelected();

        setnAi(nAI);
        setnNiveaux(nNiveaux);
        setPlayerIsPenguin(playerIsPenguin);

        System.out.println("configuration partie: IA = " + nAI + " | niveaux = " + nNiveaux + " | Joueur <=> pingouin = " + playerIsPenguin);
        this.setVisible(false);
        this.dispose();
    }
}
