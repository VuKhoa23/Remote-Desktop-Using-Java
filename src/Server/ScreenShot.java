package Server;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.imageio.ImageIO;

public class ScreenShot {
    public static final long serialVersionUID = 1L;
    static public void TakeSrceenShot(Socket connectionSocket)
    {
        try {

            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage capture = new Robot().createScreenCapture(screenRect);
            connectionSocket.getOutputStream().flush();
            ImageIO.write(capture, "jpg", connectionSocket.getOutputStream());
            connectionSocket.getOutputStream().flush();
            InetSocketAddress socketAddress = (InetSocketAddress)
                    connectionSocket.getRemoteSocketAddress();
            String addr = socketAddress.getAddress().getHostAddress();
            System.out.println(addr);
        } catch (Exception e) {
            System.out.println("Error getting screenshot");
        }
    }
}
