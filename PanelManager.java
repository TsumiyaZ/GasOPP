import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class PanelManager {
    private DisplayFrame FrameMain;
    private int[][] baseGas;
    private int fluld = GasConstants.FLULD_CONTACT;
    private JPanel panelWest;
    private JLabel monitor;
    private JTextField Fluid_level;
    private int row = 0;
    private int column = 0;
    private JPanel GridPanel;

    // ========= [ ส่ง MainFrame มาที่ไฟล์นี้ ] =========
    public PanelManager(DisplayFrame frame) {
        this.FrameMain = frame;
    }

    // ================= { Function } =================
    // ========= [ OpenFILE ] =========
    private void openFile() {
        JFileChooser file_choose = new JFileChooser();
        file_choose.setCurrentDirectory(new File("DataGas"));

        FileNameExtensionFilter filter_txt = new FileNameExtensionFilter("Please txt", "txt");

        // set ให้เปิดได้เเค่ .txt
        file_choose.setFileFilter(filter_txt);

        int result = file_choose.showOpenDialog(FrameMain);

        System.out.println(result);
        if (result == JFileChooser.APPROVE_OPTION) {
            File select_File = file_choose.getSelectedFile();

            // เอาข้อมูลตัวเลขมาจากไฟล์
            getDataInFile(select_File);

            createGridButton();

        } else {
            System.out.println("No file select");
        }
    }

    // ========= [ Check Data in file ] =========
    void checkDataInFile() {
        for (int i = 0; i < baseGas.length; i++) {
            for (int j = 0; j < baseGas[i].length; j++) {
                System.out.print(baseGas[i][j] + " ");
            }
            System.out.println();
        }
    }

    // ========= [ Get Data in file ] =========
    void getDataInFile(File select_File) {
    try (BufferedReader read = new BufferedReader(new FileReader(select_File))) {
        ArrayList<String> lines = new ArrayList<>();

        // อ่านข้อมูลบรรทัดทีละบรรทัดเก็บลง List
        String line;
        while ((line = read.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }

        if (lines.isEmpty()) {
            System.out.println("File is empty");
            return;
        }

        this.row = lines.size();

        // หาความกว้างคอลัมน์สูงสุด
        int maxCols = 0;
        for (String l : lines) {
            String[] values = l.split("\\s+");
            maxCols = Math.max(maxCols, values.length);
        }
        this.column = maxCols;
        baseGas = new int[this.row][this.column];
        this.fluld = Integer.parseInt(this.Fluid_level.getText());

        // อ่านข้อมูลใส่ baseGas และจัดการกรณีข้อมูลผิดพลาด
        for (int r = 0; r < this.row; r++) {
            String[] values = lines.get(r).split("\\s+");
            for (int c = 0; c < maxCols; c++) {
                if (c < values.length) {
                    try {
                        baseGas[r][c] = Integer.parseInt(values[c]);
                    } catch (NumberFormatException e) {
                        baseGas[r][c] = 0;
                        System.out.println("Invalid number at [" + r + "," + c + "], set to 0");
                    }
                } else {
                    baseGas[r][c] = 0;  // กรณีข้อมูลคอลัมน์น้อยกว่าความกว้าง maxCols เติม 0
                }
            }
        }

    } catch (FileNotFoundException e) {
        System.out.println("File not found");
    } catch (IOException e) {
        System.out.println("IO error: " + e.getMessage());
    }
}


    void createGridButton() {
        GridPanel = new JPanel(new GridLayout(this.row, this.column, 2, 2));
        panelWest.add(GridPanel, BorderLayout.CENTER);
        for (int i = 0; i < baseGas.length; i++) {
            for (int j = 0; j < baseGas[0].length; j++) {
                JPanel panel = new JPanel(new BorderLayout());
                double percent = getPercent(baseGas[i][j]);
                JLabel percent_Text = new JLabel(String.format("%.0f%%", percent), SwingConstants.CENTER);
                percent_Text.setFont(new Font("Arial", Font.BOLD, 10));
                percent_Text.setForeground(Color.BLACK); // เปลี่ยนตามพื้นหลังทีหลัง
                panel.setPreferredSize(new Dimension(40, 40));

                if (percent <= 0) {
                    panel.setBackground(Color.red);
                } else if (percent < 50) {
                    panel.setBackground(Color.yellow);
                } else {
                    panel.setBackground(Color.green);
                }

                panel.addMouseListener(new MouseFunction(baseGas[i][j], panel));

                panel.add(percent_Text);
                GridPanel.add(panel);
            }
        }

        GridPanel.revalidate();
        GridPanel.repaint();
    }

    private void clearGridPanel() {
        if (GridPanel != null) {
            GridPanel.removeAll();
            GridPanel.revalidate();
            GridPanel.repaint();
        }
    }

    private class MouseFunction implements MouseListener {
        private int base;
        private JPanel panel;

        MouseFunction(int base, JPanel panel) {
            this.base = base;
            this.panel = panel;
        }

        // Mouse Hover
        public void mouseEntered(MouseEvent e) {
            String label = String.format("Percent : %.2f%% | Volume Gas : %.2f", getPercent(base), getVolume(base));
            panel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
            monitor.setText(label);
        }

        // เวลาเมาส์ออกจาก panel นี้
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

    private double getPercent(int baseDepth) {
        int topHorizon = baseDepth - 200;
        int fluld = this.fluld;

        double total = baseDepth - topHorizon;
        double gas = Math.max(0, Math.min(fluld, baseDepth) - topHorizon);
        return gas / total * 100;
    }

    private double getVolume(int baseDepth) {
        int topHorizon = baseDepth - 200;
        int fluld = this.fluld;

        int thickness = fluld - (baseDepth - 200);
        if (thickness <= 0) {
            return 0; // ไม่มีแก๊ส
        }
        return GasConstants.GAS_BUTTON_WIDTH * GasConstants.GAS_BUTTON_LENGTH * thickness;
    }

    // ================= { GUI Frame } =================
    // ========= [ PANEL _ SOUTH ] =========
    public JPanel panel_south() {
        JPanel panel = new JPanel(new FlowLayout());

        panel.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        panel.setPreferredSize(new Dimension(0, 60));

        // สร้าง Box_red
        JPanel Box_red = new JPanel();
        Box_red.setBackground(Color.red);
        Box_red.setPreferredSize(new Dimension(45, 45));
        Box_red.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        JLabel red = new JLabel("RED is Gas 0%");
        red.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(Box_red);
        panel.add(red);

        // สร้าง Box_Yellow
        JPanel Box_Yellow = new JPanel();
        Box_Yellow.setBackground(Color.YELLOW);
        Box_Yellow.setPreferredSize(new Dimension(45, 45));
        Box_Yellow.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

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

        JButton button_about = createButtonAbout("About Group", 200, 30);

        panel.add(button_about);

        // เมือกดปุ่ม about จะขึ้น frame ใหม่
        button_about.addActionListener(e -> {
            AboutGroup about = new AboutGroup(FrameMain);
            // เปิดหน้า about
            about.setVisible(true);

            // ปิดหน้าหลักชั่วคราว
            FrameMain.setVisible(false);
        });

        return panel;
    }

    // ========= [ PANEL _ NORTH ] =========
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

    // ========= [ PANEL _ CENTER ] =========
    public JPanel panel_center() {
        panelWest = new JPanel(new BorderLayout());
        panelWest.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        panelWest.setPreferredSize(new Dimension(500, 500));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(GasConstants.COLOR_WINDOW);

        // createButton(200, panel_center);
        setPaddingEvery(panel);

        panel.add(panelWest, BorderLayout.CENTER);

        return panel;
    }

    // ========= [ Panel _ East ] =========
    public JPanel panel_East() {
        JPanel panelEast = new JPanel(new BorderLayout());
        panelEast.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        panelEast.setPreferredSize(new Dimension(400, 500));

        // Padding ด้านขวา / บน / ล่าง
        JPanel panel_eastPadding = new JPanel();
        panel_eastPadding.setBackground(GasConstants.COLOR_WINDOW);
        panel_eastPadding.setPreferredSize(new Dimension(25, 500));

        JPanel south_eastPanel = new JPanel();
        south_eastPanel.setBackground(GasConstants.COLOR_WINDOW);
        south_eastPanel.setPreferredSize(new Dimension(300, 25));

        JPanel north_eastPanel = new JPanel();
        north_eastPanel.setBackground(GasConstants.COLOR_WINDOW);
        north_eastPanel.setPreferredSize(new Dimension(300, 25));

        // Panel กลาง จัดแนวตั้ง
        JPanel panel_centerAll = new JPanel();
        panel_centerAll.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        panel_centerAll.setLayout(new BoxLayout(panel_centerAll, BoxLayout.Y_AXIS));
        panel_centerAll.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // ช่องว่างรอบๆ

        // Label หัวข้อ
        JLabel label = new JLabel("Fluid Contact Depth :");
        label.setFont(new Font("Arial", Font.BOLD, 17));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel_centerAll.add(label);

        panel_centerAll.add(Box.createRigidArea(new Dimension(0, 10))); // เว้นบรรทัด

        // JTextField fluidLevel
        Fluid_level = new JTextField(String.valueOf(GasConstants.FLULD_CONTACT));
        Fluid_level.setMaximumSize(new Dimension(300, 30));
        Fluid_level.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel_centerAll.add(Fluid_level);

        panel_centerAll.add(Box.createRigidArea(new Dimension(0, 20)));

        // Monitor เล็กลง
        monitor = new JLabel();
        monitor.setPreferredSize(new Dimension(300, 80));
        monitor.setMaximumSize(new Dimension(300, 80));
        monitor.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        monitor.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel_centerAll.add(monitor);

        panel_centerAll.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel ปุ่ม ใช้ GridLayout
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBackground(GasConstants.COLOR_WINDOW_SOUTH);
        buttonPanel.setMaximumSize(new Dimension(320, 180));

        JButton button_openFile = creaButtonOpenFile("OPEN FILE", 300, 35);
        JButton button_cal = createButtonCalculate("CALCULATE", 300, 50);
        JButton button_clear = creaButtonClear("CLEAR", 120, 25);

        button_openFile.addActionListener(e -> {
            if (GridPanel != null) {
                GridPanel.removeAll();
                GridPanel.revalidate();
            }
            openFile();
        });
        button_clear.addActionListener(e -> clearButton());
        button_cal.addActionListener(e -> updateData());

        buttonPanel.add(button_openFile);
        buttonPanel.add(button_cal);
        buttonPanel.add(button_clear);

        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel_centerAll.add(buttonPanel);

        panelEast.add(panel_centerAll, BorderLayout.CENTER);
        panelEast.add(panel_eastPadding, BorderLayout.EAST);
        panelEast.add(south_eastPanel, BorderLayout.SOUTH);
        panelEast.add(north_eastPanel, BorderLayout.NORTH);

        return panelEast;
    }

    private void clearButton() {
        baseGas = new int[0][0];
        this.row = 0;
        this.column = 0;
        clearGridPanel();
    }

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

    // ========= [ Padding เพิ่มช่องว่างทาง บน ล่าง ซ้าย ขวา ] =========
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

    // ========= [ Create Button ] =========
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
}
