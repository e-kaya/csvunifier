package helpers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileHelper {

	public static void writeFile(String[] lines, File aFile) throws FileNotFoundException, IOException {
		if (aFile == null) {
			throw new IllegalArgumentException("File should not be null.");
		}
		/*if (!aFile.exists()) {
			throw new FileNotFoundException ("File does not exist: " + aFile);
		}*/
		/*if (!aFile.isFile()) {
			throw new IllegalArgumentException("Should not be a directory: " + aFile);
		}*/
		/*if (!aFile.canWrite()) {
			throw new IllegalArgumentException("File cannot be written: " + aFile);
		}*/

		BufferedWriter output = new BufferedWriter(new FileWriter(aFile, true));
		try {
			for(int count = 0; count < lines.length; count++) {
				output.write(lines[count]);
				if(count != lines.length - 1) {// This makes sure that an extra new line is not inserted at the end of the file
					output.newLine();
				}
			}
		}
		finally {
			output.close();
		}
	}

	public static void appendToFile(String[] lines, File aFile) throws FileNotFoundException, IOException {
		if (aFile == null) {
			throw new IllegalArgumentException("File should not be null.");
		}
		if (!aFile.exists()) {
			throw new FileNotFoundException ("File does not exist: " + aFile);
		}
		if (!aFile.isFile()) {
			throw new IllegalArgumentException("Should not be a directory: " + aFile);
		}
		if (!aFile.canWrite()) {
			throw new IllegalArgumentException("File cannot be written: " + aFile);
		}

		BufferedWriter output = new BufferedWriter(new FileWriter(aFile, true));
		try {
			for(int count = 0; count < lines.length; count++) {
				output.write(lines[count]);
				if(count != lines.length - 1) {// This makes sure that an extra new line is not inserted at the end of the file
					output.newLine();
				}
			}
		}
		finally {
			output.close();
		}
	}

	// http://stackoverflow.com/questions/12217087/add-a-specific-string-at-the-end-of-a-line-of-file-in-java
	public static int getNumberLines(File aFile) {
		int numLines = 0;
		try {

			BufferedReader input =  new BufferedReader(new FileReader(aFile));
			try {
				String line = null;

				while (( line = input.readLine()) != null){ //ReadLine returns the contents of the next line, and returns null at the end of the file.
					numLines++;
				}
			}
			finally {
				input.close();
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		return numLines;
	}
	
	public static String [] getAllLinesOfFile(File aFile) throws IOException {

		String [] lines = new String[getNumberLines(aFile)];
		BufferedReader input =  new BufferedReader(new FileReader(aFile));
		
		int i = 0;
		String line = null;
		while (( line = input.readLine()) != null){ //ReadLine returns the contents of the next line, and returns null at the end of the file.
			lines[i] = line;
			i++;
		}
		return lines;	
	}

	public static String readLine(int lineNumber, File aFile) {
		String lineText = "";
		try {

			BufferedReader input =  new BufferedReader(new FileReader(aFile));
			try {
				for(int count = 0; count < lineNumber; count++) {
					input.readLine();  //ReadLine returns the contents of the next line, and returns null at the end of the file.
				}
				lineText = input.readLine();
			}
			finally {
				input.close();
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		return lineText;
	}
	
	public static String readFirstLine(File aFile) {
		String lineText = "";
		try {
			BufferedReader input =  new BufferedReader(new FileReader(aFile));
			try {
				lineText = input.readLine();
			}
			finally {
				input.close();
			}
		}
		catch (IOException ex){
			ex.printStackTrace();
		}
		return lineText;
	}


	public static ArrayList<String> generateNameListOfAllFiles (String path) {

		final File folder = new File(path);
		return listNamesOfFilesForFolder(folder);

	}
	
	public static ArrayList<String> generateAbsolutePathsOfAllFiles (String path) {

		final File folder = new File(path);
		ArrayList<String> list = new ArrayList<String>();
		return listAbsolutePathsOfFilesForFolder(folder,list);

	}

	// Shamelessly borrowed from StackOverflow
	// http://stackoverflow.com/questions/1844688/read-all-files-in-a-folder
	// Credit goes to the user "rich"
	private static ArrayList<String> listNamesOfFilesForFolder(final File folder) {

		ArrayList<String> list = new ArrayList<String>();  

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listNamesOfFilesForFolder(fileEntry);
			} else {
				list.add(fileEntry.getName());
			}
		}

		return list;
	}
	
	private static ArrayList<String> listAbsolutePathsOfFilesForFolder(final File folder, ArrayList<String> list) {  

		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listAbsolutePathsOfFilesForFolder(fileEntry,list);
			} else {
				list.add(fileEntry.getAbsolutePath());
			}
		}
		//for (String str : list) {System.out.println(str);}
		return list;
	}
	
}
