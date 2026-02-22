/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jetlearnsystem;

import java.awt.Color;
import java.util.List; 
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class freeuseFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(freeuseFrame.class.getName());
    
    private Turtle turtle;
    private TurtleFrame turtleFrame;
    private ProgramExecutionController controller;
    
    public freeuseFrame() {
        initComponents();
        
        linenumber lineNumberView = new linenumber(jTextArea1);
        jScrollPane1.setRowHeaderView(lineNumberView);
        
        Color baseGreen = new Color(223, 255, 214);
        Color lightGreen = new Color(245, 255, 240);
        Color borderGreen = new Color(180, 220, 190);
        Color textDark = new Color(40, 70, 60);

        // 背景グラデーション用パネル（内側だけ）
        jTextArea1 = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D g2) {
                    int w = getWidth();
                    int h = getHeight();
                    GradientPaint gp = new GradientPaint(0, 0, lightGreen, 0, h, baseGreen);
                    g2.setPaint(gp);
                    g2.fillRect(0, 0, w, h);
                }
                super.paintComponent(g);
            }
        };
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 1));
        jTextArea1.setForeground(textDark);
        jTextArea1.setCaretColor(new Color(80, 130, 100));
        jTextArea1.setOpaque(false);
    
        jScrollPane1.setViewportView(jTextArea1);
        jScrollPane1.setBorder(BorderFactory.createLineBorder(borderGreen, 2, true));
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setBackground(baseGreen);

        // 🌸 カスタムスクロールバー設定（優しい緑ベース）
        jScrollPane1.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(200, 230, 210);
                this.trackColor = new Color(240, 250, 240);
            }
            @Override
            protected JButton createDecreaseButton(int orientation) { return createInvisibleButton(); }
            @Override
            protected JButton createIncreaseButton(int orientation) { return createInvisibleButton(); }
            private JButton createInvisibleButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
            @Override
            protected Dimension getMinimumThumbSize() { return new Dimension(6, 30); }
        });

        // 背景と全体の調和
        getContentPane().setBackground(baseGreen);
        jScrollPane1.setBackground(lightGreen);
        jTextArea1.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        javax.swing.ImageIcon rawIconHome = new javax.swing.ImageIcon(getClass().getResource("/image/building_house1.png"));
        java.awt.Image resizedHome = rawIconHome.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        jButton2.setIcon(new javax.swing.ImageIcon(resizedHome));
        
        javax.swing.ImageIcon rawIcon3 = new javax.swing.ImageIcon(getClass().getResource("/image/study_wakaru_boy.png"));
        java.awt.Image resizedImg3 = rawIcon3.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        jButton4.setIcon(new javax.swing.ImageIcon(resizedImg3));
        
        javax.swing.ImageIcon rawIcon4 = new javax.swing.ImageIcon(getClass().getResource("/image/computer_programming_man.png"));
        java.awt.Image resizedImg4 = rawIcon4.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        jButton1.setIcon(new javax.swing.ImageIcon(resizedImg4));
        
        jButton2.setToolTipText("ホームへ戻る");
        jButton4.setToolTipText("JETを学習する");
        jButton1.setToolTipText("自由にプログラミングする");

        getContentPane().setBackground(new Color(223, 255, 214));
        turtleFrame = new TurtleFrame();
        turtleFrame.setExternalFeedbackArea(this.feedback);
        turtle = new Turtle();
        turtleFrame.add(turtle);
        turtlecanvas.setLayout(new java.awt.BorderLayout());
        turtlecanvas.add(turtleFrame, java.awt.BorderLayout.CENTER);
        
        // フレーム描画後に中心へ移動
        SwingUtilities.invokeLater(() -> {
            turtle.resetState();
        });

        // ボタンの状態を更新するコールバックを渡す
        controller = new ProgramExecutionController(turtle, turtleFrame, new ProgramExecutionController.ButtonStateUpdater() {
            @Override
            public void updateButtons(boolean canRun, boolean canRunAll, boolean canNext, boolean canBack, boolean canReset) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    jButton_run.setEnabled(canRun);
                    jButton_runall.setEnabled(canRunAll);
                    jButton_next.setEnabled(canNext);
                    jButton_back.setEnabled(canBack);
                    jButton_reset.setEnabled(canReset); 
                });
            }
        });
        
        turtleFrame.displayInfoMessage("JETへようこそ！コマンドを入力してください。例:前へ　１００　動く");
        
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int width = getWidth();
                int fontSize = Math.max(12, width / 40);
                int titleSize = Math.max(16, width / 25);

                java.awt.Font baseFont = new java.awt.Font("Yu Gothic UI", java.awt.Font.PLAIN, fontSize);
                java.awt.Font boldFont = new java.awt.Font("Yu Gothic UI", java.awt.Font.BOLD, fontSize);
                java.awt.Font titleFont = new java.awt.Font("Yu Gothic UI", java.awt.Font.BOLD, titleSize);

                // ボタン
                jButton1.setFont(baseFont);
                jButton2.setFont(baseFont);
                jButton4.setFont(baseFont);


                jTextField1.setFont(baseFont);
                jTextArea1.setFont(baseFont);
                
            }
        });
        setSize(1366, 740);
        //setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        turtlecanvas = new javax.swing.JPanel();
        jButton_reset = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        feedback = new javax.swing.JTextPane();
        jButton7 = new javax.swing.JButton();
        jButton_back = new javax.swing.JButton();
        jButton_next = new javax.swing.JButton();
        jButton_run = new javax.swing.JButton();
        jButton_runall = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 36)); // NOI18N
        jLabel1.setText("自由に使う");

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout turtlecanvasLayout = new javax.swing.GroupLayout(turtlecanvas);
        turtlecanvas.setLayout(turtlecanvasLayout);
        turtlecanvasLayout.setHorizontalGroup(
            turtlecanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        turtlecanvasLayout.setVerticalGroup(
            turtlecanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 565, Short.MAX_VALUE)
        );

        jButton_reset.setForeground(new java.awt.Color(255, 0, 51));
        jButton_reset.setText("やり直し");
        jButton_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_resetActionPerformed(evt);
            }
        });

        feedback.setEditable(false);
        jScrollPane2.setViewportView(feedback);

        jButton7.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jButton7.setText("!");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton_back.setText("1つ戻る");
        jButton_back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_backActionPerformed(evt);
            }
        });

        jButton_next.setText("1つ進む");
        jButton_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_nextActionPerformed(evt);
            }
        });

        jButton_run.setText("実行");
        jButton_run.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_runActionPerformed(evt);
            }
        });

        jButton_runall.setText("一気に実行");
        jButton_runall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_runallActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(turtlecanvas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton_back, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_next, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_run, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton_runall)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                .addComponent(jButton_reset))
                            .addComponent(jTextField1))
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_back, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_next, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_run, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_runall, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(turtlecanvas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new freeuseFrame().setVisible(true); // 自由に使う画面を表示
        this.dispose(); // 現在の画面を閉じる
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
         new learnFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new HomeFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_resetActionPerformed
        controller.handleResetAction();
    }//GEN-LAST:event_jButton_resetActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String helpText = 
            "📘 JET コマンド一覧\n\n" +
            "▶ 基本移動コマンド:\n" +
            "・前へ　[距離]　動く　→ カメが前に進みます。\n" +
            "・後ろへ　[距離]　動く　→ カメが後ろに下がります。\n" +
            "・右へ　[角度]　回転　→ 指定角度だけ右に向きを変えます。\n" +
            "・左へ　[角度]　回転　→ 指定角度だけ左に向きを変えます。\n" +
            "・初めへ　→ カメの位置と角度をリセットします。\n\n" +

            "▶ ペンと色の操作:\n" +
            "・色を　[色名]　に変える　→ 線の色を変更します。\n" +
            "　例）色を　あか　に変える\n" +
            "・黒 / 赤 / 緑 / 黄色 / 青 / 白　→ 色の指定が可能です。\n\n" +

            "▶ 制御構文:\n" +
            "・次を　[数値]　回繰り返す [ ... ]　→ 指定回数繰り返します。\n" +
            "・もし　[条件]　のとき [ ... ] 終わり　→ 条件分岐を行います。\n\n" +

            "▶ 関数・待機:\n" +
            "・関数　[名前]　を作る [ ... ] 終わり　→ 関数定義を作ります。\n" +
            "・[関数名]　を動かす　→ 定義済み関数を実行します。\n" +
            "・待機　[ミリ秒]　→ 指定時間停止します。\n\n" +

            "▶ その他:\n" +
            "・CS（初期化）や Reset ボタンでも画面をクリアできます。\n\n" +

            "💡 ヒント:\n" +
            "・全角数字（１００）も使えます。\n" +
            "・複数行まとめて実行できます。\n" +
            "・タートルを動かすコマンドを順に試してみましょう！";

        // 🎨 カスタムパネルを作成
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(223, 255, 214));

        // 📘 テキストエリア設定
        JTextArea textArea = new JTextArea(helpText);
        textArea.setFont(new Font("Meiryo", Font.PLAIN, 15));
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setForeground(new Color(0, 77, 64));

        // カスタムスクロールバーUI
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(480, 400));

        // スクロールバーの見た目を細くして柔らかくする
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(180, 200, 230);
                this.trackColor = new Color(240, 245, 250);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createInvisibleButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createInvisibleButton();
            }

            private JButton createInvisibleButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }

            @Override
            protected Dimension getMinimumThumbSize() {
                return new Dimension(6, 30);
            }
        });

        // 🐢 タイトルラベル追加
        JLabel title = new JLabel("🐢 JET コマンド ヘルプ", SwingConstants.CENTER);
        title.setFont(new Font("Rounded Mplus 1c", Font.BOLD, 20));
        title.setForeground(new Color(0, 120, 215));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        // 💬 ダイアログとして表示
        JOptionPane.showMessageDialog(
            this,
            panel,
            "JET ヘルプ",
            JOptionPane.PLAIN_MESSAGE
        );
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_backActionPerformed
        controller.handleBackAction();
    }//GEN-LAST:event_jButton_backActionPerformed

    private void jButton_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_nextActionPerformed
        controller.handleNextAction();
    }//GEN-LAST:event_jButton_nextActionPerformed

    private void jButton_runActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_runActionPerformed
        controller.handleRunAction(jTextArea1.getText());
    }//GEN-LAST:event_jButton_runActionPerformed

    private void jButton_runallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_runallActionPerformed
        controller.handleRunAllAction(jTextArea1.getText());
    }//GEN-LAST:event_jButton_runallActionPerformed
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        //java.awt.EventQueue.invokeLater(() -> new freeuseFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane feedback;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton_back;
    private javax.swing.JButton jButton_next;
    private javax.swing.JButton jButton_reset;
    private javax.swing.JButton jButton_run;
    private javax.swing.JButton jButton_runall;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel turtlecanvas;
    // End of variables declaration//GEN-END:variables
}
