import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

public class AboutGroup extends JFrame{

    private DisplayFrame MainFrame;

    AboutGroup(DisplayFrame frame) {
        this.MainFrame = frame;
        setTitle("About Group");
        setSize(GasConstants.WINDOW_WIDTH, GasConstants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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

        return panel;
    }

    public JButton create_Button_Back() {
        JButton backToHome = new JButton("Back");
        backToHome.setPreferredSize(new Dimension(300, 50));
        backToHome.setBackground(GasConstants.COLOR_WINDOW);
        backToHome.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return backToHome;
    }
}
