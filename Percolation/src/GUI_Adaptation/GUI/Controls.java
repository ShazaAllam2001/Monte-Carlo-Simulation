package GUI_Adaptation.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controls extends JPanel {
    private static final Font CUSTOM_FONT = new Font("calibre", Font.PLAIN, 14);
    private static final String START = "Start";
    private static final String STOP = "Stop";

    private JTextField nInput;
    private JTextField trialsInput;
    private JButton startStop;
    private Window window;

    public Controls(Window window) {
        super();
        this.window = window;
        this.setBackground(Color.lightGray);
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 8));

        JLabel nLabel = new JLabel("Enter N");
        nLabel.setFont(CUSTOM_FONT);
        nInput = new JTextField("10");
        nInput.setFont(CUSTOM_FONT);

        JLabel trialsLabel = new JLabel("Enter Trails");
        trialsLabel.setFont(CUSTOM_FONT);
        trialsInput = new JTextField("3");
        trialsInput.setFont(CUSTOM_FONT);

        startStop = new JButton(START);
        startStop.addActionListener(new StartStopActionListener());

        this.add(nLabel);
        this.add(nInput);
        this.add(trialsLabel);
        this.add(trialsInput);
        this.add(startStop);
    }

    private class StartStopActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (startStop.getText().equals(START)) {
                startStop.setText(STOP);
                int n = Integer.parseInt(nInput.getText());
                window.startGrid(n);
            }
            else if (startStop.getText().equals(STOP)) {
                startStop.setText(START);
                window.stopGrid();
            }
        }
    }

}
