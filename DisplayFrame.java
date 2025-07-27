import javax.swing.*;
import java.awt.*;

public class DisplayFrame extends JFrame {
    public DisplayFrame() {
        setFrame();
        JPanel panel_north = panel_north();
        JPanel panel_center = panel_center();
        JPanel panel_south = panel_south();

        setPadding();

        add(panel_south, BorderLayout.SOUTH);
        add(panel_north, BorderLayout.NORTH);
        add(panel_center, BorderLayout.CENTER);
    }

    public void setPadding() {
        JPanel panel_west = new JPanel();
        panel_west.setPreferredSize(new Dimension(20, 0));
        JPanel panel_east = new JPanel();
        panel_east.setPreferredSize(new Dimension(20, 0));

        panel_west.setBackground(new Color(255, 193, 218));
        panel_east.setBackground(new Color(255, 193, 218));

        add(panel_east, BorderLayout.EAST);
        add(panel_west, BorderLayout.WEST);
    }

    public JPanel panel_south() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(255, 193, 218));
        panel.setPreferredSize(new Dimension(0, 60));

        // สร้าง Box_red
        JPanel Box_red = new JPanel();
        Box_red.setBackground(Color.red);
        Box_red.setPreferredSize(new Dimension(45, 45));
        Box_red.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JLabel red = new JLabel("RED is Gas 0%");

        panel.add(Box_red);
        panel.add(red);

        // สร้าง Box_Yellow
        JPanel Box_Yellow = new JPanel();
        Box_Yellow.setBackground(Color.YELLOW);
        Box_Yellow.setPreferredSize(new Dimension(45, 45));
        Box_Yellow.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JLabel Yellow = new JLabel("Yellow is Gas < 50%");

        panel.add(Box_Yellow);
        panel.add(Yellow);

        // สร้าง Box_Green
        JPanel Box_Green = new JPanel();
        Box_Green.setBackground(Color.green);
        Box_Green.setPreferredSize(new Dimension(45, 45));
        Box_Green.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JLabel green = new JLabel("Green is Gas > 50%");

        panel.add(Box_Green);
        panel.add(green);

        return panel;
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

        // -- Center : รวม Label + TextArea + ปุ่ม -- //
        JPanel panel_centerAll = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel_centerAll.setBackground(new Color(255, 193, 218));

        // Label
        JLabel label = new JLabel("Fluid Contact Depth:");
        label.setFont(new Font("Arial", Font.BOLD, 12));
        panel_centerAll.add(label);

        // TextArea
        JTextArea text = new JTextArea();
        text.setPreferredSize(new Dimension(300, 50));
        text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel_centerAll.add(text);

        // Panel ปุ่ม (CALCULATE / CLEAR) -- add เข้า Center
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        buttonPanel.setBackground(new Color(255, 193, 218));
        JButton button_cal = new JButton("CALCULATE");
        button_cal.setPreferredSize(new Dimension(120, 25));
        button_cal.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JButton button_clear = new JButton("CLEAR");
        button_clear.setPreferredSize(new Dimension(120, 25));
        button_clear.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        buttonPanel.add(button_cal);
        buttonPanel.add(button_clear);

        panel_centerAll.add(buttonPanel);

        panelNorth.add(panel_centerAll, BorderLayout.CENTER);

        ////////////////////////////////

        // SOUTH : -- ปุ่ม OPEN FILE ด้านล่าง
        JPanel panel_southJPanel = new JPanel(new FlowLayout());
        panel_southJPanel.setBackground(new Color(255, 193, 218));
        panel_southJPanel.setPreferredSize(new Dimension(0, 55));

        JButton button_openFile = new JButton("OPEN FILE");
        button_openFile.setPreferredSize(new Dimension(300, 35));
        button_openFile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));


        panel_southJPanel.add(button_openFile);
        panelNorth.add(panel_southJPanel, BorderLayout.SOUTH);

        // NORTH : -- Padding ด้านบน --
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
        Color[] color = { Color.green, Color.red, Color.YELLOW };
        int index = (int) (Math.random() * color.length);
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