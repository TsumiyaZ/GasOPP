import javax.swing.*;
import java.awt.*;

public class DisplayFrame extends JFrame {
    private PanelManager panelManager;
    
    public DisplayFrame() {
        setFrame();
        setPadding(); // เรียกก่อนเพื่อ setup padding panels
        
        panelManager = new PanelManager(this);

        JPanel panel_north = panelManager.panel_north();
        JPanel panel_center = panelManager.panel_center();
        JPanel panel_south = panelManager.panel_south();
        JPanel panel_East = panelManager.panel_East();

        add(panel_south, BorderLayout.SOUTH);
        add(panel_north, BorderLayout.NORTH);
        add(panel_center, BorderLayout.CENTER);
        add(panel_East, BorderLayout.EAST);
    }

    public void setPadding() {
        // set ช่องว่างซ้ายขวาของ Frame
        JPanel panel_west = new JPanel();
        panel_west.setPreferredSize(new Dimension(40, 0));
        panel_west.setBackground(GasConstants.COLOR_WINDOW);

        add(panel_west, BorderLayout.WEST);
        
    }

    public void setFrame() {
        // set หน้าต่าง Frame
        setSize(GasConstants.WINDOW_WIDTH, GasConstants.WINDOW_HEIGHT);
        setTitle("Jewel Suite");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        setUndecorated(true);

        getContentPane().setBackground(GasConstants.COLOR_WINDOW);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
