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
		if (connected != null) {
			startServer();
		}
	}

	private void startServer() {
		try {
			server = new ServerSocket(7777);
			Socket client = server.accept();
			if (client != null) {
				onAcceptedClient(client);
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

	private void onAcceptedClient(Socket client) throws IOException {
		InputStreamReader inputStreamReader = new InputStreamReader(
				client.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line = "";
		StringBuilder builder = new StringBuilder();
		if ((line = bufferedReader.readLine()) != null) {
			System.err.println(line);
			builder.append(line);
		}
		System.out.println("1");
		OutputStream outputStream = client.getOutputStream();

		System.out.println("2");
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(outputStream),
				true);

		System.out.println("3");
		pw.println("OK");
		bufferedReader.close();
		System.out.println("4");
		pw.close();

		System.out.println("5");
		inputStreamReader.close();
		outputStream.close();
		server.close();
		Platform.runLater(() -> connected.onConnection(builder.toString()));

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
							&& inetAddress instanceof Inet4Address) {
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
