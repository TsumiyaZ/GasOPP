import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class AboutGroup extends JFrame{

    private DisplayFrame MainFrame;

    AboutGroup(DisplayFrame frame) {
        this.MainFrame = frame;
        setTitle("About Group");
        setSize(GasConstants.WINDOW_WIDTH, GasConstants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setBackground(GasConstants.COLOR_WINDOW_SOUTH);

        P_south();
        
    }    
    
    public void P_south() {
        JButton backToHome = create_Button_Back();

        JPanel south = create_Panel_South();
        
        backToHome.addActionListener(e -> {
            // ปิดหน้านี้ออกไปเลย
            this.dispose();
            
            // เปิดหน้าหลักก
            MainFrame.setVisible(true);
        });
        
        south.add(backToHome);
        add(south, BorderLayout.SOUTH);
    }

    public JPanel create_Panel_South() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBackground(GasConstants.COLOR_WINDOW_SOUTH);

        return panel;
    }

    public JButton create_Button_Back() {
        JButton backToHome = new JButton("Back");
        backToHome.setPreferredSize(new Dimension(300, 50));
        backToHome.setBackground(GasConstants.COLOR_WINDOW);
        backToHome.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        backToHome.setFocusPainted(false);
        
        return backToHome;
    }

    
}

/* class RoundedBorder implements Border {
    private int radius;

    RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(Color.BLACK); // สีขอบปุ่ม
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}
 */