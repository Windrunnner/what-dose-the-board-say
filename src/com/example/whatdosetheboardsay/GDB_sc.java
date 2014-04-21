package com.example.whatdosetheboardsay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class GDB_sc implements Serializable{
	// http://silverballsoftware.com/get-your-ip-address-android-code
	private static String ServerIP = "127.0.0.1";
	static DatagramSocket socket;
	private static boolean isServer = true;
	public static String GetLocalIpAddress()
    {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                for (Enumeration<InetAddress> enumIpAddr = en.nextElement().getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
                    	//System.out.println(inetAddress.getHostAddress().toString());
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            return "ERROR Obtaining IP";
        }
        return "No IP Available";   
    }
	public static void setServer(){
		isServer = true;
	}
	public static void setClient(){
		isServer = false;
	}
	
	public static boolean getIsServer(){
		return isServer;
	}
	
	public static byte[] getBytes(Object obj) throws IOException
	{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bout);
		out.writeObject(obj);
		out.flush();
		byte[] bytes = bout.toByteArray();
		bout.close();
		out.close();
		
		return bytes;
	}
	
	public static Object getObject(byte[] bytes) throws IOException, ClassNotFoundException
	{
		ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
		ObjectInputStream oi = new ObjectInputStream(bi);
		Object obj = oi.readObject();
		bi.close();
		oi.close();
		
		return obj;
	}
	
	public static void sendByteMessage(byte[] msg){
		if(isServer)
			CreateInterfaceActivity.server.ssm.sendMessage(msg);
		else
			MainActivity.client.sendMessage(msg);
	}

	public static void reciveByteMessage(byte[] msg){
		//call other functions or s.t
		System.out.println("GDB!!!\n");
		try {
			WorkSpaceView.mWorkSpaceView.messageReceived(getObject(msg));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d("GDBR", new String(msg));
	}
	
	public static void SetServerIpAddress(String adr){
		ServerIP = adr;
	}
	public static String GetServerIpAddress(){
		return ServerIP;
	}
}
