package org.frogforce503.fllscoring;

import java.io.*;
import java.net.*;

public class Server {
	private ServerSocket ssock;

	public Server() throws IOException {
		ssock = new ServerSocket(41361);
	}

	public static void main(String[] args) {
		try {
			new Server();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
