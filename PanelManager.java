    import javax.swing.*;
    import javax.swing.filechooser.FileNameExtensionFilter;
    import java.io.*;
    import java.util.Scanner;
    import java.awt.*;

    public class PanelManager {
        private DisplayFrame FrameMain;
        private int[][] baseGas = new int[10][20];

        // ========= [ ส่ง MainFrame มาที่ไฟล์นี้ ] =========
        public PanelManager(DisplayFrame frame) {
            this.FrameMain = frame;
        }

        // ================= { Function } =================
        // ========= [ OpenFILE ] =========
        private void openFile() {
            JFileChooser file_choose = new JFileChooser();

            FileNameExtensionFilter filter_txt = new FileNameExtensionFilter("Please txt", "txt");
            
            // set ให้เปิดได้เเค่ .txt
            file_choose.setFileFilter(filter_txt);

            int result = file_choose.showOpenDialog(FrameMain);
            System.out.println(result);
            if (result == JFileChooser.APPROVE_OPTION) {
                File select_File = file_choose.getSelectedFile();

                // เอาข้อมูลตัวเลขมาจากไฟล์
                getDataInFile(select_File);
                // loop ดูข้อมูลในอาเรย์
                checkDataInFile();

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
            try {
                BufferedReader read = new BufferedReader(new FileReader(select_File));
                for (int i = 0; ; i++) {
                    String line = read.readLine();
                    if (line == null) {
                        break;
                    }
                    String[] temporary = line.trim().split("\\s+");
                    for (int j = 0; j < temporary.length; j++) {
                        baseGas[i][j] = Integer.parseInt(temporary[j]);
                    }
                }
                read.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (IOException err) {
                System.out.println(err.getMessage());
            }
            
        }

        // ========= [ Create ปุ่มเยอะๆตรงกลาง ] =========
        public void createButton(int count, JPanel panel) {
            JButton[] button = new JButton[count];
            for (int i = 0; i < button.length; i++) {
                button[i] = new JButton();
                button[i].setOpaque(true);
                /* button[i].setBorderPainted(false); */
                button[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                button[i].setBackground(randomColor());
                panel.add(button[i]);
            }
        }

        // ========= [ Random Color ] =========
        public Color randomColor() {
            Color[] color = { Color.green, Color.red, Color.YELLOW };
            int index = (int) (Math.random() * color.length);
            return color[index];
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

                //ปิดหน้าหลักชั่วคราว
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


        // ========= [ Panel _ East ] =========
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

            // ---------- [ ปุ่ม CALCULATE ] ----------
            JButton button_cal = createButtonCalculate("CALCULATE", 300, 50);
            
            // ---------- [ ปุ่ม CLEAR ] ----------
            JButton button_clear = creaButtonClear("CLEAR", 120, 25);

            // ---------- [ ปุ่ม OPENFILE ] ----------
            JButton button_openFile = creaButtonOpenFile("OPEN FILE", 300, 35);       

            // ---------- [ addActionListener ให้กับปุ่ม OpenFile ] ----------
            button_openFile.addActionListener(e -> {
                openFile();
            });

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
