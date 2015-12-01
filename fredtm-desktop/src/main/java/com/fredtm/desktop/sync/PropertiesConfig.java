package com.fredtm.desktop.sync;

import static com.fredtm.desktop.sync.PropertiesFolderUtil.getApiPropertiesFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PropertiesConfig {

	private StringProperty ip, port, serverIp, serverPort;
	private Properties properties, serverProperties;

	public PropertiesConfig() {
		PropertiesFolderUtil.createFredTmFolder();
		properties = new Properties();
		serverProperties = new Properties();

		verifySocketProp();
		verifyApiProp();

		File socketProperties = PropertiesFolderUtil.getSocketPropertiesFile();
		File apiProperties = PropertiesFolderUtil.getApiPropertiesFile();
		try {
			properties.load(new FileInputStream(socketProperties));
			serverProperties.load(new FileInputStream(apiProperties));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ip = new SimpleStringProperty();
		port = new SimpleStringProperty();
		ip.setValue(properties.getProperty("ip"));
		port.setValue(properties.getProperty("port"));


		serverIp = new SimpleStringProperty();
		serverPort = new SimpleStringProperty();
		serverIp.setValue(serverProperties.getProperty("s_ip"));
		serverPort.setValue(serverProperties.getProperty("s_port"));
	}

	private void verifySocketProp() {
		if (!PropertiesFolderUtil.socketPropertiesExists()) {
			properties.setProperty("port", "7777");
			properties.setProperty("ip", SyncServer.getIpAddress().get());
			File socketProperties = PropertiesFolderUtil.getSocketPropertiesFile();
			try {
				FileOutputStream fos = new FileOutputStream(socketProperties);
				properties.store(fos, "");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void verifyApiProp() {
		if (!PropertiesFolderUtil.apiPropertiesExists()) {
			serverProperties.setProperty("s_port", "9000");
			serverProperties.setProperty("s_ip", "192.168.1.105");
			File apiProperties = PropertiesFolderUtil.getApiPropertiesFile();
			try {
				FileOutputStream fos = new FileOutputStream(apiProperties);
				serverProperties.store(fos, "");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public StringProperty getServerIp() {
		return this.serverIp;
	}

	public StringProperty getServerPort() {
		return this.serverPort;
	}

	public StringProperty getIp() {
		return ip;
	}

	public StringProperty getPort() {
		return port;
	}

	public void setIp(String ip) {
		this.ip.setValue(ip);
	}

	public void setPort(String port) {
		this.port.setValue(port);
	}

	public boolean save() {
		try {
			FileOutputStream fos = new FileOutputStream(PropertiesFolderUtil.getSocketPropertiesFile());
			properties.setProperty("port", port.get());
			properties.setProperty("ip", ip.get());
			properties.store(fos, "");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean saveServer() {
		try {
			FileOutputStream fos = new FileOutputStream(getApiPropertiesFile());
			serverProperties.setProperty("s_port", serverPort.get());
			serverProperties.setProperty("s_ip", serverIp.get());
			serverProperties.store(fos, "");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}
}
