/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jetlearnsystem;

/**
 *
 * @author Ryomario1555
 */

import javax.swing.*;
import javax.swing.text.Element;
import java.awt.*;

public class linenumber extends JComponent {
    private final JTextArea textArea;
    private final Font font = new Font("Monospaced", Font.PLAIN, 14);

    public linenumber(JTextArea textArea) {
        this.textArea = textArea;
        this.textArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { repaint(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { repaint(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { repaint(); }
        });

        this.textArea.addCaretListener(e -> repaint());

        setFont(font);
        setForeground(Color.DARK_GRAY);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        FontMetrics fm = g.getFontMetrics(getFont());
        int fontHeight = fm.getHeight();
        int currentLine = textArea.getDocument().getDefaultRootElement().getElementIndex(textArea.getCaretPosition()) + 1;

        Rectangle drawHere = g.getClipBounds();
        int startOffset = textArea.viewToModel2D(new Point(0, drawHere.y));
        int endOffset = textArea.viewToModel2D(new Point(0, drawHere.y + drawHere.height));

        Element root = textArea.getDocument().getDefaultRootElement();
        int startLine = root.getElementIndex(startOffset);
        int endLine = root.getElementIndex(endOffset);

        for (int line = startLine; line <= endLine; line++) {
            String lineNumber = Integer.toString(line + 1);
            Element lineElement = root.getElement(line);
            try {
                Rectangle r = textArea.modelToView2D(lineElement.getStartOffset()).getBounds();
                int y = r.y + r.height - 4;
                g.drawString(lineNumber, 5, y);
            } catch (Exception ex) {
                // 無視してOK
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(40, textArea.getHeight());
    }
}
