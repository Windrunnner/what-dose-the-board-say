package com.example.whatdosetheboardsay;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import android.util.Log;
 
public class Server implements Runnable  {
 
        public static String ServerIP = "127.0.0.1";
        public static final int SERVERPORT = 2333;
        
        @Override
        public void run() {
                ServerIP = GDB_sc.GetLocalIpAddress();
                byte[] bufsize = new byte[4];
                    try {
                        InetAddress serverAddr = InetAddress.getByName(ServerIP);
                        Log.d("UDP", "S: Connecting...");
                        DatagramSocket socket = new DatagramSocket(SERVERPORT, serverAddr); 
                        while(true){
                            
                        DatagramPacket packet = new DatagramPacket(bufsize, 4);
                        Log.d("UDP", "S: Receiving datasize...");
                        socket.receive(packet);
                        Log.d("UDP", "S: Received 1 :" + bufsize[0]);
                        Log.d("UDP", "S: Received 2 :" + bufsize[1]);
                        Log.d("UDP", "S: Received 3 :" + bufsize[2]);
                        Log.d("UDP", "S: Received 4 :" + bufsize[3]);
                        Log.d("UDP", "S: Received :" + ByteBuffer.wrap(bufsize).getInt());
                        if(ByteBuffer.wrap(bufsize).getInt()>0x1000000)
                                continue;
                        
                        byte[] buf = new byte[ByteBuffer.wrap(bufsize).getInt()];
                        //delete packet;
                        
                        packet = new DatagramPacket(buf, buf.length);
                        Log.d("UDP", "S: Receiving data...");
                        socket.receive(packet);
                        Log.d("UDP", "S: Received: '" + new String(packet.getData()) + "'");
                        Log.d("UDP", "S: Done.");
                        }
                } catch (Exception e) {
                        Log.e("UDP", "S: Error", e);
                }
                
        }
}