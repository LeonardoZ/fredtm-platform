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

import com.fredtm.core.model.Coleta;
import com.fredtm.core.model.TempoAtividade;

public class ColetaToCSV implements Exportavel<Coleta> {

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

	public void exportar(Coleta coleta, String caminho)
			throws ErroDeExportacaoExcetion {
		diretorioDestino = caminho;
		List<TempoAtividade> tempos = coleta.getTemposEmOrdemCronologica();
		if (tempos.isEmpty())
			return;
		String filename = fileNameGen(coleta.getOperacao().getEmpresa() + "_"
				+ coleta.getOperacao().getNome());

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

	public void exportar(List<Coleta> coletas, String caminho)
			throws ErroDeExportacaoExcetion {
		diretorioDestino = caminho;
		int i = 1;
		for (Coleta c : coletas) {
			if (!c.getTempos().isEmpty()) {
				String filename = fileNameGenParaTodos(c.getOperacao()
						.getEmpresa() + "_" + c.getOperacao().getNome(),
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

	private List<String[]> gerarLinhas(Coleta coleta) {
		List<TempoAtividade> tempos = coleta.getTemposEmOrdemCronologica();
		List<String[]> vals = new ArrayList<String[]>();
		String[] headers = {
				"Data",
				"Hora",
				"Atividade parcial",
				"Descricao",
				"Classificacao",
				coleta.getAtividadeQuantitativa() == null ? "N/A" : coleta
						.getAtividadeQuantitativa().getNomeItem(),
				"Duracao(s)", "Tempo acumulado(s)" };
		vals.add(headers);

		long sum = 0l;

		for (TempoAtividade tempo : tempos) {
			long tempoCronometrado = tempo.getTempoCronometradoEmSegundos();
			sum += tempoCronometrado;
			String[] dataInicioSeparada = tempo.getDataInicioSeparada();
			String[] line = {
					dataInicioSeparada[0],
					dataInicioSeparada[1],
					tempo.getAtividade().getTitulo(),
					tempo.getAtividade().getDescricao(),
					tempo.getAtividade().getTipoAtividade().name(),
					tempo.getAtividade().getEhQuantitativa() ? tempo
							.getQuantidadeColetada().toString() : "n/a",
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
