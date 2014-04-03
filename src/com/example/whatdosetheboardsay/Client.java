	package com.example.whatdosetheboardsay;
	
	import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
	
import android.util.Log;
	 
	public class Client implements Runnable {
			private String ClientIP = "127.0.0.1";
			private String ServerIP = "127.0.0.1";
			private byte[] buf;
			public static String str = "";
			public static Clientrvmsg Clientrv;
			public void sendMessage(byte[] toall){
				String prepend = JoinInterface_activity.uid + "|";
				buf = new byte[prepend.length()+toall.length+1];
				ByteBuffer target = ByteBuffer.wrap(buf);
				target.put(prepend.getBytes());
				target.put(toall);
				synchronized (this) {
					this.notify();
		    	}
			}
		    @Override
	        public void run() {
		    	GDB_sc.setClient();
	        	ClientIP = GDB_sc.GetLocalIpAddress();
	        	ServerIP = GDB_sc.GetServerIpAddress();
	                try {
	                		InetAddress serverAddr = InetAddress.getByName(ServerIP);
	                		InetAddress clientAddr = InetAddress.getByName(ClientIP);
	                    	Log.d("UDP", "C: Connecting... to " + ServerIP);
	                    	Clientrv = new Clientrvmsg() ;
	                		new Thread(Clientrv).start();
	                		while(true){
	                    		synchronized (this) {
	                    			this.wait();
	                    		}
	                    	//	str = JoinInterface_activity.uid + "|" + str;
	                    		Log.d("UDP", "C: CheckPoint");
	                    	//	byte[] buf = str.getBytes();
	                        	DatagramPacket packet = new DatagramPacket(ByteBuffer.allocate(4).putInt(buf.length).array(),4, serverAddr, 2333);
	                        	Log.d("UDP", "C: Sending: size '" + String.valueOf(buf.length) + "'");
	                        	GDB_sc.socket.send(packet);
	                        	packet = new DatagramPacket(buf, buf.length, serverAddr, 2333);
	                        	Log.d("UDP", "C: Sending: data '" + new String(buf) + "'");
	                        	GDB_sc.socket.send(packet);
	                        	Log.d("UDP", "C: Done.");
	                    	}
	                } catch (Exception e) {
	                        Log.e("UDP", "C: Error", e);
	                }
	        }
	}
	class Clientrvmsg implements Runnable{
		private String ClientIP = "127.0.0.1";
		private String ServerIP = "127.0.0.1";
		public static String str = "";
	    public void run() {
	    	ClientIP = GDB_sc.GetLocalIpAddress();
        	ServerIP = GDB_sc.GetServerIpAddress();
            byte[] bufsize = new byte[4];
                try {
                    while(true){
                    	DatagramPacket packet = new DatagramPacket(bufsize, 4);
                    	Log.d("UDP", "C: Receiving datasize...");
                    	GDB_sc.socket.receive(packet);
                    	Log.d("UDP", "C: Received 1 :" + bufsize[0]);
                    	Log.d("UDP", "C: Received 2 :" + bufsize[1]);
                    	Log.d("UDP", "C: Received 3 :" + bufsize[2]);
                    	Log.d("UDP", "C: Received 4 :" + bufsize[3]);
                    	Log.d("UDP", "C: Received :" + ByteBuffer.wrap(bufsize).getInt());
                    	if(ByteBuffer.wrap(bufsize).getInt()>0x1000000)
                            continue;
                    	byte[] buf = new byte[ByteBuffer.wrap(bufsize).getInt()];
                    	//delete packet;
                    	packet = new DatagramPacket(buf, buf.length);
                    	Log.d("UDP", "C: Receiving data...");
                    	GDB_sc.socket.receive(packet);
                    	//Log.d("UDP", "C: Received: '" + new String(packet.getData()) + "'");
                    	GDB_sc.reciveByteMessage(packet.getData());
                    //	String attempt = new String(packet.getData());
                    	Log.d("UDP", "C: Done. Testing the Attempt");
                    }
	            } catch (Exception e) {
	                    Log.e("UDP", "C: Error", e);
	            }
	    }
	}