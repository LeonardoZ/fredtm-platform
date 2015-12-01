package com.fredtm.desktop.sync;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFolderUtil {

	public final static String USER_HOME = System.getProperty("user.home");
	public final static String PROPERTIES_FOLDER = USER_HOME + "/fredtm-config";
	public static final String SOCKET_PROPERTIES = "/socket.properties";
	public static final String API_PROPERTIES = "/api.properties";

	public static boolean socketPropertiesExists() {
		return new File(PROPERTIES_FOLDER + "/" + SOCKET_PROPERTIES).exists();
	}

	public static boolean apiPropertiesExists() {
		return new File(PROPERTIES_FOLDER + "/" + API_PROPERTIES).exists();
	}

	public static File createFredTmFolder() {
		File file = new File(PROPERTIES_FOLDER);
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

	public static File getSocketPropertiesFile() {
		return getOrCreateFile(PROPERTIES_FOLDER + "/" + SOCKET_PROPERTIES);
	}

	public static File getApiPropertiesFile() {
		return getOrCreateFile(PROPERTIES_FOLDER + "/" + API_PROPERTIES);
	}

	public static Properties getApiProperties() {
		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream(getApiPropertiesFile()));
			return properties;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static File getOrCreateFile(String path) {
		File file = new File(path);
		if (!file.exists() && file.canWrite()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

}
