package com.fredtm.desktop.controller;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.fredtm.desktop.views.ReportWindow;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ReportController {

	private final static String BASE_PATH = "src/main/resources/reports/";
	private String IMAGE_PATH = "src/main/resources/images/ic_title.png";

	private JRBeanCollectionDataSource reportDataSource;
	private Map<String, Object> params;
	private String fileName;

	public ReportController() {
		params = new HashMap<>();
		File file = new File("src/main/resources/images/ic_title.png");
	}

	public void buildAndShow() {
		params.put("logo", getLogo());
		JasperPrint print = null;
		try {
			print = JasperFillManager.fillReport(BASE_PATH + fileName, params, reportDataSource);
		} catch (JRException e) {
			e.printStackTrace();
		}
		ReportWindow window = new ReportWindow(print);
		window.show();
	}

	public ReportController loadReport(String fileName) {

		this.fileName = fileName;
		return this;
	}

	public ReportController fillParam(String key, Object value) {
		this.params.put(key, value);
		return this;
	}

	public ReportController fillDataSource(Collection<?> elements) {
		this.reportDataSource = new JRBeanCollectionDataSource(elements);
		return this;
	}

	private InputStream getLogo() {
		return ReportController.class.getResourceAsStream("/images/ic_title.png");
	}

}
