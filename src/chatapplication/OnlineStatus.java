package chatapplication;

import java.io.*;
import static java.lang.Thread.sleep;
import java.net.*;
import java.util.*;

public class OnlineStatus implements Runnable{
    DatagramSocket s;
    OnlineStatus() {
        try {
            s=new DatagramSocket();
        }catch(SocketException ex) {
            System.err.println(ex);
        }
    }
    @Override
    public void run() { 
        while(true)
        { 
            try {
                byte[] buf ;
                buf=MulticastClient.name.getBytes();
                // send it      
                InetAddress group = InetAddress.getByName("230.0.0.2");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 5000);
                s.send(packet);  
                try{
                    sleep((long)(Math.random() * 10000));
                }catch(Exception e){
                    System.err.println(e);
                }
            }       
            catch (IOException e) {
                System.out.println("error in online status class");
                s.close();
            }
        }
    }
}

class ReceiveOnlineStatus implements Runnable {
    InetAddress address=null;
    MulticastSocket socket=null;
    public static ArrayList OnlineUsersList=new ArrayList();
    ReceiveOnlineStatus() {
        try {
            socket = new MulticastSocket(5000) ;
            address=InetAddress.getByName("230.0.0.2");
            socket.joinGroup(address);
        }
        catch(Exception e) {
            System.err.println("error");
        }
    }
    @Override
    public void run() {
        OnlineUsersList=new ArrayList();
        while(true) { 
            try {
                DatagramPacket packet;
                byte[] buf = new byte[256];
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String name=new String(packet.getData(), 0, packet.getLength());
                if(name.equals("exited")) {
                    OnlineUsersList=new ArrayList();
                }
                if(!OnlineUsersList.contains(name)&& !name.equals("exited"))
                {
                    OnlineUsersList.add(name);
                    if(MulticastClient.onlineUsers.getText().equals("")) {
                        MulticastClient.onlineUsers.setText(name);
                    }
                    else { 
                        MulticastClient.onlineUsers.setText("");
                        for(Object obj:OnlineUsersList) {
                            MulticastClient.onlineUsers.setText(MulticastClient.onlineUsers.getText()+obj.toString()+"\n");  
                        }
                    }       
                }
            }
            catch(Exception e) {
                System.err.println(e);
            }
        }
    }
}