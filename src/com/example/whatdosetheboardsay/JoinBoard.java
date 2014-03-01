package com.example.whatdosetheboardsay;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

import android.util.Log;

public class JoinBoard {
	
	/**
	 * @param serverIP
	 * @return -1 if Failed, otherwise return client number.
	 */
	public static int join(String serverIP) {
        try {
    		InetAddress serverAddr = InetAddress.getByName(serverIP);
    		InetAddress localAddr = InetAddress.getByName(GDB_sc.GetLocalIpAddress());
        	Log.d("UDP", "C: Connecting... to " + serverIP);
        	DatagramSocket socket = new DatagramSocket(2333, localAddr);
        	Log.d("UDP", "C: CheckPoint");
        	byte[] buf = GDB_sc.GetLocalIpAddress().getBytes();
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
            return ByteBuffer.wrap(bufsize).getInt();
        } catch (Exception e) {
            Log.e("UDP", "C: Error", e);
            return -1;
        }		
	}
	/**
	 * @param serverIP
	 * @param password
	 * @return -1 if Failed, otherwise return client number.
	 */
	public static int join(String serverIP, String password) {
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
            return ByteBuffer.wrap(bufsize).getInt();
        } catch (Exception e) {
            Log.e("UDP", "C: Error", e);
            return -1;
        }		
	}
}
