package jetlearnsystem;

import java.awt.Color;
import javax.swing.JOptionPane;
import java.io.InputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.util.List; 
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JOptionPane;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

public class DrawingFrame extends javax.swing.JFrame 
    implements ExerciseController.ExerciseUIUpdater
{
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DrawingFrame.class.getName());
    
    private Turtle turtle;
    private TurtleFrame turtleFrame;
    private ProgramExecutionController controller;
    private ExerciseController exerciseController; 
    private int currentIndex = 1; 

    

    public DrawingFrame() {
    initComponents();  
    
    linenumber lineNumberView = new linenumber(jTextArea3);
    jScrollPane4.setRowHeaderView(lineNumberView);
    
    Color baseGreen = new Color(223, 255, 214);
    Color lightGreen = new Color(245, 255, 240);
    Color borderGreen = new Color(180, 220, 190);
    Color textDark = new Color(40, 70, 60);

    jTextArea3 = new JTextArea() {
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
    
    
    // 1. jTextArea1 自体の設定
    jTextArea1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
    jTextArea1.setForeground(textDark);
    jTextArea1.setOpaque(false);
    jTextArea1.setBackground(lightGreen);
    jTextArea1.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    jTextArea3.setLineWrap(true);
    jTextArea3.setWrapStyleWord(true);
    jTextArea3.setFont(new Font("Yu Gothic UI", Font.PLAIN, 15));
    jTextArea3.setForeground(textDark);
    jTextArea3.setCaretColor(new Color(80, 130, 100));
    jTextArea3.setOpaque(false);
    
    jScrollPane4.setViewportView(jTextArea3);
    jScrollPane4.setBorder(BorderFactory.createLineBorder(borderGreen, 2, true));
    jScrollPane4.setOpaque(false);
    jScrollPane4.getViewport().setOpaque(false);
    jScrollPane4.setBackground(baseGreen);

    jScrollPane4.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
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

    getContentPane().setBackground(baseGreen);
    jScrollPane4.setBackground(lightGreen);
    jTextArea3.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

    jLabel1.setForeground(new Color(60, 100, 80));
    jLabel1.setFont(new Font("Rounded Mplus 1c", Font.BOLD, 36));

    getContentPane().setBackground(new Color(223, 255, 214));

    turtleFrame = new TurtleFrame();
    turtleFrame.setExternalFeedbackArea(this.feedback);
    turtle = new Turtle();
    turtleFrame.add(turtle);
    turtlecanvas.setLayout(new java.awt.BorderLayout());
    turtlecanvas.add(turtleFrame, java.awt.BorderLayout.CENTER);

    SwingUtilities.invokeLater(() -> {
        turtle.resetState();
    });
    
    this.controller = new ProgramExecutionController(turtle, turtleFrame, new ProgramExecutionController.ButtonStateUpdater() {
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
    this.exerciseController = new ExerciseController(this.controller, turtleFrame, this, this);

    String startId = this.exerciseController.getStartProblemId();
    this.exerciseController.startExercise(startId);

    turtleFrame.displayInfoMessage("JETへようこそ！コマンドを入力してください。例:前へ　１００　動く");
    javax.swing.ImageIcon rawIconHome = new javax.swing.ImageIcon(getClass().getResource("/image/building_house1.png"));
    java.awt.Image resizedHome = rawIconHome.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    jButton1.setIcon(new javax.swing.ImageIcon(resizedHome));

    javax.swing.ImageIcon rawIcon4 = new javax.swing.ImageIcon(getClass().getResource("/image/computer_programming_man.png"));
    java.awt.Image resizedImg4 = rawIcon4.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    jButton2.setIcon(new javax.swing.ImageIcon(resizedImg4));

    javax.swing.ImageIcon rawIcon3 = new javax.swing.ImageIcon(getClass().getResource("/image/study_wakaru_boy.png"));
    java.awt.Image resizedImg3 = rawIcon3.getImage().getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    jButton3.setIcon(new javax.swing.ImageIcon(resizedImg3));

    jButton1.setToolTipText("ホームへ戻る");
    jButton3.setToolTipText("JETを学習する");
    jButton2.setToolTipText("自由にプログラミングする");

    setSize(1366, 740);
    
    // 閉じるボタンを押してもすぐには閉じない設定にする
    setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);

    addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            int res = JOptionPane.showConfirmDialog(DrawingFrame.this, 
                "演習を終了してホームに戻りますか？（保存していない進捗は失われます）", 
                "終了確認", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                new HomeFrame().setVisible(true);
                dispose();
            }
        }
    });
    
    revalidate();
    repaint();
    }
    
    private void nextQuestion() {
    }

    private void prevQuestion() {
    }
       
    public void startFromProblem(String id) {
    if (exerciseController != null) {
        exerciseController.startExercise(id);
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton_reset = new javax.swing.JButton();
        turtlecanvas = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton_back = new javax.swing.JButton();
        jButton_next = new javax.swing.JButton();
        jButton_run = new javax.swing.JButton();
        jButton_runall = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        feedback = new javax.swing.JTextPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton_check = new javax.swing.JButton();

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

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton_reset.setForeground(new java.awt.Color(255, 0, 0));
        jButton_reset.setText("やり直し");
        jButton_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_resetActionPerformed(evt);
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
            .addGap(0, 216, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel1.setText("はじめから");

        jButton5.setText("!");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
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

        jScrollPane3.setViewportView(feedback);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane4.setViewportView(jTextArea3);

        jButton4.setText("保存して終了");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setText("前へ");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("次へ");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton_check.setText("採点");
        jButton_check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_checkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addGap(14, 14, 14)))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jButton_back, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_next, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_run, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_runall)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_check)
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(jButton_reset))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(turtlecanvas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton_back, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton_next, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton_run, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton_runall, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton_check, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(turtlecanvas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3))
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_resetActionPerformed
    controller.handleResetAction();
    }//GEN-LAST:event_jButton_resetActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int res = JOptionPane.showConfirmDialog(this, "ホームに戻りますか？", "確認", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            new HomeFrame().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int res = JOptionPane.showConfirmDialog(this, "学習選択画面に戻りますか？", "確認", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            new learnFrame().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int res = JOptionPane.showConfirmDialog(this, "自由に使う画面に移りますか？", "確認", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            new freeuseFrame().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
                                         

    // 🌿 テーマカラー（freeuseFrameと統一）
    Color baseGreen  = new Color(223, 255, 214);
    Color textDark   = new Color(0, 77, 64);

    // ✅ ダイアログ本体パネル
    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(baseGreen);

    // タイトル
    JLabel title = new JLabel("🐢 JET ヘルプ（章ごとに見られるよ）", SwingConstants.CENTER);
    title.setFont(new Font("Rounded Mplus 1c", Font.BOLD, 22));
    title.setForeground(new Color(0,120,90));
    title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    mainPanel.add(title, BorderLayout.NORTH);

    // ✅ タブ本体
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.setFont(new Font("Yu Gothic UI", Font.BOLD, 14));

    // ==============================
    // 1章：基本移動
    // ==============================
    JTextArea chapter1 = new JTextArea();
    chapter1.setText(
        "【第1章：基本移動】\n\n" +
        "・前へ　[距離]　動く\n" +
        "・後ろへ　[距離]　動く\n" +
        "・右へ　[角度]　回る\n" +
        "・左へ　[角度]　回る\n" +
        "・初めへ\n\n" +
        "例：\n" +
        "前へ　100　動く\n" +
        "右へ　90　回る"
    );
    setupTextArea(chapter1, baseGreen, textDark);

    // ==============================
    // 2章：色とペン
    // ==============================
    JTextArea chapter2 = new JTextArea();
    chapter2.setText(
        "【第2章：色とペン】\n\n" +
        "・色を　あか　に変える\n" +
        "・色を　あお　に変える\n\n" +
        "使える色：\n" +
        "黒 / 赤 / 緑 / 黄色 / 青 / 白\n\n" +
        "・ペンを上げる\n" +
        "・ペンを下ろす"
    );
    setupTextArea(chapter2, baseGreen, textDark);

    // ==============================
    // 3章：くりかえし
    // ==============================
    JTextArea chapter3 = new JTextArea();
    chapter3.setText(
        "【第3章：くりかえし】\n\n" +
        "・次を　[数値]　回くりかえす [ ... ]\n\n" +
        "例：\n" +
        "次を　4　回くりかえす[\n" +
        "　前へ　50　動く\n" +
        "　右へ　90　回る\n" +
        "]"
    );
    setupTextArea(chapter3, baseGreen, textDark);

    // スクロール付きにする
    JScrollPane sp1 = new JScrollPane(chapter1);
    JScrollPane sp2 = new JScrollPane(chapter2);
    JScrollPane sp3 = new JScrollPane(chapter3);

    // ✅ タブに追加
    tabbedPane.addTab(" 第1章 ", sp1);
    tabbedPane.addTab(" 第2章 ", sp2);
    tabbedPane.addTab(" 第3章 ", sp3);

    // タブ背景色（付箋風）
    tabbedPane.setBackgroundAt(0, new Color(255, 249, 200)); // きいろ
    tabbedPane.setBackgroundAt(1, new Color(200, 240, 255)); // みずいろ
    tabbedPane.setBackgroundAt(2, new Color(210, 255, 210)); // みどり

    mainPanel.add(tabbedPane, BorderLayout.CENTER);

    // ✅ ダイアログサイズ大きくする
    JOptionPane optionPane = new JOptionPane(mainPanel, JOptionPane.PLAIN_MESSAGE);
    JDialog dialog = optionPane.createDialog(this, "JET ヘルプ");
    dialog.setSize(800, 500);
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}

// テキストエリアの見た目共通化
private void setupTextArea(JTextArea area, Color back, Color fore) {
    area.setFont(new Font("Yu Gothic UI", Font.PLAIN, 16));
    area.setEditable(false);
    area.setWrapStyleWord(true);
    area.setLineWrap(true);
    area.setBackground(back);
    area.setForeground(fore);
    area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_backActionPerformed
        controller.handleBackAction();
    }//GEN-LAST:event_jButton_backActionPerformed

    private void jButton_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_nextActionPerformed
        controller.handleNextAction();
    }//GEN-LAST:event_jButton_nextActionPerformed

    private void jButton_runActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_runActionPerformed
        controller.handleRunAction(jTextArea3.getText());
    }//GEN-LAST:event_jButton_runActionPerformed

    private void jButton_runallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_runallActionPerformed
        if (exerciseController != null) {
            controller.handleRunAllAction(jTextArea3.getText()); 
            turtleFrame.displayInfoMessage("実行が完了しました。キャンバスを確認してください。");
        }
    }//GEN-LAST:event_jButton_runallActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            JSONObject obj = new JSONObject();
            obj.put("last_problem_id", exerciseController.getCurrentProblemId());

            Files.write(
                Paths.get("continue.json"),
                obj.toString(2).getBytes("UTF-8")
            );

            JOptionPane.showMessageDialog(this,
                    "現在の問題を保存しました！",
                    "保存完了",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "保存に失敗しました: " + e.getMessage(),
                    "エラー",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        new learnFrame().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        prevQuestion(); // 「前へ」
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        nextQuestion(); // 「次へ」
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton_checkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_checkActionPerformed
        if (exerciseController != null) {
            exerciseController.startScoringFlow(jTextArea3.getText()); 
        }
    }//GEN-LAST:event_jButton_checkActionPerformed

    private void loadImage(String resourcePath) {
         try {
            System.out.println("Attempting to load image from resource path (Classpath): " + resourcePath);

            java.net.URL imgUrl = getClass().getResource(resourcePath); 

            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);

                javax.swing.SwingUtilities.invokeLater(() -> {

                    javax.swing.JLabel imageLabel = new javax.swing.JLabel(icon);

                    jPanel1.removeAll();
                    jPanel1.setLayout(new java.awt.BorderLayout()); 
                    jPanel1.add(imageLabel, java.awt.BorderLayout.CENTER);

                    jPanel1.revalidate(); 
                    jPanel1.repaint();
                });
            }
        } catch (Exception e) {
            turtleFrame.displayErrorMessage("画像処理エラー: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void displayProblem(ProblemDefinition problem) { 
        javax.swing.SwingUtilities.invokeLater(() -> {
            jTextArea1.setText("--- " + problem.getId() + " ---\n\n" + problem.getText());
            jTextArea1.setCaretPosition(0); 
            jTextArea1.setEditable(false);
        });

        loadImage("/jetlearnsystem/questions/image/" + problem.getImage());
        controller.handleResetAction();

        javax.swing.SwingUtilities.invokeLater(() -> {
            jLabel1.setText(problem.getId()); 
            jLabel1.setForeground(new Color(60, 100, 80));
        });
    }
    
    @Override
    public void displayResult(ScoreResult result) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            String status = result.isCorrect() ? "【正解】" : "【不正解】";
            String timeInfo = String.format("(経過時間: %.2f秒)", result.getTimeTakenMs() / 1000.0);
            
            turtleFrame.displayInfoMessage(status + result.getMessage() + timeInfo);
        });
    }

    @Override
    public void notifyExecutionStart() {
        turtleFrame.displayInfoMessage("解答コードの実行を開始します...");
    }

    @Override
    public void notifyExecutionEnd() {
        turtleFrame.displayInfoMessage("採点が完了しました。");
    }

    @Override
    public void showSelfScorePopup(String modelImage) {
        javax.swing.SwingUtilities.invokeLater(() -> {

            // 1. 模範解答図の読み込み
            String imagePath = "/jetlearnsystem/questions/image/" + modelImage; 
            ImageIcon modelIcon = null;
            try {
                java.net.URL imgUrl = DrawingFrame.class.getResource(imagePath);
                if (imgUrl != null) {
                    // 画像を適切なサイズにスケーリング
                    modelIcon = new ImageIcon(imgUrl);
                    Image img = modelIcon.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);
                    modelIcon = new ImageIcon(img);
                }
            } catch (Exception e) {
                 System.err.println("Model image load error: " + e.getMessage());
            }

            // 2. カスタムパネルの構築 (画像 + 説明 + ボタン)
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.add(new JLabel("目標図と実行結果、そして模範解答図を比較してください。"), BorderLayout.NORTH);

            // 模範解答図を表示する JLabel
            JLabel imageLabel = new JLabel(modelIcon);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // 模範解答図と自己評価ボタンを中央に配置
            JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
            centerPanel.add(imageLabel, BorderLayout.CENTER);

            panel.add(centerPanel, BorderLayout.CENTER);

            // 3. 選択ボタンの定義
            String[] options = {"🎉 正解（次の問題へ）", "💡 不正解（もう一度）"};

            int choice = JOptionPane.showOptionDialog(
                this,
                panel, 
                "【自己採点】模範解答図", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                options, 
                options[0] 
            );

            // 4. ユーザーの選択をExerciseControllerに送り返す
            if (choice == JOptionPane.YES_OPTION) { // 「正解（次の問題へ）」
                exerciseController.handleSelfScoreChoice("CORRECT"); 
            } else if (choice == JOptionPane.NO_OPTION) { // 「不正解（もう一度）」
                exerciseController.handleSelfScoreChoice("INCORRECT"); 
            }
        });
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new DrawingFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane feedback;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton_back;
    private javax.swing.JButton jButton_check;
    private javax.swing.JButton jButton_next;
    private javax.swing.JButton jButton_reset;
    private javax.swing.JButton jButton_run;
    private javax.swing.JButton jButton_runall;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JPanel turtlecanvas;
    // End of variables declaration//GEN-END:variables
}
