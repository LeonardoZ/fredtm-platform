package com.fredtm.export;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public interface Exportable<T> {

	void export(T t, String path) throws ExportationErrorExcetion;

	void export(List<T> t, String path) throws ExportationErrorExcetion;

	default void saveInFile(String pathParam, String json) {

		Path path = Paths.get(pathParam + "/operations.json");
		try {
			Files.createFile(path);
		} catch (FileAlreadyExistsException e1) {
			e1.printStackTrace();
			try {
				Files.deleteIfExists(path);
				Files.createFile(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		FileWriter fw;
		try {
			fw = new FileWriter(path.toFile());
			fw.append(json);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
