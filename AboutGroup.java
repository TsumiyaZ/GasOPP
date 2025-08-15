import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class AboutGroup extends JFrame{

    private DisplayFrame MainFrame;

    AboutGroup(DisplayFrame frame) {
        this.MainFrame = frame;
        setTitle("About Group");
        setSize(GasConstants.WINDOW_WIDTH, GasConstants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setBackground(GasConstants.COLOR_WINDOW_SOUTH);

        P_south();
        About_Topic();
        Mid_Panel();

        
        
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

    public void About_Topic ()
    {
        JPanel panel = create_Panel_Topic();
        JLabel about_topic = new JLabel("About Group");

        panel.setLayout(new FlowLayout());
        panel.setBackground(GasConstants.COLOR_WINDOW);
        panel.setBorder(new LineBorder(Color.BLACK, 2));

        about_topic.setFont(new Font(null, Font.BOLD, 36));

        panel.add(about_topic);
        add(panel, BorderLayout.NORTH);
    }

    public  JPanel create_Panel_Topic ()
    {
        JPanel about_panel = new JPanel();
        return about_panel;
    }

    public void Mid_Panel ()
    {
        JPanel panel = create_Panel_Topic();
        JLabel[] add_label = Add_Label();
        JPanel[] add_name = Add_Name(add_label);
        JPanel[] add_picture = Add_Picture();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new LineBorder(Color.BLACK, 2));
        for (int i=0; i< Add_Picture().length;i++)
        {
            JPanel picture_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            picture_panel.add(add_picture[i]);
            picture_panel.add(add_name[i]);
            panel.add(picture_panel);
            add_picture[i].add(picture());
        }
        add(panel, BorderLayout.CENTER);
    }

    public JPanel[] Add_Picture ()
    {
        JPanel[] a_picture = new JPanel[3];
        for (int i = 0; i < a_picture.length; i++)
        {
            a_picture[i] = new JPanel();
            a_picture[i].setPreferredSize(new Dimension(150, 200));
            a_picture[i].setBorder(new LineBorder(Color.BLACK, 2));
        }
        return a_picture;
    }


    public JPanel[] Add_Name (JLabel[] Add_Label)
    {
        JPanel[] add_name = new JPanel[3];
        for (int i = 0; i < add_name.length; i++)
        {
            add_name[i] = new JPanel();
            add_name[i].setPreferredSize(new Dimension(500, 200));
            add_name[i].setLayout(new FlowLayout(FlowLayout.CENTER,0,60));
            add_name[i].setBorder(new LineBorder(Color.BLACK, 2));
            add_name[i].add(Add_Label[i]);
        }
        return add_name;
    }

    public JLabel[] Add_Label() {
    JLabel[] add_label = new JLabel[3]; 
    for (int i = 0; i < add_label.length; i++) {
        String data = "<html>NAME : " + GasConstants.GAS_NAME_TEXT[i] + "<br>ID : " + GasConstants.GAS_ID_TEXT[i] + "</html>";
        add_label[i] = new JLabel(data);
        add_label[i].setFont(new Font(null, Font.BOLD, 24));
    }
    return add_label;
}


    public JLabel picture()
    {
        ImageIcon picture_image = new ImageIcon("PICTURE1.jpg");
        Image image = picture_image.getImage().getScaledInstance(130, 185, Image.SCALE_SMOOTH);
        ImageIcon Ic_Image = new ImageIcon(image);
        JLabel picture = new JLabel(Ic_Image);
        return picture;
    }



    
}