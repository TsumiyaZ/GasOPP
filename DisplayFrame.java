import javax.swing.*;
import java.awt.*;

public class DisplayFrame extends JFrame {
    public DisplayFrame() {
        setSize(GasConstants.WINDOW_WIDTH, GasConstants.WINDOW_HEIGHT);
        setTitle("Jewel Suite");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        getContentPane().setBackground(Color.BLACK);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
