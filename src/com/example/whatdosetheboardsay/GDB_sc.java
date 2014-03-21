package com.example.whatdosetheboardsay;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class GDB_sc {
	// http://silverballsoftware.com/get-your-ip-address-android-code
	private static String ServerIP = "127.0.0.1";
	static DatagramSocket socket;
	private static boolean isServer;
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
	public static void sendByteMessage(byte[] msg){
		if(isServer)
			MainActivity.server.sendMessage(msg);
		else
			MainActivity.client.sendMessage(msg);
	}

	public static void reciveByteMessage(byte[] msg){
		//call other functions or s.t
	}
	
	public static void SetServerIpAddress(String adr){
		ServerIP = adr;
	}
	public static String GetServerIpAddress(){
		return ServerIP;
	}
}
