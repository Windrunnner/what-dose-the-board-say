package com.example.whatdosetheboardsay;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import android.util.Log;

/**
 * The JoinBoard class has two static method. Called can know if the 
 * filled IP and password are valid by the returned int. 
 * @author Len
 *
 */
public class JoinBoard implements Runnable {
	public static String serverIP;
	public static String password;
	
	/**
	 * @param serverIP
	 * @return -1 if Failed, otherwise return client number.
	 */

	public void run() {
		if (!password.equals("")){
			join(serverIP, password);
		}else {
	        try {
	    		InetAddress serverAddr = InetAddress.getByName(serverIP);
	    		InetAddress localAddr = InetAddress.getByName(GDB_sc.GetLocalIpAddress());
	        	Log.d("UDP", "C: Connecting... to " + serverIP);
	        	DatagramSocket socket = new DatagramSocket(2333, localAddr);
	        	Log.d("UDP", "C: CheckPoint");
	        	byte[] buf = GDB_sc.GetLocalIpAddress().getBytes();
	            DatagramPacket packet = new DatagramPacket(ByteBuffer.allocate(4).putInt(buf.length).array(),4, serverAddr, 2333);
	            byte[] bufsize = new byte[4];
				Log.d("UDP", "C: Sending: size '" + String.valueOf(buf.length) + "'");
	            socket.send(packet);
	            packet = new DatagramPacket(buf, buf.length, serverAddr, 2333);
	            Log.d("UDP", "C: Sending: data '" + new String(buf) + "'");
	            socket.send(packet);
	            Log.d("UDP", "C: Done.");
	            DatagramPacket returnPacket = new DatagramPacket(bufsize , 4);
	            socket.receive(returnPacket);
	            Log.d("UDP", "C: Recieved");
	            JoinInterface_activity.uid = ByteBuffer.wrap(bufsize).getInt();
	        	synchronized (JoinInterface_activity.joinThread) {
	        		JoinInterface_activity.joinThread.notify();
	        	}
	        } catch (Exception e) {
	        	JoinInterface_activity.uid = -1;
	            Log.e("UDP", "C: Error", e);
	        	synchronized (JoinInterface_activity.joinThread) {
	        		JoinInterface_activity.joinThread.notify();
	        	}
	        }		
		}
	}
	/**
	 * @param serverIP
	 * @param password
	 * @return -1 if Failed, otherwise return client number.
	 */
	public static void join(String serverIP, String password) {
        try {
    		InetAddress serverAddr = InetAddress.getByName(serverIP);
    		InetAddress localAddr = InetAddress.getByName(GDB_sc.GetLocalIpAddress());
        	Log.d("UDP", "C: Connecting... to " + serverIP);
        	DatagramSocket socket = new DatagramSocket(2333, localAddr);
        	Log.d("UDP", "C: CheckPoint");
        	byte[] buf = (GDB_sc.GetLocalIpAddress() + "|" + password).getBytes();
            DatagramPacket packet = new DatagramPacket(ByteBuffer.allocate(4).putInt(buf.length).array(),4, serverAddr, 2333);
            byte[] bufsize = new byte[4];
			DatagramPacket returnPacket = new DatagramPacket(bufsize , 4);
            Log.d("UDP", "C: Sending: size '" + String.valueOf(buf.length) + "'");
            socket.send(packet);
            packet = new DatagramPacket(buf, buf.length, serverAddr, 2333);
            Log.d("UDP", "C: Sending: data '" + new String(buf) + "'");
            socket.send(packet);
            Log.d("UDP", "C: Done.");
            socket.receive(returnPacket);
            JoinInterface_activity.uid = ByteBuffer.wrap(bufsize).getInt();
        	synchronized (JoinInterface_activity.joinThread) {
        		JoinInterface_activity.joinThread.notify();
        	}
        } catch (Exception e) {
        	JoinInterface_activity.uid = -1;
            Log.e("UDP", "C: Error", e);
        	synchronized (JoinInterface_activity.joinThread) {
        		JoinInterface_activity.joinThread.notify();
        	}
        }		
	}
}
