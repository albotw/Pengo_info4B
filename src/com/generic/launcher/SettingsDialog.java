package com.generic.launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsDialog extends JDialog {

    private JComboBox NbIA;
    private JRadioButton PenguinControl;
    private JRadioButton AnimalControl;
    private JComboBox NbNiveau;
    private JTextField text;
    private JButton close;

    private Launcher L = Launcher.instance;

    public SettingsDialog(Frame parent, boolean modal)
    {
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
        center.setLayout(new GridLayout(3,1));

        JPanel paramsIA = new JPanel();
        paramsIA.setLayout(new GridLayout(1, 1));
        paramsIA.setBorder(BorderFactory.createTitledBorder("Réglages de l'IA"));
        paramsIA.add(NbIA);

        center.add(paramsIA);

        JPanel control = new JPanel();
        control.setBorder(BorderFactory.createTitledBorder("Contrôle"));
        control.setLayout(new GridLayout(1,2));
        control.add(AnimalControl);
        control.add(PenguinControl);

        center.add(control);

        JPanel paramsNiveau = new JPanel();
        paramsNiveau.setLayout(new GridLayout(1, 1));
        paramsNiveau.setBorder(BorderFactory.createTitledBorder("Réglages des niveaux"));
        paramsNiveau.add(NbNiveau);

        center.add(paramsNiveau);

        JPanel south = new JPanel();
        south.setLayout(new GridLayout(1,1));
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

    public void fillUI()
    {
        PenguinControl.setSelected(true);
        AnimalControl.setSelected(false);
        NbIA.addItem("3 instances");
        NbIA.addItem("4 instances");
        NbIA.addItem("5 instances");
        NbIA.addItem("6 instances");
        NbIA.addItem("7 instances");

        NbNiveau.addItem("1 niveau");
        NbNiveau.addItem("2 niveaux");
        NbNiveau.addItem("3 niveaux");
        NbNiveau.addItem("4 niveaux");
        NbNiveau.addItem("5 niveaux");
    }

    public void CloseSelected()
    {
        this.setVisible(false);
        this.dispose();
    }
}
