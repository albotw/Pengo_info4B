package com.generic.launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingDialog extends JDialog {

    private JComboBox addIA;
    private JRadioButton PenguinControl;
    private JRadioButton AnimalControl;
    private JComboBox NbNiveau;
    private JTextField text;
    private JButton close;

    private Launcher L = Launcher.instance;

    public SettingDialog(Frame parent, boolean modal)
    {
        super(parent, modal);

        addIA = new JComboBox();
        AnimalControl = new JRadioButton("Contrôler un animal");
        PenguinControl = new JRadioButton("Contrôler un penguin");
        NbNiveau = new JComboBox();
        close = new JButton("Fermer");

        setLayout(new BorderLayout());
        setTitle("Paramètres");

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(3,1));
        center.add(addIA);

        JPanel control = new JPanel();
        control.setLayout(new GridLayout(1,2));
        control.add(AnimalControl);
        control.add(PenguinControl);

        center.add(control);

        center.add(NbNiveau);

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

        setSize(350, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);



    }

    public void CloseSelected()
    {
        this.setVisible(false);
        this.dispose();
    }

    public void addIA(){

    }





}
