package com.fredtm.desktop.views;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class ReportWindow {

	private JasperPrint print;

	public ReportWindow(JasperPrint print) {
		this.print = print;
	}

	public void show() {
		JRViewer v = new JRViewer(print);

		JFrame jDialog = new JFrame();
		jDialog.setTitle("Visualização de relatório");
		jDialog.setSize(1024, 768);
		jDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jDialog.setLayout(new BorderLayout(10, 10));
		jDialog.add(v, BorderLayout.CENTER);
		jDialog.setVisible(true);		
		jDialog.setLocationRelativeTo(null);
	}

	
}
