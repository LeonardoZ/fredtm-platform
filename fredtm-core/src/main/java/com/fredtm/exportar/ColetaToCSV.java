package com.fredtm.exportar;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

import com.fredtm.core.model.Collect;
import com.fredtm.core.model.TimeActivity;

public class ColetaToCSV implements Exportavel<Collect> {

	private static final String SUFIX = ".csv";
	private String diretorioDestino;

	public ColetaToCSV() {

	}

	public ColetaToCSV(String diretorioDestino) {
		super();
		this.diretorioDestino = diretorioDestino;
	}

	public void setDiretorioDestino(String diretorioDestino) {
		this.diretorioDestino = diretorioDestino;
	}

	public void exportar(Collect coleta, String caminho)
			throws ErroDeExportacaoExcetion {
		diretorioDestino = caminho;
		List<TimeActivity> tempos = coleta.getTimeInChronologicalOrder();
		if (tempos.isEmpty())
			return;
		String filename = fileNameGen(coleta.getOperation().getCompany() + "_"
				+ coleta.getOperation().getName());

		try {
			CSVWriter w;
			FileOutputStream fos = new FileOutputStream(filename);
			// Excel entende UTF-8 dessa maneira /=
			fos.write(0xef);
			fos.write(0xbb);
			fos.write(0xbf);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos,
					"UTF8"));
			w = new CSVWriter(bw, ',');
			List<String[]> vals = gerarLinhas(coleta);

			w.writeAll(vals);
			w.flush();
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ErroDeExportacaoExcetion();
		}
	}

	public void exportar(List<Collect> coletas, String caminho)
			throws ErroDeExportacaoExcetion {
		diretorioDestino = caminho;
		int i = 1;
		for (Collect c : coletas) {
			if (!c.getTimes().isEmpty()) {
				String filename = fileNameGenParaTodos(c.getOperation()
						.getCompany() + "_" + c.getOperation().getName(),
						String.valueOf(i++));
				try {
					CSVWriter w;
					FileOutputStream fos = new FileOutputStream(filename);
					// Excel entende UTF-8 dessa maneira /=
					fos.write(0xef);
					fos.write(0xbb);
					fos.write(0xbf);
					BufferedWriter bw = new BufferedWriter(
							new OutputStreamWriter(fos, "UTF8"));
					w = new CSVWriter(bw, ',');
					List<String[]> vals = gerarLinhas(c);

					w.writeAll(vals);
					w.flush();
					w.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new ErroDeExportacaoExcetion();
				}
			}
		}
	}

	private List<String[]> gerarLinhas(Collect coleta) {
		List<TimeActivity> tempos = coleta.getTimeInChronologicalOrder();
		List<String[]> vals = new ArrayList<String[]>();
		String[] headers = {
				"Data",
				"Hora",
				"Activity parcial",
				"Descricao",
				"Classificacao",
				coleta.getQuantitativeActivity() == null ? "N/A" : coleta
						.getQuantitativeActivity().getItemName(),
				"Duracao(s)", "Tempo acumulado(s)" };
		vals.add(headers);

		long sum = 0l;

		for (TimeActivity tempo : tempos) {
			long tempoCronometrado = tempo.getEllapsedTimeInSeconds();
			sum += tempoCronometrado;
			String[] dataInicioSeparada = tempo.getSplitedStartDate();
			String[] line = {
					dataInicioSeparada[0],
					dataInicioSeparada[1],
					tempo.getActivity().getTitle(),
					tempo.getActivity().getDescription(),
					tempo.getActivity().getActivityType().name(),
					tempo.getActivity().isQuantitative() ? tempo
							.getCollectedAmount().toString() : "n/a",
					Long.toString(tempoCronometrado), Long.toString(sum) };
			vals.add(line);
		}
		return vals;
	}

	private String fileNameGen(String conteudo) {

		String path = diretorioDestino + "/fred_tm";
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
		}
		Date dt = GregorianCalendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		return path + "/" + conteudo + "_" + sdf.format(dt) + SUFIX;
	}

	private String fileNameGenParaTodos(String conteudo, String valorDoArquivo) {
		String path = diretorioDestino + "/fred_tm";
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
		}
		Date dt = GregorianCalendar.getInstance().getTime();
		SimpleDateFormat sdfPasta = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");

		String subFolder = path + "/" + conteudo + "_" + sdfPasta.format(dt);
		File sub = new File(subFolder);
		if (!sub.exists()) {
			sub.mkdir();
		}
		return sub + "/" + valorDoArquivo + SUFIX;
	}

}
