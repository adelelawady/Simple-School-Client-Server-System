/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

/**
 *
 * @author adelElawady
 */
import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import javax.swing.border.*;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ColoredPanel extends JTextPane {

    //private JPanel topPanel;
  //  private JTextPane tPane;

    public ColoredPanel() {
       

        EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));

     
        this.setBorder(eb);
        
        //tPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        this.setMargin(new Insets(5, 5, 5, 5));

        //topPanel.add(this);

//        appendToPane(this, "Elkonsol Applications", Color.RED);
//        appendToPane(this, "test Blue", Color.BLUE);
//        appendToPane(this, "Test", Color.DARK_GRAY);
//        appendToPane(this, "adel", Color.MAGENTA);
//        appendToPane(this, "ali", Color.ORANGE);

    //    getContentPane().add(topPanel);

     //   pack();
        setVisible(true);
    }

    public void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
        tp.setCaretPosition(tp.getDocument().getLength());
    }

  
}

    

