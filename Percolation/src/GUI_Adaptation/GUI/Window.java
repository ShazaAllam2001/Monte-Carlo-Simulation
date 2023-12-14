package GUI_Adaptation.GUI;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private final Grid grid;
    private JLabel result;

    public Window() {
        super();
        this.setTitle("Monte Carlo Simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.lightGray);
        this.setSize(1000, 700);
        this.setLayout(new BorderLayout());

        Controls controls = new Controls(this);
        grid = new Grid(this, 10);
        createResult();
        this.add(controls, BorderLayout.NORTH);
        this.add(grid, BorderLayout.CENTER);
        this.add(result, BorderLayout.SOUTH);
    }

    private void createResult() {
        result = new JLabel();
        //result.setBackground(Color.lightGray);
        result.setFont(new Font("serif", Font.PLAIN, 20));
        result.setHorizontalAlignment(SwingConstants.RIGHT);
        result.setBorder(BorderFactory.createMatteBorder(10, 15, 10, 15, Color.lightGray));
    }

    public void updateResult(String text) {
        result.setText(text);
    }

    public void startGrid(int n) {
        grid.updateGrid(n);
        grid.timer.start();
    }

    public void stopGrid() {
        grid.timer.stop();
    }

    public static void main(String[] args) {
        Window window = new Window();
        window.setVisible(true);
    }
    
}
