package paoo;

import javax.swing.*;
import java.awt.*;

public class SimpleFrame extends JFrame{

    public SimpleFrame(int WIDTH, int HEIGHT, int SCALE, String NAME) {
        setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void stopFrame() {
        dispose();
    }

}