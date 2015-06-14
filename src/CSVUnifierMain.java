import java.io.FileNotFoundException;
import java.io.IOException;

import unifiers.CSVUnifier;


public class CSVUnifierMain {

	public static void main(String[] args) throws IOException {
		
		String path = "./data";
		String [] leadingColumns= {"Country","League Type","Season"};
		String outputFile = "./data/agg_file.csv";
		
		@SuppressWarnings("unused")
		CSVUnifier csvu = new CSVUnifier(path, leadingColumns, outputFile);
		
		System.exit(0);

	}

}
