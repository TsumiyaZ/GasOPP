import javax.swing.*;
import java.awt.*;

public class PanelManager {
    public JPanel panel_south() {
        JPanel panel = new JPanel(new FlowLayout());

        panel.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        panel.setPreferredSize(new Dimension(0, 60));

        // สร้าง Box_red
        JPanel Box_red = new JPanel();
        Box_red.setBackground(Color.red);
        Box_red.setPreferredSize(new Dimension(45, 45));

        JLabel red = new JLabel("RED is Gas 0%");
        red.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(Box_red);
        panel.add(red);

        // สร้าง Box_Yellow
        JPanel Box_Yellow = new JPanel();
        Box_Yellow.setBackground(Color.YELLOW);
        Box_Yellow.setPreferredSize(new Dimension(45, 45));

        JLabel Yellow = new JLabel("Yellow is Gas < 50%");
        Yellow.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(Box_Yellow);
        panel.add(Yellow);

        // สร้าง Box_Green
        JPanel Box_Green = new JPanel();
        Box_Green.setBackground(Color.green);
        Box_Green.setPreferredSize(new Dimension(45, 45));
        Box_Green.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        

        JLabel green = new JLabel("Green is Gas > 50%");
        green.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(Box_Green);
        panel.add(green);

        JButton button_about = new JButton("About Group");
        button_about.setBackground(new Color(196, 217, 255));
        button_about.setPreferredSize(new Dimension(200, 30));
        button_about.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        button_about.setFont(new Font("Arial", Font.BOLD, 14));
        
        panel.add(button_about);

        return panel;
    }

        public JPanel panel_north() {
        JPanel panelNorth = new JPanel();
        panelNorth.setBackground(GasConstants.COLOR_WINDOW);
        panelNorth.setLayout(new BorderLayout());

        ////////////////////////////////

        // SOUTH : -- ปุ่ม OPEN FILE ด้านล่าง
        JPanel panel_southJPanel = new JPanel(new FlowLayout());
        panel_southJPanel.setBackground(GasConstants.COLOR_WINDOW);
        panel_southJPanel.setPreferredSize(new Dimension(0, 40));

        panelNorth.add(panel_southJPanel, BorderLayout.SOUTH);

        // NORTH : -- Padding ด้านบน --
        JPanel panel_northPadding = new JPanel();

        panel_northPadding.setBackground(GasConstants.COLOR_WINDOW);
        panel_northPadding.setPreferredSize(new Dimension(0, 10));

        panelNorth.add(panel_northPadding, BorderLayout.NORTH);

        return panelNorth;
    }

    public JPanel panel_center() {
        JPanel panelWest = new JPanel();
        panelWest.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        panelWest.setPreferredSize(new Dimension(500, 500));
        panelWest.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GasConstants.COLOR_WINDOW);

        JPanel panel_center = new JPanel(new GridLayout(10, 20));
        panel_center.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        panel_center.setBorder(BorderFactory.createLineBorder(GasConstants.COLOR_WINDOW_SOUTH, 40));
    
        createButton(200, panel_center);
        setPaddingEvery(panel);

        panelWest.add(panel_center, BorderLayout.CENTER);
        panel.add(panelWest, BorderLayout.CENTER);

        return panel;
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

    public void setPaddingEvery(JPanel panel) {
        JPanel padding_south = new JPanel();
        padding_south.setPreferredSize(new Dimension(0, 25));
        padding_south.setBackground(GasConstants.COLOR_WINDOW);

        JPanel padding_east = new JPanel();
        padding_east.setPreferredSize(new Dimension(25, 0));
        padding_east.setBackground(GasConstants.COLOR_WINDOW);

        JPanel padding_west = new JPanel();
        padding_west.setPreferredSize(new Dimension(25, 0));
        padding_west.setBackground(GasConstants.COLOR_WINDOW);

        JPanel padding_north = new JPanel();
        padding_north.setPreferredSize(new Dimension(0, 25));
        padding_north.setBackground(GasConstants.COLOR_WINDOW);

        panel.add(padding_north, BorderLayout.NORTH);
        panel.add(padding_south, BorderLayout.SOUTH);
        panel.add(padding_east, BorderLayout.EAST);
        panel.add(padding_west, BorderLayout.WEST);
    }

    public JPanel panel_East() {
        JPanel panelEast = new JPanel();
        panelEast.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        panelEast.setPreferredSize(new Dimension(400, 500));
        panelEast.setLayout(new BorderLayout());
        
        JPanel panel_eastPadding = new JPanel();
        panel_eastPadding.setBackground(GasConstants.COLOR_WINDOW);
        panel_eastPadding.setPreferredSize(new Dimension(25, 500)); 
        
        JPanel south_eastPanel = new JPanel();
        south_eastPanel.setBackground(GasConstants.COLOR_WINDOW);
        south_eastPanel.setPreferredSize(new Dimension(300, 25));
        south_eastPanel.setLayout(new BorderLayout());

        JPanel north_eastPanel = new JPanel();
        north_eastPanel.setBackground(GasConstants.COLOR_WINDOW);
        north_eastPanel.setPreferredSize(new Dimension(300, 25));
        north_eastPanel.setLayout(new BorderLayout());

        JPanel panel_centerAll = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 50));
        panel_centerAll.setBackground(GasConstants.COLOR_WINDOW_SOUTH);

        JLabel label = new JLabel("Fluid Contact Depth :");
        label.setFont(new Font("Arial", Font.BOLD, 17));
        panel_centerAll.add(label);

        JTextArea text = new JTextArea();
        text.setPreferredSize(new Dimension(300, 150));
        text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        panel_centerAll.add(text);

        // Panel ปุ่ม (CALCULATE / CLEAR) -- add เข้า Center
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 20, 30));
        buttonPanel.setBackground(GasConstants.COLOR_WINDOW_SOUTH);

        JButton button_cal = new JButton("CALCULATE");
        button_cal.setPreferredSize(new Dimension(300, 50));
        button_cal.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button_cal.setBackground(new Color(196, 217, 255));

        JButton button_clear = new JButton("CLEAR");
        button_clear.setPreferredSize(new Dimension(120, 25));
        button_clear.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button_clear.setBackground(new Color(196, 217, 255));

        JButton button_openFile = new JButton("OPEN FILE");
        button_openFile.setPreferredSize(new Dimension(300, 35));
        button_openFile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button_openFile.setBackground(new Color(196, 217, 255));
        
        buttonPanel.add(button_openFile);
        buttonPanel.add(button_cal);
        buttonPanel.add(button_clear);

        panel_centerAll.add(buttonPanel);

        panelEast.add(panel_centerAll, BorderLayout.CENTER);

        panelEast.add(panel_eastPadding, BorderLayout.EAST);
        panelEast.add(south_eastPanel, BorderLayout.SOUTH);
        panelEast.add(north_eastPanel, BorderLayout.NORTH);
        
        return panelEast;
    }
}
