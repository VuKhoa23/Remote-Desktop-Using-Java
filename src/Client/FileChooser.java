package Client;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class FileChooser {
	static public File getFileChooser() {
		JFileChooser f = new JFileChooser();
		f.setDialogTitle("Chọn thư mục để save hình");
		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		f.showSaveDialog(null);

		return f.getSelectedFile();
	}
}