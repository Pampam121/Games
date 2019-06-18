package WarOfOmens;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class Logger {
	String fileLocation = "E:\\Anett\\Desktop\\";
	private String logFile;

	public Logger(String fileName) {
		logFile = fileName;
	}

	public List<String[]> readFileWithoutHeader() throws IOException {
		String file = fileLocation + logFile;

		// Create an object of file reader class with CSV file as a parameter.
		FileReader filereader = new FileReader(file);

		// create csvReader object and skip first Line
		CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
		List<String[]> allData = csvReader.readAll();

		return allData;
	}

	public String getFileLocationAndName() {
		return fileLocation + logFile;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

}
