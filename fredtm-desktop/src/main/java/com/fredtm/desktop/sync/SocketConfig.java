package com.fredtm.desktop.sync;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SocketConfig {

	private StringProperty ip;
	private StringProperty port;
	private Properties properties;

	public SocketConfig() {
		properties = new Properties();
		InputStream stream = getPropertiesStream();
		try {
			properties.load(stream);
			ip = new SimpleStringProperty();
			ip.setValue(SyncServer.getIpAddress().get());
			port = new SimpleStringProperty();
			port.setValue((String) properties.getProperty("port"));

		} catch (IOException e) {
			e.printStackTrace();
		}
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

	public boolean salvar() {
		File propertiesFile = getPropertiesFile();
		try {
			FileWriter osw = new FileWriter(propertiesFile);
			properties.setProperty("port", port.get());
			properties.store(osw, "");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	private File getPropertiesFile() {
		URL resource = getClass().getClassLoader().getResource(
				"config.properties");
		return new File(resource.getFile());
	}

	private InputStream getPropertiesStream() {
		return getClass().getClassLoader().getResourceAsStream(
				"config.properties");
	}

}
