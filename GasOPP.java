import javax.swing.*;
import java.awt.*;

public class GasOPP extends JFrame {

    public GasOPP() {
        iniFrame();
        JPanel Panel_south = createPanelSouth();
        createPanelDimetion();
        JPanel Panel_center = createPanelCenter();
        JPanel Panel_North = createPanelNorth();

        add(Panel_North, BorderLayout.NORTH);
        add(Panel_center, BorderLayout.CENTER);
        add(Panel_south, BorderLayout.SOUTH);
        setVisible(true);
    }

    public void iniFrame() {
        setSize(GasConstants.WINDOW_WIDTH, GasConstants.WINDOW_HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Jewel Suite");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    public JPanel createPanelSouth() {
        JPanel panelSouth = new JPanel();
        panelSouth.setPreferredSize(new Dimension(0, 50));

        panelSouth.add(Box.createRigidArea(new Dimension(20, 0)));

        TextField textField = new TextField("");
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        panelSouth.add(textField);

        panelSouth.add(Box.createRigidArea(new Dimension(20, 0)));

        JButton button_openFILE = createStyledButton("Open File");
        JButton button_Fluid = createStyledButton("Fluid Contact");
        JButton button_Clear = createStyledButton("Clear Gas");

        panelSouth.add(button_Fluid);
        panelSouth.add(Box.createRigidArea(new Dimension(10, 0)));
        panelSouth.add(button_openFILE);
        panelSouth.add(Box.createRigidArea(new Dimension(10, 0)));
        panelSouth.add(button_Clear);

        return panelSouth;
    }

    public JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(130, 30));
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.blue);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    public JPanel createPanelCenter() {
        int count = 200;
        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new GridLayout(10, 20));
        panelCenter.setBackground(Color.cyan);

        JButton[] buttons = new JButton[count];
        for (int i = 0; i < count; i++) {
            buttons[i] = new JButton();
            buttons[i].setLayout(new BorderLayout());
            buttons[i].setBackground(randomColor());

            JLabel text = new JLabel(randomPer() + "%");
            text.setFont(new Font("Arial", Font.BOLD, 9));
            
            buttons[i].add(text, BorderLayout.SOUTH);
            panelCenter.add(buttons[i]);
        }
        return panelCenter;
    }

    public Color randomColor() {
        Color[] colors = {Color.red, Color.green, Color.yellow};
        int index = (int)(Math.random() * colors.length);
        return colors[index];
    }

    public String randomPer() {
        int num = (int)(Math.random() * 100) + 1;
        return String.valueOf(num);
    }

    public void createPanelDimetion() {
        JPanel panelWest = new JPanel();
        panelWest.setPreferredSize(new Dimension(60, 0));

        JPanel panelEast = new JPanel();
        panelEast.setPreferredSize(new Dimension(60, 0));

        add(panelWest, BorderLayout.WEST);
        add(panelEast, BorderLayout.EAST);
    }

    public JPanel createPanelNorth() {
        JPanel panelNorth = new JPanel();
        panelNorth.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelNorth.add(Box.createRigidArea(new Dimension(20, 20)));

        JButton colorRED = new JButton("");
        colorRED.setPreferredSize(new Dimension(40, 40));
        colorRED.setBackground(Color.red);

        JButton colorYellow = new JButton("");
        colorYellow.setPreferredSize(new Dimension(40, 40));
        colorYellow.setBackground(Color.YELLOW);

        JButton colorGreen = new JButton("");
        colorGreen.setPreferredSize(new Dimension(40, 40));
        colorGreen.setBackground(Color.green);

        JLabel textRed = new JLabel("RED not have gas");
        textRed.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel textYellow = new JLabel("Yellow Gas < 50%");
        textYellow.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel textGreen = new JLabel("Green Gas > 50%");    
        textGreen.setFont(new Font("Arial", Font.BOLD, 14));

        panelNorth.add(colorRED);
        panelNorth.add(textRed);
        panelNorth.add(colorYellow);
        panelNorth.add(textYellow);
        panelNorth.add(colorGreen);
        panelNorth.add(textGreen);

        return panelNorth;
    }
}
