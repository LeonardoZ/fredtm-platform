package com.fredtm.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Location;
import com.fredtm.core.model.TimeActivity;

import au.com.bytecode.opencsv.CSVWriter;

public class CollectToCSV implements Exportable<Collect> {

	private static final String SUFIX = ".csv";
	private String destinationDirectory;

	public CollectToCSV() {

	}

	public CollectToCSV(String directoryDestination) {
		super();
		this.destinationDirectory = directoryDestination;
	}

	public void setdirectoryDestination(String directoryDestination) {
		this.destinationDirectory = directoryDestination;
	}

	public void export(Collect collect, String path) throws ExportationErrorExcetion {
		destinationDirectory = path;
		List<TimeActivity> tempos = collect.getTimeInChronologicalOrder();
		if (tempos.isEmpty())
			return;
		String filename = fileNameGenenerator(
				collect.getOperation().getCompany() + "_" + collect.getOperation().getName());

		save(collect, filename);
	}

	private void save(Collect collect, String filename) {
		try {
			BufferedWriter bw = Files.newBufferedWriter(Paths.get(filename), Charset.forName("UTF-8"));
			CSVWriter w;
			// FileOutputStream fos = new FileOutputStream(filename);
			// // Excel entende UTF-8 dessa maneira /=
			// fos.write(0xef);
			// fos.write(0xbb);
			// fos.write(0xbf);
			// BufferedWriter bw = new BufferedWriter(new
			// OutputStreamWriter(fos,
			// "UTF8"));
			w = new CSVWriter(bw, ',');
			List<String[]> vals = generateLines(collect);

			w.writeAll(vals);

			w.flush();
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExportationErrorExcetion();
		}
	}

	public void export(List<Collect> collects, String path) throws ExportationErrorExcetion {
		destinationDirectory = path;
		int i = 1;
		for (Collect c : collects) {
			if (!c.getCollectedTimes().isEmpty()) {
				String filename = fileNameGenerationForAll(
						c.getOperation().getCompany() + "_" + c.getOperation().getName(), String.valueOf(i++));
				save(c, filename);
			}
		}
	}

	private List<String[]> generateLines(Collect collect) {
		List<TimeActivity> tempos = collect.getTimeInChronologicalOrder();
		List<String[]> vals = new ArrayList<String[]>();
		String[] headers = { "Data", "Hora", "Atividade parcial", "Description", "Classificacao",
				collect.getQuantitativeActivity() == null ? "N/A" : collect.getQuantitativeActivity().getItemName(),
				"Duracao(s)", "Tempo acumulado(s)", "Latitude", "Longitude" };
		vals.add(headers);

		long sum = 0l;

		for (TimeActivity timeActivity : tempos) {
			long measuredTime = timeActivity.getEllapsedTimeInSeconds();
			sum += measuredTime;
			String[] splitedStartDate = timeActivity.getSplitedStartDate();
			String[] line = { splitedStartDate[0], splitedStartDate[1], timeActivity.getActivity().getTitle(),
					timeActivity.getActivity().getDescription(), timeActivity.getActivity().getActivityType().name(),
					timeActivity.getActivity().isQuantitative() ? timeActivity.getCollectedAmount().toString() : "n/a",
					Long.toString(measuredTime), Long.toString(sum),
					timeActivity.getLocation().orElseGet(() -> new Location(0, 0)).getLatitude(),
					timeActivity.getLocation().orElseGet(() -> new Location(0, 0)).getLongitude() };
			vals.add(line);
		}
		return vals;
	}

	private String fileNameGenenerator(String content) {

		String path = destinationDirectory + "/fred_tm";
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
		}
		Date dt = GregorianCalendar.getInstance().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
		return path + "/" + content + "_" + sdf.format(dt) + SUFIX;
	}

	private String fileNameGenerationForAll(String conteudo, String fileValue) {
		String path = destinationDirectory + "/fred_tm";
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
		}
		Date dt = GregorianCalendar.getInstance().getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");

		String subFolder = path + "/" + conteudo + "_" + dateFormat.format(dt);
		File sub = new File(subFolder);
		if (!sub.exists()) {
			sub.mkdir();
		}
		return sub + "/" + fileValue + SUFIX;
	}

}
