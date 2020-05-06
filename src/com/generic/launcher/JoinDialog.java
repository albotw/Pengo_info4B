package com.generic.launcher;

import com.generic.UI.JoinUI;

import javax.swing.*;
import java.awt.*;

public class JoinDialog extends JDialog {

    private JoinUI UI;

    public JoinDialog(Frame parent, boolean modal)
    {
        super(parent, modal);
        this.UI = new JoinUI(this);
        add(UI);

        setTitle("Rejoindre une partie");
        setSize(350, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    }

    public void connectSelected()
    {

    }

    public void closeSelected()
    {
        this.setVisible(false);
        this.dispose();
    }

    public void disconnectSelected()
    {

    }
}
