/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chattingapplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;
public class Server implements ActionListener{
    JLabel back,profile,video, phone, options;
    JButton send;
    JTextField text;
    JPanel a1;
    static Box vertical =  Box.createVerticalBox();
    static JFrame frame;
    static DataOutputStream dout;
    public Server(){
        
        frame = new JFrame();
        frame.setLayout(null);
        
        JPanel p = new JPanel();
        p.setBackground(new Color(7,94,84));
        p.setLayout(null);
        p.setBounds(0,0,450,70);
        frame.add(p);
      
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p.add(back);
        back.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p.add(profile);
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        p.add(video);
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        phone = new JLabel(i12);
        phone.setBounds(360,20,30,30);
        p.add(phone);
        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(15, 20, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        options = new JLabel(i15);
        options.setBounds(400,20,20,30);
        p.add(options);
        
        JLabel name = new JLabel("Manikant");
        name.setBounds(100,17,100,30);
        name.setFont(new Font("raleway", Font.BOLD, 15));
        name.setForeground(Color.WHITE);
        p.add(name);
        
        JLabel status = new JLabel("Active Now");
        status.setBounds(100,40,100,20);
        status.setFont(new Font("SAN_SARIF", Font.BOLD, 10));
        status.setForeground(Color.WHITE);
        p.add(status);
        
          
        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        frame.add(a1);
        
        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN_SARIF", Font.PLAIN, 16));
        frame.add(text);
        
        send = new JButton("Send");
        send.setBounds(320,655,125,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        frame.add(send);

        
        frame.setSize(450,700);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLocation(250,10);
        frame.setUndecorated(true);
        frame.setVisible(true);
        
    }
    public static void main(String[] args){
        new Server();
        
        try{
            ServerSocket skt = new ServerSocket(6001);
            while(true){
                Socket s = skt.accept();
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());
                
                while(true){
                    String msg = din.readUTF();
                    JPanel panel = formatedPanel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    frame.validate();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
      try{
          String msg = text.getText();
          
          JPanel p2 = formatedPanel(msg);
          a1.setLayout(new BorderLayout());

          JPanel right = new JPanel(new BorderLayout());
          right.add(p2, BorderLayout.LINE_END);
          vertical.add(right);
          vertical.add(Box.createVerticalStrut(15));
          a1.add(vertical, BorderLayout.PAGE_START);


          dout.writeUTF(msg); 
          
          text.setText("");
          
          frame.repaint();
          frame.invalidate();
          frame.validate();
    }catch(Exception e){
              e.printStackTrace();
          }

        }
    
    public static JPanel formatedPanel(String msg){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel message = new JLabel("<html><p style=\"width:150px\">"+msg+"</html>");
        message.setFont(new Font("Tahoma", Font.PLAIN, 16));
        message.setBackground(new Color(37,211,102));
        message.setOpaque(true);
        message.setBorder(new EmptyBorder(15,15,15,50));
        
        panel.add(message);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        return panel;
    }
    
}
