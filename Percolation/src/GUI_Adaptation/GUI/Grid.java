package GUI_Adaptation.GUI;

import GUI_Adaptation.Algorithm.Percolation;
import edu.princeton.cs.algs4.StdRandom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Grid extends JPanel {
    public int n;
    private final int xOrigin = 30;
    private final int yOrigin = 10;
    private final int WH = 550; //  grid width and height in pixels
    private int boxSize;
    private int gap;
    private Percolation percolation;
    private Window window;
    public Timer timer;

    public Grid(Window window, int n) {
        super();
        this.window = window;
        //this.setBackground(Color.RED);
        this.setBorder(BorderFactory.createMatteBorder(0, 15, 0, 15, Color.lightGray));
        setParameters(n);
    }

    private void setParameters(int n) {
        if (n > 0) {
            this.n = n;
            boxSize = (int) Math.floor(0.9 * WH / n);
            gap = (int) Math.floor(0.1 * WH / (n - 1));
            percolation = new Percolation(n);
            timer = new Timer(50, new UpdateGridAction());
        }
    }

    public void updateGrid(int n) {
        setParameters(n);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int row = 1, col;
        for (int x = xOrigin; x < WH + xOrigin; x += boxSize + gap) {
            col = 1;
            for (int y = yOrigin; y < WH + yOrigin; y += boxSize + gap) {
                if (!percolation.isOpen(row, col)) {
                    g.setColor(Color.BLACK);
                }
                else {
                    if (!percolation.isFull(row, col))
                        g.setColor(Color.WHITE);
                    else
                        g.setColor(Color.BLUE);
                }
                g.fillRect(x, y, boxSize, boxSize);
                col++;
            }
            row++;
        }
    }

    private class UpdateGridAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!percolation.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                percolation.open(row, col);
                repaint();
            }
            else {
                ((Timer) e.getSource()).stop();
                double pResult = ((double) percolation.numberOfOpenSites()) / (n * n);
                window.updateResult("p* = " + pResult);
            }
        }
    }

}
