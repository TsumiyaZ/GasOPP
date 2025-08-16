import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AboutDialog extends JDialog {

    public AboutDialog(JFrame parent) {
        super(parent, "About Group", true); // modal dialog
        
        setSize(GasConstants.WINDOW_WIDTH, GasConstants.WINDOW_HEIGHT);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);

        getContentPane().setBackground(GasConstants.COLOR_WINDOW_SOUTH);

        // เรียกเมธอดสร้างแต่ละส่วนของ dialog
        createSouthPanel();  // ปุ่ม Close ด้านล่าง
        createTopicPanel();  // หัวข้อด้านบน
        createContentPanel(); // เนื้อหากลาง (รูป + ชื่อ + ID)
    }

    // ส่วนล่าง (South Panel) มีปุ่ม Close
    private void createSouthPanel() {
        JButton closeButton = createCloseButton();
        JPanel south = new JPanel(new FlowLayout());
        south.setBackground(GasConstants.COLOR_WINDOW);

        closeButton.addActionListener(e -> dispose()); // ปิด dialog

        south.add(closeButton);
        add(south, BorderLayout.SOUTH);
    }

    // สร้างปุ่ม Close
    private JButton createCloseButton() {
        JButton closeButton = new JButton("Close");
        closeButton.setPreferredSize(new Dimension(300, 50));
        closeButton.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        closeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        closeButton.setFocusPainted(false);
        return closeButton;
    }

    // ส่วนบน (North Panel) แสดงหัวข้อ "About Group"
    private void createTopicPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(GasConstants.COLOR_WINDOW);
        panel.setBorder(new LineBorder(Color.BLACK, 2));

        JLabel about_topic = new JLabel("About Group");
        about_topic.setFont(new Font(null, Font.BOLD, 36));

        panel.add(about_topic);
        add(panel, BorderLayout.NORTH);
    }

    // ส่วนกลาง (Center Panel) เอาไว้ใส่ "รูป + ชื่อ + ID"
    private void createContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new LineBorder(Color.BLACK, 2));

        JLabel[] labels = createLabels();
        JPanel[] namePanels = createNamePanels(labels);
        JPanel[] picturePanels = createPicturePanels();

        // วนสร้างทีละ row (แถว) -> รูปภาพ + ข้อความ
        for (int i = 0; i < picturePanels.length; i++) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER));
            row.add(picturePanels[i]);
            row.add(namePanels[i]);

            // ใส่รูปภาพใน panel
            picturePanels[i].add(createPictureLabel(GasConstants.PICTURE_ABOUT[i]));

            panel.add(row);
        }

        add(panel, BorderLayout.CENTER);
    }

    // สร้าง labels สำหรับแต่ละคน (NAME + ID)
    private JLabel[] createLabels() {
        JLabel[] labels = new JLabel[3];
        for (int i = 0; i < labels.length; i++) {
            String data = "<html>NAME : " + GasConstants.GAS_NAME_TEXT[i]
                    + "<br>ID : " + GasConstants.GAS_ID_TEXT[i] + "</html>";

            labels[i] = new JLabel(data);
            labels[i].setFont(new Font(null, Font.BOLD, 24));
        }
        return labels;
    }

    // สร้าง panel สำหรับเก็บชื่อ + ID แต่ละคน
    private JPanel[] createNamePanels(JLabel[] labels) {
        JPanel[] panels = new JPanel[labels.length];
        for (int i = 0; i < panels.length; i++) {
            panels[i] = new JPanel();
            panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.Y_AXIS));
            panels[i].setPreferredSize(new Dimension(500, 200));
            panels[i].setBorder(new LineBorder(Color.BLACK, 2));

            labels[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            panels[i].add(Box.createVerticalGlue());
            panels[i].add(labels[i]);
            panels[i].add(Box.createVerticalGlue());
        }
        return panels;
    }

    // สร้าง panel สำหรับเก็บรูปภาพแต่ละคน
    private JPanel[] createPicturePanels() {
        JPanel[] panels = new JPanel[3];
        for (int i = 0; i < panels.length; i++) {
            panels[i] = new JPanel();
            panels[i].setPreferredSize(new Dimension(200, 200));
            panels[i].setBorder(new LineBorder(Color.BLACK, 2));
        }
        return panels;
    }

    // สร้าง JLabel ใส่รูป (ย่อสัดส่วน)
    private JLabel createPictureLabel(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image scaled = icon.getImage().getScaledInstance(185, 185, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(scaled));
    }
}