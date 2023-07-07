package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class TaskManager {
    public static String getProcessName(String input)
    {
        File file = new File(input);
        return file.getName();
    }
    public static void Xem(Socket connectionSocket) {
        try {
            String line;
            ArrayList<ProcessHandle> myListPro = new ArrayList<ProcessHandle>();
            ProcessHandle.allProcesses()
                    .forEach(process ->myListPro.add(process));
            ArrayList<String> myList = new ArrayList<String>();
            myList.add(String.format("%8s %8s", "[ProcessName.exe]", "[PID]"));
            for(int i = 0; i< myListPro.size(); i++)
            {
                var pathname = myListPro.get(i).info().command();
                if (pathname.isPresent()) {

                    myList.add(String.format("%8s %8d", getProcessName(pathname.get()),myListPro.get(i).pid()));
                }
            }
                ObjectOutputStream objectOutput = new ObjectOutputStream(connectionSocket.getOutputStream());
                objectOutput.writeObject(myList);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
    public static void Kill(String PID, Socket connectionSocket) {
        try {
            String cmd = "taskkill /F /PID " + PID;
            Runtime.getRuntime().exec(cmd);
            OutputStream outputStream = connectionSocket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF("ok");
        } catch (Exception err) {
            try {
                OutputStream outputStream = connectionSocket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeUTF("error");
                err.printStackTrace();
            }catch(Exception e)
            {
                err.printStackTrace();
            }
        }
    }
    public static void Start(String name, Socket connectionSocket) {
        Runtime rs = Runtime.getRuntime();
        try {
            rs.exec(name);
            OutputStream outputStream = connectionSocket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeUTF("ok");
        }
        catch (IOException e) {
            try {
                OutputStream outputStream = connectionSocket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeUTF("error");
                e.printStackTrace();
            }catch(Exception er)
            {
                er.printStackTrace();
            }
        }
    }
}
