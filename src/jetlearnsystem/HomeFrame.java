/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jetlearnsystem;


import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomeFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HomeFrame.class.getName());

    public HomeFrame() {
    
        initComponents();
        
        UIManager.put("ToolTip.background", java.awt.Color.BLUE);
        UIManager.put("ToolTip.foreground", java.awt.Color.BLACK);
        UIManager.put("ToolTip.font", new java.awt.Font("MS Gothic", java.awt.Font.BOLD, 14));

        javax.swing.ImageIcon rawIcon1 = new javax.swing.ImageIcon(getClass().getResource("/image/seizu_pen.png"));
        java.awt.Image resizedImg1 = rawIcon1.getImage().getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
        jButton1.setIcon(new javax.swing.ImageIcon(resizedImg1));

        javax.swing.ImageIcon rawIcon2 = new javax.swing.ImageIcon(getClass().getResource("/image/ai_study_kikaigakusyu.png"));
        java.awt.Image resizedImg2 = rawIcon2.getImage().getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH);
        jButton2.setIcon(new javax.swing.ImageIcon(resizedImg2));

        javax.swing.ImageIcon rawIconHome = new javax.swing.ImageIcon(getClass().getResource("/image/building_house1.png"));
        java.awt.Image resizedHome = rawIconHome.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        jButton4.setIcon(new javax.swing.ImageIcon(resizedHome));

        javax.swing.ImageIcon rawIcon3 = new javax.swing.ImageIcon(getClass().getResource("/image/study_wakaru_boy.png"));
        java.awt.Image resizedImg3 = rawIcon3.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        jButton5.setIcon(new javax.swing.ImageIcon(resizedImg3));

        javax.swing.ImageIcon rawIcon4 = new javax.swing.ImageIcon(getClass().getResource("/image/computer_programming_man.png"));
        java.awt.Image resizedImg4 = rawIcon4.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
        jButton3.setIcon(new javax.swing.ImageIcon(resizedImg4));

        jButton4.setToolTipText("ホームへ戻る");
        jButton5.setToolTipText("JETを学習する");
        jButton3.setToolTipText("自由にプログラミングする");


        getContentPane().setBackground(new Color(223, 255, 214));
         
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                int width = getWidth();
                int height = getHeight();

                int fontSize = Math.max(12, width / 40);
                int smallFontSize = Math.max(10, width / 60);

                java.awt.Font baseFont = new java.awt.Font("Yu Gothic UI", java.awt.Font.PLAIN, fontSize);
                java.awt.Font boldFont = new java.awt.Font("Yu Gothic UI", java.awt.Font.BOLD, fontSize);
                java.awt.Font titleFont = new java.awt.Font("Yu Gothic UI", java.awt.Font.BOLD, fontSize + 8);

                jButton1.setFont(boldFont);
                jButton2.setFont(boldFont);
                jButton3.setFont(baseFont);
                jButton4.setFont(baseFont);
                jButton5.setFont(baseFont);
                jButton7.setFont(baseFont);

                jLabel1.setFont(baseFont);
                jLabel2.setFont(baseFont);
                jLabel3.setFont(titleFont);
            }
        });
        setSize(1366, 740);
        //setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setFont(new java.awt.Font("Yu Gothic UI", 1, 36)); // NOI18N
        jButton1.setText("自由に使う");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Yu Gothic UI", 1, 36)); // NOI18N
        jButton2.setText("JETを学習する");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel1.setText("自由にプログラミングができるモード");

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel2.setText("用意された問題を解き進めるモード");

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 1, 36)); // NOI18N
        jLabel3.setText("ホーム");

        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jButton7.setText("!");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(28, 28, 28))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new freeuseFrame().setVisible(true); // 自由に使う画面を表示
        this.dispose(); // 現在の画面を閉じる      
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new learnFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new HomeFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         new freeuseFrame().setVisible(true); // 自由に使う画面を表示
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        new learnFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new HomeFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
    


