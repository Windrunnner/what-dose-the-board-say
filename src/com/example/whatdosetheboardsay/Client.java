package com.example.whatdosetheboardsay;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import android.util.Log;
 
public class Client implements Runnable {
		private String ClientIP = "127.0.0.1";
		private String ServerIP = "127.0.0.1";
		public static String str = "";
        @Override
        public void run() {
        	ClientIP = GDB_sc.GetLocalIpAddress();
        	ServerIP = GDB_sc.GetServerIpAddress();
                try {
                		InetAddress serverAddr = InetAddress.getByName(ServerIP);
                    	Log.d("UDP", "C: Connecting... to " + ServerIP);
                    	DatagramSocket socket = new DatagramSocket();
                    	while(true){
                    		synchronized (MainActivity.client) {
                    			MainActivity.client.wait();
                    		}
                    		Log.d("UDP", "C: CheckPoint");
                    		byte[] buf = str.getBytes();
                        	DatagramPacket packet = new DatagramPacket(ByteBuffer.allocate(4).putInt(buf.length).array(),4, serverAddr, 2333);
                        	Log.d("UDP", "C: Sending: size '" + String.valueOf(buf.length) + "'");
                        	socket.send(packet);
                        	packet = new DatagramPacket(buf, buf.length, serverAddr, 2333);
                        	Log.d("UDP", "C: Sending: data '" + new String(buf) + "'");
                        	socket.send(packet);
                        	Log.d("UDP", "C: Done.");
                    	}
                } catch (Exception e) {
                        Log.e("UDP", "C: Error", e);
                }
        }
}