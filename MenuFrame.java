import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.*;

public class MenuFrame extends JFrame {
    private JFrame mainframe;

    MenuFrame() {
        setDetailsFrame();
        panel_center();
    }

    void setDetailsFrame() {
        setTitle("Jewel Suite");
        setSize(GasConstants.WINDOW_WIDTH, GasConstants.WINDOW_HEIGHT);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.cyan);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void panel_center() {
        JPanel center = new JPanel(new BorderLayout());

        JPanel panelOfcenter = new JPanel();
        panelOfcenter.setBackground(GasConstants.COLOR_WINDOW);
        panelOfcenter.setLayout(new BoxLayout(panelOfcenter, BoxLayout.Y_AXIS));

        JButton start = new JButton("Start");
        start.setPreferredSize(new Dimension(200, 45));
        start.setMaximumSize(new Dimension(200, 45));
        start.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        start.setBackground(new Color(231, 211, 211));
        start.setFocusPainted(false);
        start.setFont(new Font("Arial", Font.BOLD, 18));

        JButton about = new JButton("ABOUT");
        about.setPreferredSize(new Dimension(200, 45));
        about.setMaximumSize(new Dimension(200, 45));
        about.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        about.setBackground(new Color(231, 211, 211));
        about.setFocusPainted(false);
        about.setFont(new Font("Arial", Font.BOLD, 18));

        JButton exit = new JButton("EXIT");
        exit.setPreferredSize(new Dimension(200, 45));
        exit.setMaximumSize(new Dimension(200, 45));
        exit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        exit.setBackground(new Color(231, 211, 211));
        exit.setFocusPainted(false);
        exit.setFont(new Font("Arial", Font.BOLD, 18));

        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        about.setAlignmentX(Component.CENTER_ALIGNMENT);
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelOfcenter.add(Box.createVerticalGlue());
        panelOfcenter.add(start);
        panelOfcenter.add(Box.createRigidArea(new Dimension(0, 20)));
        panelOfcenter.add(about);
        panelOfcenter.add(Box.createRigidArea(new Dimension(0, 20)));
        panelOfcenter.add(exit);
        panelOfcenter.add(Box.createVerticalGlue());

        

        exit.addActionListener(e -> System.exit(0));

        setPaddingEvery(center);

        center.add(panelOfcenter, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);
    }

    public void setPaddingEvery(JPanel panel) {
        JPanel padding_south = new JPanel();
        padding_south.setPreferredSize(new Dimension(0, 100));
        padding_south.setBackground(GasConstants.COLOR_WINDOW);

        JPanel padding_east = new JPanel();
        padding_east.setPreferredSize(new Dimension(100, 0));
        padding_east.setBackground(GasConstants.COLOR_WINDOW);

        JPanel padding_west = new JPanel();
        padding_west.setPreferredSize(new Dimension(100, 0));
        padding_west.setBackground(GasConstants.COLOR_WINDOW);

        JPanel padding_north = new JPanel();
        padding_north.setPreferredSize(new Dimension(0, 100));
        padding_north.setBackground(GasConstants.COLOR_WINDOW);

        panel.add(padding_north, BorderLayout.NORTH);
        panel.add(padding_south, BorderLayout.SOUTH);
        panel.add(padding_east, BorderLayout.EAST);
        panel.add(padding_west, BorderLayout.WEST);
    }

    public static void main(String[] args) {
        MenuFrame frame = new MenuFrame();
        frame.setVisible(true);
    }
}
