import javax.swing.*;
import java.awt.*;

public class DisplayFrame extends JFrame {
    public DisplayFrame() {
        setFrame();
        JPanel panel_north = panel_north();
        JPanel panel_center = panel_center();

        JPanel panel_west = new JPanel();
        panel_west.setPreferredSize(new Dimension(20, 0));
        JPanel panel_east = new JPanel();
        panel_east.setPreferredSize(new Dimension(20, 0));
        JPanel panel_south = new JPanel();
        panel_south.setPreferredSize(new Dimension(0, 20));
        
        panel_west.setBackground(new Color(255, 193, 218));
        panel_east.setBackground(new Color(255, 193, 218));
        panel_south.setBackground(new Color(255, 193, 218));

        add(panel_east, BorderLayout.EAST);
        add(panel_west, BorderLayout.WEST);
        add(panel_south, BorderLayout.SOUTH);
        add(panel_north, BorderLayout.NORTH);
        add(panel_center, BorderLayout.CENTER);
    }

    public void setFrame() {
        setSize(GasConstants.WINDOW_WIDTH, GasConstants.WINDOW_HEIGHT);
        setTitle("Jewel Suite");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(255, 193, 218));

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public JPanel panel_north() {
    JPanel panelNorth = new JPanel();
    panelNorth.setBackground(new Color(255, 193, 218));
    panelNorth.setLayout(new BorderLayout());

    // -- กลาง: รวม Label + TextArea + ปุ่ม --
    JPanel panel_centerAll = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    panel_centerAll.setBackground(new Color(255, 193, 218));

    JLabel label = new JLabel("Fluid Contact Depth:");
    panel_centerAll.add(label);

    JTextArea text = new JTextArea();
    text.setPreferredSize(new Dimension(300, 50));
    text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    panel_centerAll.add(text);

    // Panel ปุ่ม (CALCULATE / CLEAR)
    JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
    buttonPanel.setBackground(new Color(255, 193, 218));
    buttonPanel.add(new JButton("CALCULATE"));
    buttonPanel.add(new JButton("CLEAR"));

    panel_centerAll.add(buttonPanel);

    panelNorth.add(panel_centerAll, BorderLayout.CENTER);

    // -- ปุ่ม OPEN FILE ด้านล่าง --
    JPanel panel_southJPanel = new JPanel(new FlowLayout());
    panel_southJPanel.setBackground(new Color(255, 193, 218));
    JButton button_openFile = new JButton("OPEN FILE");
    button_openFile.setPreferredSize(new Dimension(250, 35));
    panel_southJPanel.add(button_openFile);
    panelNorth.add(panel_southJPanel, BorderLayout.SOUTH);

    // -- Padding ด้านบน --
    JPanel panel_northPadding = new JPanel();
    panel_northPadding.setBackground(new Color(255, 193, 218));
    panel_northPadding.setPreferredSize(new Dimension(0, 10));
    panelNorth.add(panel_northPadding, BorderLayout.NORTH);

    return panelNorth;
}

    public void createButton(int count, JPanel panel) {
        JButton[] button = new JButton[count];
        for (int i = 0; i < button.length; i++) {
            button[i] = new JButton();
            button[i].setBackground(randomColor());
            panel.add(button[i]);
        }
    }

    public Color randomColor() {
        Color[] color = {Color.green, Color.red, Color.YELLOW};
        int index = (int)(Math.random() * color.length);
        return color[index];
    }

    public JPanel panel_center() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 193, 218));
        panel.setLayout(new GridLayout(10, 20));

        createButton(200, panel);

        return panel;
    }
}