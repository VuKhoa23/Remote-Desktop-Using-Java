package Client;

import javax.swing.*;

public class GetInput {
	static public String GetPictureName() {
		JFrame frame = new JFrame();
		String s = (String) JOptionPane.showInputDialog(frame, "Hình được lưu dưới dạng " + '"' + "Tên.jpg" + '"',
				"Nhập tên hình: ", JOptionPane.QUESTION_MESSAGE);
		return s;
	}

	static public String GetProcessPID() {
		JFrame frame = new JFrame();
		String s = (String) JOptionPane.showInputDialog(frame, "Nhập PID của process", "Nhập process cần kill",
				JOptionPane.QUESTION_MESSAGE);
		return s;
	}

	static public String GetProcessName() {
		JFrame frame = new JFrame();
		String s = (String) JOptionPane.showInputDialog(frame, "Nhập tên của process", "Nhập process cần start",
				JOptionPane.QUESTION_MESSAGE);
		return s;
	}

	static public String GetIP() {
		JLabel name_label = new JLabel(".jpg");
		JFrame frame = new JFrame();
		frame.add(name_label);
		String s = (String) JOptionPane.showInputDialog(frame, "Địa chỉ IP hiện tại: " + Main.IP, "Nhập IP",
				JOptionPane.QUESTION_MESSAGE);
		return s;
	}
}
