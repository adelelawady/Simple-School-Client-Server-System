package com.ui;

import java.util.Random;

public class CreditCard extends javax.swing.JFrame {

    private javax.swing.JTextField FrameTextFieldInterface;

    private Add AddFrameHandler;

    public void daysAdjuster() {
        this.jComboBox3.removeAllItems();
        if (this.jComboBox4.getSelectedItem().equals("January")) {
            for (int i = 1; i <= 31; i++) {
                this.jComboBox3.addItem(i);
            }
        } else if (this.jComboBox4.getSelectedItem().equals("February")) {
            for (int i = 1; i <= 29; i++) {
                this.jComboBox3.addItem(i);
            }
        } else {
            for (int i = 1; i <= 30; i++) {
                this.jComboBox3.addItem(i);
            }
        }
    }

    private Object makeObj(final String item) {
        return new Object() {

            public String toString() {
                return item;
            }
        };
    }

    public CreditCard(javax.swing.JTextField Interface, Add Add_Frame_Handler) {
        initComponents();
        jLabel6.setVisible(false);
        daysAdjuster();
        FrameTextFieldInterface = Interface;
        AddFrameHandler = Add_Frame_Handler;
        for (int i = 1950; i <= 2050; i++) {
            String Curr = "" + i;
            this.jComboBox2.addItem(makeObj(Curr));
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox4 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Full Name");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 49, -1, 26));

        jLabel2.setText("Street Address");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 115, -1, 26));

        jLabel3.setText("Expire ON");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 81, -1, 26));

        jLabel4.setText("Billing Zip Code");
        jLabel4.setToolTipText("");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 147, -1, 33));

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        getContentPane().add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 80, -1));

        jTextField1.setText("99301");
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 150, 128, -1));

        jTextField2.setText("Lawrence Hayes");
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 300, -1));

        jTextField3.setText("8156 Waterford Drive");
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 300, -1));

        jComboBox3.setSelectedItem(2);
        getContentPane().add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 62, -1));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        jComboBox4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox4ItemStateChanged(evt);
            }
        });
        jComboBox4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox4MouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jComboBox4MouseExited(evt);
            }
        });
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        jComboBox4.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBox4PropertyChange(evt);
            }
        });
        getContentPane().add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 159, -1));

        jLabel5.setText("Card Number");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, 26));

        jTextField4.setText("372863503988009");
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 300, -1));

        jButton1.setText("Check");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(136, 186, 168, -1));

        jLabel6.setText("Checking .... ");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 191, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jComboBox4PropertyChange(java.beans.PropertyChangeEvent evt) {
    }

    private void jComboBox4MouseClicked(java.awt.event.MouseEvent evt) {
    }

    private void jComboBox4MouseExited(java.awt.event.MouseEvent evt) {
    }

    private void jComboBox4ItemStateChanged(java.awt.event.ItemEvent evt) {
        daysAdjuster();
    }

    private void CheckCreditCard() {
        jLabel6.setVisible(true);
        Random xs = new Random();
        int CRandom = xs.nextInt(9999);
        jLabel6.setText("Checked :$" + CRandom);
        jButton1.setText("Submit");
        FrameTextFieldInterface.setText("$ " + CRandom);
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (jButton1.getText() == "Submit") {
            this.setVisible(false);
        } else {
            if (jTextField1.getText().length() > 0 & jTextField2.getText().length() > 0 & jTextField3.getText().length() > 0 & jTextField4.getText().length() > 0 & jComboBox2.getSelectedIndex() >= 0 & jComboBox3.getSelectedIndex() >= 0 & jComboBox4.getSelectedIndex() >= 0) {
                CheckCreditCard();
                String Data = "[Card_Number='{0}',Full_Name='{1}',Expire ON='{d='{2}'m='{3}'y='{4}'}'" + ",Street_Address='{5}',Billing_Zip_Code='{6}']";
                Data = Data.replace("{0}", jTextField4.getText());
                Data = Data.replace("{1}", jTextField2.getText());
                Data = Data.replace("{2}", jComboBox3.getSelectedItem().toString());
                Data = Data.replace("{3}", jComboBox4.getSelectedItem().toString());
                Data = Data.replace("{4}", jComboBox2.getSelectedItem().toString());
                Data = Data.replace("{5}", jTextField3.getText());
                Data = Data.replace("{6}", jTextField1.getText());
                AddFrameHandler.CreditCardData = Data;
            }
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreditCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreditCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreditCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreditCard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new CreditCard(null, null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
    

   
}
