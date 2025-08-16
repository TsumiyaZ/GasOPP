import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AboutGroup extends JFrame {

    private DisplayFrame MainFrame;

    AboutGroup(DisplayFrame frame) {
        this.MainFrame = frame;
        setTitle("About Group");
        setSize(GasConstants.WINDOW_WIDTH, GasConstants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setUndecorated(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        getContentPane().setBackground(GasConstants.COLOR_WINDOW_SOUTH);

        P_south();
        About_Topic();
        midPanel();

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
        panel.setBackground(GasConstants.COLOR_WINDOW);

        return panel;
    }

    public JButton create_Button_Back() {
        JButton backToHome = new JButton("Back");
        backToHome.setPreferredSize(new Dimension(300, 50));
        backToHome.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        backToHome.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        backToHome.setFocusPainted(false);

        return backToHome;
    }

    public void About_Topic() {
        JPanel panel = create_Panel_Topic();
        JLabel about_topic = new JLabel("About Group");

        panel.setLayout(new FlowLayout());
        panel.setBackground(GasConstants.COLOR_WINDOW);
        panel.setBorder(new LineBorder(Color.BLACK, 2));

        about_topic.setFont(new Font(null, Font.BOLD, 36));

        panel.add(about_topic);
        add(panel, BorderLayout.NORTH);
    }

    public JPanel create_Panel_Topic() {
        JPanel about_panel = new JPanel();
        return about_panel;
    }

    public void midPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new LineBorder(Color.BLACK, 2));

        JLabel[] labels = createLabels();
        JPanel[] namePanels = createNamePanels(labels);
        JPanel[] picturePanels = createPicturePanels();

        for (int i = 0; i < picturePanels.length; i++) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER));
            row.add(picturePanels[i]);
            row.add(namePanels[i]);

            // ใส่รูปภาพในช่อง picture
            picturePanels[i].add(createPictureLabel(GasConstants.PICTURE_ABOUT[i]));

            panel.add(row);
        }

        add(panel, BorderLayout.CENTER);
    }

    public JLabel[] createLabels() {
        JLabel[] labels = new JLabel[3];
        for (int i = 0; i < labels.length; i++) {
            String data = "<html>NAME : " + GasConstants.GAS_NAME_TEXT[i]
                    + "<br>ID : " + GasConstants.GAS_ID_TEXT[i] + "</html>";
            labels[i] = new JLabel(data);
            labels[i].setFont(new Font(null, Font.BOLD, 24));
        }
        return labels;
    }

    public JPanel[] createNamePanels(JLabel[] labels) {
    JPanel[] panels = new JPanel[labels.length];
    for (int i = 0; i < panels.length; i++) {
        panels[i] = new JPanel();
        panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.Y_AXIS));
        panels[i].setPreferredSize(new Dimension(500, 200));
        panels[i].setBorder(new LineBorder(Color.BLACK, 2));

        labels[i].setAlignmentX(Component.CENTER_ALIGNMENT);
        panels[i].add(Box.createVerticalGlue());  // ดันให้กึ่งกลาง
        panels[i].add(labels[i]);
        panels[i].add(Box.createVerticalGlue());
    }
    return panels;
}


    public JPanel[] createPicturePanels() {
        JPanel[] panels = new JPanel[3];
        for (int i = 0; i < panels.length; i++) {
            panels[i] = new JPanel();
            panels[i].setPreferredSize(new Dimension(150, 200));
            panels[i].setBorder(new LineBorder(Color.BLACK, 2));
        }
        return panels;
    }

    public JLabel createPictureLabel(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image scaled = icon.getImage().getScaledInstance(130, 185, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(scaled));
    }

}