package Client;

import java.net.*;
import java.util.ArrayList;
import java.io.*;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

class ClientTCP extends JFrame {
	static public boolean valid(final String ip) {
		String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";

		return ip.matches(PATTERN);
	}

	static public void ChupHinh() {
		try {
		if (!valid(Main.IP) || !InetAddress.getByName(Main.IP).isReachable(10)) {
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
			return;
		}
		Socket socket = new Socket(Main.IP, 7789);
		OutputStream outputStream = socket.getOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
		dataOutputStream.writeUTF("chup");
		dataOutputStream.flush(); 

		try {
			   BufferedImage image = ImageIO.read(socket.getInputStream());
		       JFrame frame=new JFrame();
		       frame.setSize(900, 800);
		       frame.setLayout(null);
		       JLabel label=new JLabel();
		       label.setBounds(0,0,900,700);
		       Image dimg = image.getScaledInstance(900, 700,
		    	        Image.SCALE_SMOOTH);
		       JButton btn = new JButton("SAVE HÌNH");
		       btn.setBounds(0, 700, 900, 100);
		       frame.setTitle("Captured Image");
		       frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		       
		       label.setIcon(new ImageIcon(dimg));
		       frame.add(label);
		       frame.setLocationRelativeTo(null);
		       //frame.pack();
		       frame.add(btn);

		       frame.setVisible(true);
			
			
		       btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						File file = FileChooser.getFileChooser();
						String name = null;
						if (file != null) {
							name = GetInput.GetPictureName();
						}
						if (file != null && name != null) {
							name += ".jpg";
							String path = file.getAbsolutePath() + "\\" + name;
							try {
								ImageIO.write(image, "jpg", new File(path));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Noti.infoBox("Đã lưu hình thành công !", "Thông báo");
					}					}
				});
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		socket.close();
		}catch(IOException e)
		{
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
		}
	}

	static public void TatMay() {
		try
		{
		if (!valid(Main.IP) || !InetAddress.getByName(Main.IP).isReachable(10)) {
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
			return;
		}
		Socket socket = new Socket(Main.IP, 7789);

		// get the output stream from the socket.
		OutputStream outputStream = socket.getOutputStream();
		// create a data output stream from the output stream so we can send data
		// through it
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

		// write the message we want to send
		dataOutputStream.writeUTF("tat");

		dataOutputStream.flush(); // send the message
		// dataOutputStream.close(); // close the output stream when we're done.

		Noti.infoBox("Đã tắt máy tính!", "Thông báo");

		socket.close();
		
		}catch(IOException e)
		{
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
		}
	}

	static public void TaskManager() 
	{
		
		try {
		if (!valid(Main.IP) || !InetAddress.getByName(Main.IP).isReachable(10)) {
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
			return;
		}
		JFrame frame = new JFrame("Quản lí Task Manager");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(500, 500);
		frame.setLayout(new GridLayout(3, 2, 20, 20));
		JButton btn = new JButton("Xem các process đang chạy");
		JButton btn2 = new JButton("Kill Process");
		JButton btn3 = new JButton("Start Process");
		// Lắng nghe sự kiện Click và xử lý
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				XemTaskManager();
			}
		});
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				KillTaskManager();
			}
		});
		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				StartTaskManager();
			}
		});

		frame.add(btn);
		frame.add(btn2);
		frame.add(btn3);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		// socket.close();
		}catch(IOException e)
		{
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
		}
	}

	public static String getProcessName(String input) {
		File file = new File(input);
		return file.getName();
	}


	public static void XemTaskManager() {
		try {

			ArrayList<String> myList = new ArrayList<String>();
			if (!valid(Main.IP) || !InetAddress.getByName(Main.IP).isReachable(10)) {
				Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
				return;
			}
			Socket socket = new Socket(Main.IP, 7789);
			OutputStream outputStream = socket.getOutputStream();
			// create a data output stream from the output stream so we can send data
			// through it
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

			// write the message we want to send
			dataOutputStream.writeUTF("xem");

			ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
			try {
				Object object = objectInput.readObject();
				myList = (ArrayList<String>) object;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			String[][] tableData = new String[myList.size()][2];
			for(int i = 0 ; i < myList.size(); i++)
			{
				tableData[i][0] = myList.get(i).split("&")[0];
				tableData[i][1] = myList.get(i).split("&")[1];
			}
			String[] colnames = {"Tên Process", "PID"};

			    TableModel model = new DefaultTableModel(tableData, colnames) {
			      public Class getColumnClass(int column) {
			        Class returnValue;
			        if ((column >= 0) && (column < getColumnCount())) {
			          returnValue = getValueAt(0, column).getClass();
			        } else {
			          returnValue = Object.class;
			        }
			        return returnValue;
			      }
			    };
			    JFrame frame = new JFrame("Các process đang chạy");
			    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			    final JTable table = new JTable(model);
			    table.setFont(new Font("Arial", Font.BOLD, 15));
			    table.setEnabled(false);
			    final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
			    table.setRowSorter(sorter);
			    JScrollPane pane = new JScrollPane(table);
			    frame.add(pane, BorderLayout.CENTER);
			    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			    centerRenderer.setHorizontalAlignment( JLabel.CENTER );
			    table.setDefaultRenderer(String.class, centerRenderer);
			    JPanel panel = new JPanel(new BorderLayout());
			    JLabel label = new JLabel("Lọc");
			    panel.add(label, BorderLayout.WEST);
			    final JTextField filterText = new JTextField("Nhập tên của process cần lọc !");
			    panel.add(filterText, BorderLayout.CENTER);
			    frame.add(panel, BorderLayout.NORTH);
			    JButton button = new JButton("Lọc");
			    button.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent e) {
			        String text = filterText.getText();
			        if (text.length() == 0) {
			          sorter.setRowFilter(null);
			        } else {
			          sorter.setRowFilter(RowFilter.regexFilter(text));
			        }
			      }
			    });
			    frame.add(button, BorderLayout.SOUTH);
			    frame.setSize(500, 500);
			    frame.setVisible(true);
		}catch(IOException e)
		{
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
		}
	}

	public static void KillTaskManager() {
		try {

			if (!valid(Main.IP) || !InetAddress.getByName(Main.IP).isReachable(10)) {
				Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
				return;
			}
			Socket socket = new Socket(Main.IP, 7789);

			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeUTF("kill");

			String process_PID = GetInput.GetProcessPID();
			dataOutputStream.writeUTF(process_PID);

			InputStream inputStream = socket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			String message = dataInputStream.readUTF();
			if (message.equals("ok")) {
				Noti.infoBox("Đã kill process!", "Thông báo");
			} else {
				Noti.warnBox("Lỗi !", "Thông báo");
			}
			socket.close();

		}catch(IOException e)
		{
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
		}
	}

	public static void StartTaskManager() {
		try {

			if (!valid(Main.IP) || !InetAddress.getByName(Main.IP).isReachable(10)) {
				Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
				return;
			}
			Socket socket = new Socket(Main.IP, 7789);

			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeUTF("start");

			String process_PID = GetInput.GetProcessName();
			dataOutputStream.writeUTF(process_PID);

			InputStream inputStream = socket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			String message = dataInputStream.readUTF();
			if (message.equals("ok")) {
				Noti.infoBox("Đã start process!", "Thông báo");
			} else {
				Noti.warnBox("Lỗi !", "Thông báo");
			}
			socket.close();

		}catch(IOException e)
		{
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
		}
	}

	public static void KeyLogger() throws UnknownHostException {
		
		try {
		if (!valid(Main.IP) || !InetAddress.getByName(Main.IP).isReachable(10)) {
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
			return;
		}
		StopKeyLogger();
		ClearKeyLogger();
		JFrame frame = new JFrame("Key Logger");
		frame.setSize(650, 300);
		frame.setLayout(new BorderLayout());

		// frame.setLayout(new GridLayout(1,1,15,15));
		frame.setLayout(null);
		// create JLabel
		// Tạo Button
		JButton btn = new JButton("Start");
		JButton btn2 = new JButton("Stop");
		JButton btn3 = new JButton("Show");
		JButton btn4 = new JButton("Clear");
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		btn.setBounds(50, 10, 100, 50);
		btn2.setBounds(200, 10, 100, 50);
		btn3.setBounds(350, 10, 100, 50);
		btn4.setBounds(500, 10, 100, 50);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(50, 100, 550, 100);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		textArea.setEditable(false);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				StartKeyLogger();
			}
		});
		// Lắng nghe sự kiện Click và xử lý
		btn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				StopKeyLogger();
			}
		});
		btn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(ShowKeyLogger());
			}
		});
		btn4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ClearKeyLogger();
				textArea.setText(null);
			}
		});
		frame.add(btn);
		frame.add(btn2);
		frame.add(btn3);
		frame.add(btn4);
		frame.add(scrollPane);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setLayout(null);
		}catch(IOException e)
		{
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
		}

	}

	public static void StartKeyLogger() {
		try {

			if (!valid(Main.IP) || !InetAddress.getByName(Main.IP).isReachable(10)) {
				Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
				return;
			}
			Socket socket = new Socket(Main.IP, 7789);

			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeUTF("startlog");

			socket.close();

		}catch(IOException e)
		{
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
		}
	}

	public static void StopKeyLogger() {
		try {

			if (!valid(Main.IP) || !InetAddress.getByName(Main.IP).isReachable(10)) {
				Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
				return;
			}
			Socket socket = new Socket(Main.IP, 7789);

			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeUTF("stoplog");

			socket.close();

		}catch(IOException e)
		{
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
		}
	}

	public static String ShowKeyLogger() {
		try {

			if (!valid(Main.IP) || !InetAddress.getByName(Main.IP).isReachable(10)) {
				Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
				return null;
			}
			String res = null;
			Socket socket = new Socket(Main.IP, 7789);

			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeUTF("showlog");

			InputStream inputStream = socket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);
			res = dataInputStream.readUTF();

			socket.close();
			return res;

		}catch(IOException e)
		{
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
		}
		return null;
	}

	public static void ClearKeyLogger() {
		try {

			if (!valid(Main.IP) || !InetAddress.getByName(Main.IP).isReachable(10)) {
				Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
				return;
			}
			Socket socket = new Socket(Main.IP, 7789);

			OutputStream outputStream = socket.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
			dataOutputStream.writeUTF("clearlog");
			socket.close();

		}catch(IOException e)
		{
			Noti.warnBox("Không thể kết nối đến server!", "Thông báo");
		}
	}
}
