package Client;

import javax.swing.*;

import Server.ScreenShot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class Main extends JFrame {
	private JLabel lb;
	static public Socket main_socket;
	static public String IP;
	static public int command = 0;

	public Main() {
		// create JFrame
		JFrame frame = new JFrame("Điều khiển máy tính");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(540, 450);
		// frame.setLayout(new GridLayout(1, 1,20,20));
		frame.setLayout(null);
		// create JLabel
		// Tạo Button
		JButton btn = new JButton("Chụp màn hình");
		JButton btn2 = new JButton("Tắt máy");
		JButton btn3 = new JButton("Quản lí Task manager");
		JButton btn4 = new JButton("Key Logger");
		JButton btn5 = new JButton("Thay đổi địa chỉ IP của server");
		btn.setBounds(50, 0, 200, 100);
		btn2.setBounds(270, 0, 200, 100);
		btn3.setBounds(50, 150, 200, 100);
		btn4.setBounds(270, 150, 200, 100);
		btn5.setBounds(170, 300, 200, 100);
		// Lắng nghe sự kiện Click và xử lý
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					ClientTCP.ChupHinh();
			}
		});
		// Lắng nghe sự kiện Click và xử lý
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					ClientTCP.TatMay();
			}
		});
		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
					ClientTCP.TaskManager();
					// TODO Auto-generated catch block
			}
		});
		btn4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					ClientTCP.KeyLogger();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btn5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.IP = GetInput.GetIP();

			}
		});
		frame.add(btn);
		frame.add(btn2);
		frame.add(btn3);
		frame.add(btn4);
		frame.add(btn5);
		// display JFrame
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	// change text of lb
	private void ScreenShotCaptured() {
		lb.setText("Screen Shot Captured");
	}

	public static void main(String[] args) throws IOException {
		Main.IP = GetInput.GetIP();
		if (!ClientTCP.valid(IP) || !InetAddress.getByName(Main.IP).isReachable(10)) {
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
		}
		new Main();
	}
}
