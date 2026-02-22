// --- Modification History ---
// Modified by Ryoma Ohno・Raiki Yoshino, Hosei University, 2026

// Turtle Graphics Library extension for teaching OO concepts
// by Hideki Tsuiki, Kyoto University
// as a modification of Tatsuya Hagino's Turtle Graphics Library
// Copyright (C) 2006, Hideki Tsuiki, Kyoto University
// Copyright (C) 2000, Hideki Tsuiki, Kyoto University

// Turtle Graphics Library for Information Processing I
// Copyright (C) 1998, Tatsuya Hagino, Keio University
//
// Permission to use, copy, modify, and distribute this software
// for educational purpose only is hereby granted, provided that
// the above copyright notice appear in all copies and that both
// the copyright notice and this permission notice appear in
// supporting documentation.  This software is provided "as is"
// with no warranty.

package jetlearnsystem;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.lang.Math;
import java.util.Vector;
import java.util.function.Consumer;


public class TurtleFrame extends JPanel implements ActionListener, ItemListener {
    JFrame frame;
    JRadioButtonMenuItem[] speedMenu;
    static String[] speedMenuString = {"no turtle", "fast", "normal", "slow"};

    Vector<Line> history  = new Vector<Line>(10);
    Vector<Line> ghostHistory = new Vector<Line>(10);
    Vector<Turtle> turtles = new Vector<Turtle>(10);
    
    boolean mesh= false;
    
    private JTextPane externalFeedbackArea = null;

    private JTextArea feedbackArea;
    private JScrollPane feedbackScrollPane;
    private JTextField commandInput;
    private Consumer<String> commandProcessor;
    
    private double scale = 1.0;
    private int translateX = 0;
    private int translateY = 0;

    private final Object paintLock = new Object();
    private volatile boolean paintCompleted = false; 

    public void setExternalFeedbackArea(JTextPane textArea) {
        this.externalFeedbackArea = textArea;
    }
    
    public TurtleFrame(int width, int height) {
        super();
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.white);
        setForeground(Color.black);

        frame = new JFrame("Turtle");
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setLayout(new BorderLayout());

        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File", true);
        menubar.add(file);
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(this);
        quit.setActionCommand("quit");
        file.add(quit);

        JMenu speed = new JMenu("Speed", true);
        menubar.add(speed);
        speedMenu = new JRadioButtonMenuItem[speedMenuString.length];
        ButtonGroup bg = new ButtonGroup();
        for (int i = 0; i < speedMenuString.length; i++) {
            speedMenu[i] = new JRadioButtonMenuItem(speedMenuString[i], false);
            speedMenu[i].addItemListener(this);
            speed.add(speedMenu[i]);
            bg.add(speedMenu[i]);
        }
        speedMenu[Turtle.speedNormal].doClick();
        frame.setJMenuBar(menubar);

        JPanel inputPanel = new JPanel(new BorderLayout());
        JLabel inputLabel = new JLabel("コマンド入力: ");
        commandInput = new JTextField();
        commandInput.addActionListener(e -> {
            String command = commandInput.getText();
            commandInput.setText(""); 
            if (commandProcessor != null) {
                new Thread(() -> {
                    commandProcessor.accept(command);
                }).start();
            }
        });
        inputPanel.add(inputLabel, BorderLayout.WEST);
        inputPanel.add(commandInput, BorderLayout.CENTER);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        feedbackArea = new JTextArea(3, 40);
        feedbackArea.setEditable(false);
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        feedbackArea.setBackground(SystemColor.control);
        feedbackArea.setForeground(Color.BLACK);
        feedbackArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        feedbackScrollPane = new JScrollPane(feedbackArea);
        feedbackScrollPane.setPreferredSize(new Dimension(width, 70));

        Container c = frame.getContentPane();
        c.add(inputPanel, BorderLayout.NORTH);
        c.add(this, BorderLayout.CENTER);
        c.add(feedbackScrollPane, BorderLayout.SOUTH);

        addMouseWheelListener(e -> {
            if (e.isControlDown()) {
                double delta = 0.1;
                if (e.getWheelRotation() < 0)
                    scale *= (1 + delta);
                else
                    scale /= (1 + delta);

                scale = Math.max(0.2, Math.min(scale, 5.0));
                repaint();
            }
        });

        final Point[] lastMouse = {null};

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                lastMouse[0] = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                lastMouse[0] = null;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (lastMouse[0] != null) {
                    int dx = e.getX() - lastMouse[0].x;
                    int dy = e.getY() - lastMouse[0].y;
                    translateX += dx;
                    translateY += dy;
                    lastMouse[0] = e.getPoint();
                    repaint();
                }
            }
        });
        
        JPopupMenu popup = new JPopupMenu();
        JMenuItem resetItem = new JMenuItem("ズームをリセット");
        resetItem.addActionListener(e -> resetView());
        popup.add(resetItem);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        
        frame.pack();
        frame.setDefaultCloseOperation(3);
    }
    
    

    public TurtleFrame() {
        this(400,400);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.equals("quit")) System.exit(0);
    }

    public void itemStateChanged(ItemEvent e) {
        JRadioButtonMenuItem s = (JRadioButtonMenuItem)e.getItem();
        for (int i = 0; i < speedMenuString.length; i++) {
            if (s == speedMenu[i]) {
                if(i > 0) Turtle.speedAll(i);
                else Turtle.withTurtleAll = false;
                break;
            }
        }
    }

   @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();

        g2.translate(translateX, translateY);
        g2.scale(scale, scale);

        if (mesh) {
            drawmesh(g2);
        }
        
        g2.setColor(new Color(150, 150, 150, 100));
        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{3.0f, 3.0f}, 0.0f)); // 破線
        for (Line line : ghostHistory) {
            g2.drawLine(line.bx, line.by, line.ex, line.ey);
        }
        g2.setStroke(new BasicStroke(1));

        for (Line line : history) {
            g2.setColor(line.c);
            g2.drawLine(line.bx, line.by, line.ex, line.ey);
        }
        
        for (Turtle t : turtles) {
            t.show(g2);
        }

        g2.dispose();

        Graphics2D overlay = (Graphics2D) g.create();
        overlay.setFont(new Font("Yu Gothic UI", Font.BOLD, 16));
        overlay.setColor(new Color(30, 30, 30, 180));
        String zoomText = String.format("ズーム: %d%%", (int)(scale * 100));
        FontMetrics fm = overlay.getFontMetrics();
        int textWidth = fm.stringWidth(zoomText);
        overlay.drawString(zoomText, getWidth() - textWidth - 15, 25);
        overlay.dispose();

        synchronized (paintLock) {
            paintCompleted = true;
            paintLock.notifyAll();
        }
    }

   

    /**
     * 描画が完了するまで待機するメソッド。
     * このメソッドは、UIスレッド以外から呼び出されることを想定しています。
     */
    public void waitForPaintCompletion() {
        if (SwingUtilities.isEventDispatchThread()) {
            return; 
        }

        synchronized (paintLock) {
            paintCompleted = false;

            SwingUtilities.invokeLater(() -> repaint());

            long startTime = System.currentTimeMillis();
            long timeout = 5000;
            while (!paintCompleted && (System.currentTimeMillis() - startTime < timeout)) {
                try {
                    paintLock.wait(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            if (!paintCompleted) {
            }
        }
    }

    class Line {
        public int bx;
        public int by;
        public int ex;
        public int ey;
        public Color c;
        public Line(int bx, int by, int ex, int ey, Color c) {
            this.bx = bx;
            this.by = by;
            this.ex = ex;
            this.ey = ey;
            this.c = c;
        }
    }

    public void add(Turtle t) {
        turtles.addElement(t);
        t.setFrame(this);
        repaint(); 
    }

    public void remove(Turtle t) {
        turtles.removeElement(t);
        repaint();
    }

    public void clear(){
        history.clear();
        
        for (int i = 0; i < turtles.size(); i++) {
            turtles.elementAt(i).resetState();
        }

        repaint();
    }
    
    public void clearGhostHistory(){
        ghostHistory.clear();
        repaint();
    }

    void addLineElement(int xx, int yy, int x, int y, Color c) {
        history.addElement(new Line(xx,yy,x,y,c));
    }
    
    public void addGhostLineElement(int xx, int yy, int x, int y, Color c) {
        ghostHistory.addElement(new Line(xx, yy, x, y, c));
    }

    static Color meshDark = new Color(230,230,100);
    static Color meshLight = new Color(230,230,230);

    void drawmesh(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        g.setColor(meshLight);
        for(int x = 0; x < width; x+= 10){
            g.drawLine(x,0,x,height);
        }
        for(int x = 0; x < height; x+= 10){
            g.drawLine(0,x,width,x);
        }
        g.setColor(meshDark);
        for(int x = 0; x < width; x+= 50){
            g.drawLine(x,0,x,height);
        }
        for(int x = 0; x < height; x+= 50){
            g.drawLine(0,x,width,x);
        }
    }

    public void addMesh() {
        mesh = true;
        repaint();
    }

    public void closeFrame() {
        if (frame != null) {
            frame.dispose();
        }
    }
    
    public void displayMessage(String message, Color color) {
        final JTextPane targetPane = this.externalFeedbackArea;
        if (targetPane == null) return;

        SwingUtilities.invokeLater(() -> {
            try {
                StyledDocument doc = targetPane.getStyledDocument();
                SimpleAttributeSet attrs = new SimpleAttributeSet();
                StyleConstants.setForeground(attrs, color);
                doc.insertString(doc.getLength(), message + "\n", attrs);
                targetPane.setCaretPosition(doc.getLength());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void displayErrorMessage(String message) {
        displayMessage("エラー: " + message, Color.RED);
    }
    
    public void displayInfoMessage(String message) {
        displayMessage("情報: " + message, Color.BLUE);
    }

    public void clearFeedbackHistory() {
        if (SwingUtilities.isEventDispatchThread()) {
            feedbackArea.setText("");
        } else {
            SwingUtilities.invokeLater(() -> feedbackArea.setText(""));
        }
    }

    public void setCommandProcessor(Consumer<String> processor) {
        this.commandProcessor = processor;
    }
    
    /**
     * このフレームに紐付けられている最初のTurtleインスタンスを返す。
     * ExerciseControllerがタートルの最終状態にアクセスするために使用。
     * @return 最初のTurtleインスタンス
     */
    public Turtle getTurtle() {
        if (!turtles.isEmpty()) {
            return turtles.elementAt(0); 
        }
        return null;
    }
    
    /**
     * 描画された線分の総数を返す。
     * ExerciseControllerが採点ロジック（レベル2）で使用。
     * @return 描画された線分の数
     */
    public int getLineElementCount() {
        return history.size(); 
    }
        
    public void resetView() {
        scale = 1.0;
        translateX = 0;
        translateY = 0;
        repaint();
    }
}


