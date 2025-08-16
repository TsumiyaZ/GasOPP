import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AboutGroup extends JFrame {

    private DisplayFrame MainFrame; // เก็บ reference ของหน้าหลัก (MainFrame)

    AboutGroup(DisplayFrame frame) {
        this.MainFrame = frame; // รับค่าหน้าหลักมาเก็บไว้
        setTitle("About Group"); // ตั้งชื่อ title
        setSize(GasConstants.WINDOW_WIDTH, GasConstants.WINDOW_HEIGHT); // ขนาดหน้าต่าง
        setLocationRelativeTo(null); // เปิดตรงกลางจอ
        setLayout(new BorderLayout()); // ใช้ BorderLayout
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // ปิดเฉพาะ frame นี้ ไม่ปิดทั้งโปรแกรม

        setUndecorated(true); // เอาแถบ title bar ด้านบนออก (X, -, [])

        // ถ้า user กดปิดหน้าต่าง (x) ให้ปิดโปรแกรมไปเลย
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        getContentPane().setBackground(GasConstants.COLOR_WINDOW_SOUTH); // สีพื้นหลัง

        // เรียกเมธอดสร้างแต่ละส่วนของหน้า
        P_south();      // ปุ่ม Back ด้านล่าง
        About_Topic();  // หัวข้อด้านบน
        midPanel();     // เนื้อหากลาง (รูป + ชื่อ + ID)
    }

    // ส่วนล่าง (South Panel) มีปุ่ม Back กลับหน้า Main
    public void P_south() {
        JButton backToHome = create_Button_Back(); // สร้างปุ่ม Back
        JPanel south = create_Panel_South();       // สร้าง panel สำหรับ South

        // กดปุ่ม Back -> ปิดหน้านี้ แล้วกลับไป MainFrame
        backToHome.addActionListener(e -> {
            this.dispose(); // ปิด frame นี้
            MainFrame.setVisible(true); // เปิด MainFrame
        });

        south.add(backToHome); // ใส่ปุ่มใน panel
        add(south, BorderLayout.SOUTH); // ใส่ panel ลงตำแหน่ง SOUTH
    }

    // สร้าง panel ด้านล่าง
    public JPanel create_Panel_South() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout()); // ใช้ FlowLayout
        panel.setBackground(GasConstants.COLOR_WINDOW); // ตั้งสีพื้นหลัง
        return panel;
    }

    // สร้างปุ่ม Back
    public JButton create_Button_Back() {
        JButton backToHome = new JButton("Back");
        backToHome.setPreferredSize(new Dimension(300, 50)); // กำหนดขนาดปุ่ม
        backToHome.setBackground(GasConstants.COLOR_WINDOW_SOUTH); // สีพื้นหลัง
        backToHome.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // กรอบเส้นดำ
        backToHome.setFocusPainted(false); // ไม่ให้มีเส้นขอบเวลา focus
        return backToHome;
    }

    // ส่วนบน (North Panel) แสดงหัวข้อ "About Group"
    public void About_Topic() {
        JPanel panel = create_Panel_Topic();
        JLabel about_topic = new JLabel("About Group"); // label หัวข้อ

        panel.setLayout(new FlowLayout()); // Layout ตรงกลาง
        panel.setBackground(GasConstants.COLOR_WINDOW); // สีพื้นหลัง
        panel.setBorder(new LineBorder(Color.BLACK, 2)); // เส้นกรอบ

        about_topic.setFont(new Font(null, Font.BOLD, 36)); // ตั้งค่า font

        panel.add(about_topic); // ใส่ label ลง panel
        add(panel, BorderLayout.NORTH); // ใส่ panel ลงด้านบน
    }

    // คืนค่า panel สำหรับหัวข้อ (ตอนนี้ยังว่าง)
    public JPanel create_Panel_Topic() {
        JPanel about_panel = new JPanel();
        return about_panel;
    }

    // ส่วนกลาง (Center Panel) เอาไว้ใส่ "รูป + ชื่อ + ID"
    public void midPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // วางเรียงแนวตั้ง
        panel.setBorder(new LineBorder(Color.BLACK, 2));

        JLabel[] labels = createLabels();            // label ข้อความ (NAME + ID)
        JPanel[] namePanels = createNamePanels(labels); // panel สำหรับชื่อแต่ละคน
        JPanel[] picturePanels = createPicturePanels(); // panel สำหรับรูปภาพ

        // วนสร้างทีละ row (แถว) -> รูปภาพ + ข้อความ
        for (int i = 0; i < picturePanels.length; i++) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER)); // 1 แถว
            row.add(picturePanels[i]); // รูปภาพ
            row.add(namePanels[i]);    // ข้อความ (ชื่อ + ID)

            // ใส่รูปภาพใน panel
            picturePanels[i].add(createPictureLabel(GasConstants.PICTURE_ABOUT[i]));

            panel.add(row); // เพิ่ม row ลง panel ใหญ่
        }

        add(panel, BorderLayout.CENTER); // ใส่ panel กลาง
    }

    // สร้าง labels สำหรับแต่ละคน (NAME + ID)
    public JLabel[] createLabels() {
        JLabel[] labels = new JLabel[3]; // จำนวน 3 คน
        for (int i = 0; i < labels.length; i++) {
            // ใช้ HTML <br> เพื่อขึ้นบรรทัดใหม่
            String data = "<html>NAME : " + GasConstants.GAS_NAME_TEXT[i]
                    + "<br>ID : " + GasConstants.GAS_ID_TEXT[i] + "</html>";

            labels[i] = new JLabel(data); // สร้าง label
            labels[i].setFont(new Font(null, Font.BOLD, 24)); // ตั้งค่า font
        }
        return labels;
    }

    // สร้าง panel สำหรับเก็บชื่อ + ID แต่ละคน
    public JPanel[] createNamePanels(JLabel[] labels) {
        JPanel[] panels = new JPanel[labels.length];
        for (int i = 0; i < panels.length; i++) {
            panels[i] = new JPanel();
            panels[i].setLayout(new BoxLayout(panels[i], BoxLayout.Y_AXIS)); // วางแนวตั้ง
            panels[i].setPreferredSize(new Dimension(500, 200)); // ขนาดเท่ากันทุกช่อง
            panels[i].setBorder(new LineBorder(Color.BLACK, 2)); // เส้นกรอบ

            // จัด label ให้อยู่กึ่งกลางในแกน X
            labels[i].setAlignmentX(Component.CENTER_ALIGNMENT);

            // ใส่กล่องว่าง (Glue) เพื่อดันข้อความให้ไปอยู่ตรงกลางแนวตั้ง
            panels[i].add(Box.createVerticalGlue());
            panels[i].add(labels[i]);
            panels[i].add(Box.createVerticalGlue());
        }
        return panels;
    }

    // สร้าง panel สำหรับเก็บรูปภาพแต่ละคน
    public JPanel[] createPicturePanels() {
        JPanel[] panels = new JPanel[3]; // มี 3 คน
        for (int i = 0; i < panels.length; i++) {
            panels[i] = new JPanel();
            panels[i].setPreferredSize(new Dimension(150, 200)); // ขนาดเท่ากัน
            panels[i].setBorder(new LineBorder(Color.BLACK, 2)); // เส้นกรอบ
        }
        return panels;
    }

    // สร้าง JLabel ใส่รูป (ย่อสัดส่วน)
    public JLabel createPictureLabel(String path) {
        ImageIcon icon = new ImageIcon(path); // โหลดรูปจาก path
        Image scaled = icon.getImage().getScaledInstance(130, 185, Image.SCALE_SMOOTH); // ย่อรูป
        return new JLabel(new ImageIcon(scaled)); // สร้าง JLabel เก็บรูป
    }

}
