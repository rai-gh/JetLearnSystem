// Jet Learn System
// Copyright (C) 2026, Ryoma Ohno・Raiki Yoshino, Hosei University

// This software uses the following libraries
// Turtle.java
// Copyright (C) 2000, Hideki Tsuiki, Kyoto University
// Copyright (C) 1998, Tatsuya Hagino, Keio University
// TurtleFrame.java
// Copyright (C) 2000, Hideki Tsuiki, Kyoto University
// Copyright (C) 2006, Hideki Tsuiki, Kyoto University
// Copyright (C) 1998, Tatsuya Hagino, Keio University

// Permission to use, copy, modify, and distribute this software
// for educational purpose only is hereby granted, provided that
// the above copyright notice appear in all copies and that both
// the copyright notice and this permission notice appear in
// supporting documentation.  This software is provided "as is"
// with no warranty.

package jetlearnsystem;

public class JetLearnSystem {

    public static void main(String[] args) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(JetLearnSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new HomeFrame().setVisible(true);
        });
    }
    
}