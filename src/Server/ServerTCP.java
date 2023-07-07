package Server;


import java.io.*;
import java.net.*;
import java.awt.AWTException;
import java.awt.*; 
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
class SeverTCP{
	private JLabel lb;
	static public Socket main_socket;
	static public String IP;
	static public int command = 0;
	static int is_open = 0;

	public static void Button() {
		// create JFrame
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(100, 150, 300, 100);
		textArea.setFont(new Font("Arial", Font.PLAIN, 30));
		try {
			textArea.setText("      " + InetAddress.getLocalHost().getHostAddress() + '\n' +"   " +  "Đang đóng kết nối");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JFrame frame = new JFrame("SERVER");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		// frame.setLayout(new GridLayout(1, 1,20,20));
		frame.setLayout(null);
		// create JLabel
		// Tạo Button
		JButton btn = new JButton("Mở kết nối");

		btn.setBounds(150, 10, 200, 100);

		// Lắng nghe sự kiện Click và xử lý
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				is_open = 1;
				try {
					textArea.setText("      " + InetAddress.getLocalHost().getHostAddress() + '\n' +"     " +  "Đang mở kết nối");
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		

		frame.add(btn);
		frame.add(scrollPane);

		// display JFrame
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void Run() throws Exception
	{
		Button();
        while(is_open != 1)
        {
        	System.out.print("");
        }

        ServerSocket welcomeSocket = new ServerSocket(7789);
        while(true)
        {
            Socket connectionSocket = welcomeSocket.accept();
            InputStream inputStream = connectionSocket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String message = dataInputStream.readUTF();
            if(message.equals("chup"))
            {
                ScreenShot.TakeSrceenShot(connectionSocket);
            }
            else if(message.equals("tat"))
            {
                ShutDown.Do();
            }
            else if(message.equals("xem"))
            {
                TaskManager.Xem(connectionSocket);
            }
            else if(message.equals("kill"))
            {
                String PID = dataInputStream.readUTF();
                TaskManager.Kill(PID, connectionSocket);
            }
            else if(message.equals("start"))
            {

                String name = dataInputStream.readUTF();
                TaskManager.Start(name, connectionSocket);
            }
            else if(message.equals("startlog"))
            {
                KeyLogger.init();
                KeyLogger.Start();
            }
            else if(message.equals("stoplog"))
            {
                KeyLogger.Stop();
            }
            else if(message.equals("showlog"))
            {
                KeyLogger.Show(connectionSocket);
            }
            else if(message.equals("clearlog"))
            {
                KeyLogger.Clear();
            }
            connectionSocket.close();
        }
	}
    public static void main(String argv[])throws Exception{
    	Run();
    }
}