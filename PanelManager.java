import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.MouseListener;

public class PanelManager {
    // เก็บ reference ของ Frame หลัก
    private DisplayFrame FrameMain;

    // เก็บข้อมูลฐานแก๊ส (จากไฟล์)
    private Double[][] baseGas;

    // ค่าระดับของ fluid contact (ค่า default มาจาก GasConstants)
    private int fluld = GasConstants.FLULD_CONTACT;

    // panel ฝั่งซ้าย
    private JPanel panelWest;

    // ป้าย label แสดงข้อมูลแก๊สเวลาชี้เมาส์
    private JLabel monitor;

    // ช่องกรอกระดับ fluid
    private JTextField Fluid_level;

    // จำนวนแถวและคอลัมน์ของข้อมูล
    private int row = 0;
    private int column = 0;

    // panel สำหรับแสดง grid
    private JPanel GridPanel;

    // ========= [ Constructor: รับ Frame หลัก ] =========
    public PanelManager(DisplayFrame frame) {
        this.FrameMain = frame;
    }

    // ================= { Function } =================
    // ========= [ เปิดไฟล์ ] =========
    private void openFile() {
        // ตรวจสอบว่ามีข้อมูลอยู่แล้วหรือไม่
        if (this.row > 0 && this.column > 0) {
            int result = JOptionPane.showConfirmDialog(null, 
                "Are you clear File Data?", 
                "ยืนยันการเปิดไฟล์ใหม่", 
                JOptionPane.YES_NO_OPTION);
            
            if (result != JOptionPane.YES_OPTION) {
                return; // ยกเลิกการเปิดไฟล์
            }
            
            // ล้างข้อมูลเก่า
            clearGridPanel();
            baseGas = new Double[0][0];
            this.row = 0;
            this.column = 0;
        }
        
        JFileChooser file_choose = new JFileChooser();
        file_choose.setCurrentDirectory(new File("DataGas")); // ตั้งโฟลเดอร์เริ่มต้น

        FileNameExtensionFilter filter_txt = new FileNameExtensionFilter("Please txt", "txt"); // ให้เลือกได้เฉพาะไฟล์.txt
        file_choose.setFileFilter(filter_txt);

        int result = file_choose.showOpenDialog(FrameMain);

        // ถ้าผู้ใช้เลือกไฟล์
        if (result == JFileChooser.APPROVE_OPTION) {
            File select_File = file_choose.getSelectedFile();

            // อ่านข้อมูลจากไฟล์มาใส่ baseGas
            getDataInFile(select_File);

            // สร้างปุ่ม grid จากข้อมูลในไฟล์
            createGridButton();

        } else {
            System.out.println("No file select");
        }
    }

    // ========= [ แสดงข้อมูลใน baseGas เพื่อ debug ] =========
    void checkDataInFile() {
        for (int i = 0; i < baseGas.length; i++) {
            for (int j = 0; j < baseGas[i].length; j++) {
                System.out.print(baseGas[i][j] + " ");
            }
            System.out.println();
        }
    }

    // ========= [ อ่านข้อมูลจากไฟล์ใส่ baseGas ] =========
    void getDataInFile(File select_File) {
        try (BufferedReader read = new BufferedReader(new FileReader(select_File))) {
            ArrayList<String> List_Line = new ArrayList<>();

            // อ่านข้อมูลทีละบรรทัด เก็บใน ArrayList
            String line;
            while ((line = read.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    List_Line.add(line);
                }
            }

            // ถ้าไฟล์ว่าง
            if (List_Line.isEmpty()) {
                System.out.println("File is empty");
                return;
            }

            // หา column สูงสุด
            int MAX_Cols = 0;
            for (String liness : List_Line) {
                String[] values = liness.split("\\s+");
                MAX_Cols = Math.max(MAX_Cols, values.length);
            }

            // จํานวน column สูงสุด
            this.column = MAX_Cols;
            // จำนวนแถว
            this.row = List_Line.size();

            // สร้าง array baseGas
            baseGas = new Double[this.row][this.column];

            // อ่านค่าจากช่อง Fluid_level
            this.fluld = Integer.parseInt(this.Fluid_level.getText());

            // ใส่ค่าจากไฟล์ลงใน baseGas
            for (int row = 0; row < this.row; row++) {
                String[] values = List_Line.get(row).split("\\s+");
                for (int column = 0; column < MAX_Cols; column++) {
                    if (column < values.length) {
                        try {

                            baseGas[row][column] = Double.parseDouble(values[column]);
                            if (Double.isNaN(baseGas[row][column] )) {
                                baseGas[row][column] = 0.0;
                            }
                        } catch (NumberFormatException e) {
                            baseGas[row][column] = 0.0; // ถ้าไม่ใช่ตัวเลข ใส่ 0
                        }
                    } else {
                        baseGas[row][column] = -1.0; // ถ้าจำนวนน้อยกว่าความกว้างสูงสุด ให้เติม 0
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO error: " + e.getMessage());
        }
    }

    // ========= [ สร้าง Grid แสดงปุ่มแต่ละ cell ] =========
    void createGridButton() {
        GridPanel = new JPanel(new GridLayout(this.row, this.column, 2, 2));
        panelWest.add(GridPanel, BorderLayout.CENTER);

        for (int i = 0; i < baseGas.length; i++) {
            for (int j = 0; j < baseGas[0].length; j++) {
                JPanel panel = new JPanel(new BorderLayout());
                double percent = getPercent(baseGas[i][j]);

                // แสดงเปอร์เซ็นต์ตรงกลางปุ่ม
                JLabel percent_Text = new JLabel(String.format("%.0f%%", percent), SwingConstants.CENTER);
                percent_Text.setFont(new Font("Arial", Font.BOLD, 10));
                percent_Text.setForeground(Color.BLACK);
                panel.setPreferredSize(new Dimension(40, 40));

                // กำหนดสีตามเปอร์เซ็นต์
                if (percent <= 0) {
                    panel.setBackground(Color.red);
                } else if (percent <= 50) {
                    panel.setBackground(Color.yellow);
                } else {
                    panel.setBackground(Color.green);
                }

                // เพิ่ม MouseListener เพื่อให้แสดงข้อมูลเมื่อ hover
                panel.addMouseListener(new MouseFunction(baseGas[i][j], panel));

                panel.add(percent_Text);
                GridPanel.add(panel);
            }
        }

        GridPanel.revalidate();
        GridPanel.repaint();
    }

    // ลบข้อมูลใน GridPanel
    private void clearGridPanel() {
        if (GridPanel != null) {
            GridPanel.removeAll();
            GridPanel.revalidate();
            GridPanel.repaint();
        }
    }
    
    // Method สำหรับ refresh GridPanel เมื่อกลับมาจาก About
    public void refreshGrid() {
        if (GridPanel != null) {
            GridPanel.revalidate();
            GridPanel.repaint();
            // Force repaint ทุก component ใน GridPanel
            Component[] components = GridPanel.getComponents();
            for (Component comp : components) {
                comp.repaint();
            }
        }
    }

    // ========= [ คลาสสำหรับจัดการ Mouse Event ในแต่ละ cell ] =========
    private class MouseFunction implements MouseListener {
        private double base;
        private JPanel panel;

        MouseFunction(double base, JPanel panel) {
            this.base = base;
            this.panel = panel;
        }

        // เมื่อเมาส์เข้า
        public void mouseEntered(MouseEvent e) {
            setLabelData(panel, base);
        }

        // เมื่อเมาส์ออก
        public void mouseExited(MouseEvent e) {
            monitor.setText("");
            panel.setBorder(BorderFactory.createLineBorder(Color.white));
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }
    }

    // ตั้งค่าข้อมูลลงใน monitor
    void setLabelData(JPanel panel, double base) {
        String label = String.format("<html>Percent | %.2f%%<br>Volume | %.2f<html>", getPercent(base),
                getVolume(base));
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        monitor.setText(label);
    }

    // คำนวณเปอร์เซ็นต์แก๊ส
    private double getPercent(double baseDepth) {
        if (baseDepth < 0) {
            return 0.0;
        }
        double topHorizon = baseDepth - 200;
        double fluld = this.fluld;

        double total = baseDepth - topHorizon;
        double gas = Math.max(0, Math.min(fluld, baseDepth) - topHorizon);
        return gas / total * 100;
    }

    // คำนวณปริมาตรแก๊ส
    private double getVolume(double baseDepth) {
        if (baseDepth < 0) {
            return 0.0;
        }
        double topHorizon = baseDepth - 200;
        double fluld = this.fluld;

        double thickness = fluld - topHorizon;
        if (thickness <= 0) {
            return 0; // ไม่มีแก๊ส
        }
        return GasConstants.GAS_BUTTON_WIDTH * GasConstants.GAS_BUTTON_LENGTH * thickness;
    }

    // ================= { GUI Panel ส่วนต่างๆ } =================

    // ========= [ PANEL SOUTH: Legend + ปุ่ม About ] =========
    public JPanel panel_south() {
        JPanel panel = new JPanel(new FlowLayout());

        panel.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        panel.setPreferredSize(new Dimension(0, 60));

        // กล่องสีแดง
        JPanel Box_red = new JPanel();
        Box_red.setBackground(Color.red);
        Box_red.setPreferredSize(new Dimension(45, 45));
        Box_red.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JLabel red = new JLabel("RED is Gas 0%");
        red.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(Box_red);
        panel.add(red);

        // กล่องสีเหลือง
        JPanel Box_Yellow = new JPanel();
        Box_Yellow.setBackground(Color.YELLOW);
        Box_Yellow.setPreferredSize(new Dimension(45, 45));
        Box_Yellow.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JLabel Yellow = new JLabel("Yellow is Gas < 50%");
        Yellow.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(Box_Yellow);
        panel.add(Yellow);

        // กล่องสีเขียว
        JPanel Box_Green = new JPanel();
        Box_Green.setBackground(Color.green);
        Box_Green.setPreferredSize(new Dimension(45, 45));
        Box_Green.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JLabel green = new JLabel("Green is Gas > 50%");
        green.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(Box_Green);
        panel.add(green);

        // ปุ่ม About
        JButton button_about = createButtonAbout("About Group", 200, 30);
        panel.add(button_about);

        // ปุ่ม EXIT
        JButton button_exit = createButtonExit("EXIT", 200, 30);
        panel.add(button_exit);

        // เมื่อกดปุ่ม about
        button_about.addActionListener(e -> {
            AboutDialog about = new AboutDialog(FrameMain);
            about.setVisible(true);
        });

        // เมื่อกดปุ่ม EXIT
        button_exit.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "Sure?", "ยืนยันการออก", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        });

        return panel;
    }

    // ========= [ PANEL NORTH: พื้นที่ด้านบน ] =========
    public JPanel panel_north() {
        JPanel panelNorth = new JPanel();
        panelNorth.setBackground(GasConstants.COLOR_WINDOW);
        panelNorth.setLayout(new BorderLayout());

        JPanel panel_southJPanel = new JPanel(new FlowLayout());
        panel_southJPanel.setBackground(GasConstants.COLOR_WINDOW);
        panel_southJPanel.setPreferredSize(new Dimension(0, 40));
        panelNorth.add(panel_southJPanel, BorderLayout.SOUTH);

        JPanel panel_northPadding = new JPanel();
        panel_northPadding.setBackground(GasConstants.COLOR_WINDOW);
        panel_northPadding.setPreferredSize(new Dimension(0, 10));
        panelNorth.add(panel_northPadding, BorderLayout.NORTH);

        return panelNorth;
    }

    // ========= [ PANEL CENTER: พื้นที่ตรงกลางฝั่งซ้าย ] =========
    public JPanel panel_center() {
        panelWest = new JPanel(new BorderLayout());
        panelWest.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        panelWest.setPreferredSize(new Dimension(500, 500));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GasConstants.COLOR_WINDOW);
        setPaddingEvery(panel);
        panel.add(panelWest, BorderLayout.CENTER);

        return panel;
    }

    // ========= [ PANEL EAST: ฝั่งขวา มีช่องกรอกข้อมูล ปุ่ม และ Monitor ] =========
    public JPanel panel_East() {
        JPanel panelEast = new JPanel(new BorderLayout());
        panelEast.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        panelEast.setPreferredSize(new Dimension(400, 500));

        // padding
        JPanel panel_eastPadding = new JPanel();
        panel_eastPadding.setBackground(GasConstants.COLOR_WINDOW);
        panel_eastPadding.setPreferredSize(new Dimension(25, 500));

        JPanel south_eastPanel = new JPanel();
        south_eastPanel.setBackground(GasConstants.COLOR_WINDOW);
        south_eastPanel.setPreferredSize(new Dimension(300, 25));

        JPanel north_eastPanel = new JPanel();
        north_eastPanel.setBackground(GasConstants.COLOR_WINDOW);
        north_eastPanel.setPreferredSize(new Dimension(300, 25));

        // panel กลาง (ฝั่งขวา)
        JPanel panel_centerAll = new JPanel();
        panel_centerAll.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        panel_centerAll.setLayout(new BoxLayout(panel_centerAll, BoxLayout.Y_AXIS));
        panel_centerAll.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Fluid Contact Depth :");
        label.setFont(new Font("Arial", Font.BOLD, 17));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel_centerAll.add(label);
        panel_centerAll.add(Box.createRigidArea(new Dimension(0, 10)));

        Fluid_level = new JTextField(String.valueOf(GasConstants.FLULD_CONTACT));
        Fluid_level.setMaximumSize(new Dimension(300, 30));
        Fluid_level.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel_centerAll.add(Fluid_level);
        panel_centerAll.add(Box.createRigidArea(new Dimension(0, 20)));

        monitor = new JLabel();
        monitor.setPreferredSize(new Dimension(300, 80));
        monitor.setMaximumSize(new Dimension(300, 80));
        monitor.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        monitor.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel_centerAll.add(monitor);
        panel_centerAll.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        buttonPanel.setMaximumSize(new Dimension(320, 180));

        JButton button_openFile = creaButtonOpenFile("OPEN FILE", 300, 35);
        JButton button_cal = createButtonCalculate("CALCULATE", 300, 50);
        JButton button_clear = creaButtonClear("CLEAR", 120, 25);

        button_openFile.addActionListener(e -> openFile());
        button_clear.addActionListener(e -> clearButton());
        button_cal.addActionListener(e -> updateData());

        buttonPanel.add(button_cal);
        buttonPanel.add(button_openFile);
        buttonPanel.add(button_clear);

        panel_centerAll.add(buttonPanel);

        panelEast.add(panel_centerAll, BorderLayout.CENTER);
        panelEast.add(panel_eastPadding, BorderLayout.EAST);
        panelEast.add(south_eastPanel, BorderLayout.SOUTH);
        panelEast.add(north_eastPanel, BorderLayout.NORTH);

        return panelEast;
    }

    // ล้างข้อมูลทั้งหมด
    private void clearButton() {
        int result = JOptionPane.showConfirmDialog(null, "Sure?", "Clear Data", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            baseGas = new Double[0][0];
            this.row = 0;
            this.column = 0;
            clearGridPanel();
        }
    }

    // อัพเดตข้อมูลตามค่าที่ใส่ใน Fluid_level
    private void updateData() {
        if (this.row > 0) {
            if (Fluid_level.getText().matches("[0-9]+")) {
                this.fluld = Integer.parseInt(Fluid_level.getText());
                GridPanel.removeAll();
                GridPanel.revalidate();
                createGridButton();
            } else {
                JOptionPane.showMessageDialog(null, "Please Input Integer", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please Select File", "INFO", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // ========= [ Padding รอบ panel ] =========
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

    // ========= [ เมธอดสร้างปุ่มต่างๆ ] =========
    public JButton createButtonCalculate(String text, int width, int height) {
        JButton button_cal = new JButton(text);
        button_cal.setPreferredSize(new Dimension(width, height));
        button_cal.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button_cal.setBackground(new Color(196, 217, 255));
        button_cal.setFocusPainted(false);
        return button_cal;
    }

    public JButton creaButtonClear(String text, int width, int height) {
        JButton button_clear = new JButton(text);
        button_clear.setPreferredSize(new Dimension(width, height));
        button_clear.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button_clear.setBackground(new Color(196, 217, 255));
        button_clear.setFocusPainted(false);
        return button_clear;
    }

    public JButton creaButtonOpenFile(String text, int width, int height) {
        JButton button_openFile = new JButton(text);
        button_openFile.setPreferredSize(new Dimension(width, height));
        button_openFile.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button_openFile.setBackground(new Color(196, 217, 255));
        button_openFile.setFocusPainted(false);
        return button_openFile;
    }

    public JButton createButtonAbout(String text, int width, int height) {
        JButton button_about = new JButton(text);
        button_about.setBackground(new Color(196, 217, 255));
        button_about.setPreferredSize(new Dimension(width, height));
        button_about.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        button_about.setFont(new Font("Arial", Font.BOLD, 14));
        button_about.setFocusPainted(false);
        return button_about;
    }

    public JButton createButtonExit(String text, int width, int height) {
        JButton button_about = new JButton(text);
        button_about.setBackground(Color.red);
        button_about.setPreferredSize(new Dimension(width, height));
        button_about.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        button_about.setFont(new Font("Arial", Font.BOLD, 14));
        button_about.setFocusPainted(false);
        return button_about;
    }
}
