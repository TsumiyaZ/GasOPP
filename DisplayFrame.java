import javax.swing.*;
import java.awt.*;

public class DisplayFrame extends JFrame {
    public DisplayFrame() {
        setFrame();
        PanelManager PanelManager = new PanelManager(this);

        JPanel panel_north = PanelManager.panel_north();
        JPanel panel_center = PanelManager.panel_center();
        JPanel panel_south = PanelManager.panel_south();
        JPanel panel_East = PanelManager.panel_East();
        
        setPadding(); // ⚠ ตรงนี้จะ add panel_east เข้า BorderLayout.EAST

        add(panel_south, BorderLayout.SOUTH);
        add(panel_north, BorderLayout.NORTH);
        add(panel_center, BorderLayout.CENTER);
        add(panel_East, BorderLayout.EAST); 
        // ⚠ ตรงนี้ก็ add panel_East เข้า BorderLayout.EAST อีกครั้ง 
        // จะทำให้ panel_east จาก setPadding() โดนแทนที่ (component เดิมหาย)
    }

    public void setPadding() {
        // set ช่องว่างซ้ายขวาของ Frame
        JPanel panel_west = new JPanel();
        panel_west.setPreferredSize(new Dimension(40, 0));
        panel_west.setBackground(GasConstants.COLOR_WINDOW);

        JPanel panel_east = new JPanel();
        panel_east.setPreferredSize(new Dimension(40, 0));
        panel_east.setBackground(GasConstants.COLOR_WINDOW);

        add(panel_east, BorderLayout.EAST); // ⚠ จุดนี้ชนกับ panel_East ข้างบน
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
