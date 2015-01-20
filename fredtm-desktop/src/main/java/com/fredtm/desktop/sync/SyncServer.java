package com.fredtm.desktop.sync;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Optional;

import javafx.application.Platform;

public class SyncServer {

	private ClientConnected connected;
	private ServerSocket server;

	public SyncServer(ClientConnected connected) {
		this.connected = connected;
		String porta = new SocketConfig().getPort().getValue();
		if (connected != null) {
			startServer(Integer.valueOf(porta));
		}
	}

	private void startServer(int porta) {
		try {
			server = new ServerSocket(porta);
			Socket client = server.accept();
			if (client != null) {
				onAcceptClient(client);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void onAcceptClient(Socket client) {
		BufferedReader bufferedReader = null;
		OutputStream outputStream = null;
		PrintWriter pw = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			String line = "";
			StringBuilder builder = new StringBuilder();
			if ((line = bufferedReader.readLine()) != null) {
				builder.append(line);
			}
			outputStream = client.getOutputStream();
			pw = new PrintWriter(new OutputStreamWriter(outputStream), true);
			pw.println("OK");
			Platform.runLater(() -> connected.onConnection(builder.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
				pw.close();
				outputStream.close();
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @return Thanks to "Ayush" on StackOverflow
	 */
	public static Optional<String> getIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& (inetAddress instanceof Inet4Address)) {
						String ipAddress = inetAddress.getHostAddress()
								.toString();
						return Optional.of(ipAddress);
					}
				}
				
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return Optional.empty();
	}

}
