package com.example.whatdosetheboardsay;

public class ExitMsg implements IMessage{
	private int client;
	public ExitMsg(int c) {
		client = c;
	}
	
	public int getClient() {
		return client;
	}
}
