package unifiers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import helpers.FileHelper;
import helpers.Logger;

public class CSVUnifier {
	
	private String pathToDataFiles;
	private String [] leadingColumns;
	private String [] mainColumnHeaders;
	private ArrayList<String> pathsToAllDataFiles;
	private String outputFileName;
	private int numberOfDataPoints;

	/**
	 * @param pathToDataFiles
	 * @throws IOException 
	 */
	public CSVUnifier(String pathToDataFiles, String [] leadingColumns, String outputFileName) throws IOException {
		
		this.pathToDataFiles = pathToDataFiles;
		this.leadingColumns = leadingColumns;
		this.outputFileName = outputFileName;
		
		this.pathsToAllDataFiles = FileHelper.generateAbsolutePathsOfAllFiles(this.pathToDataFiles);
		//for (String str : listOfAbsoluteFilePaths){System.out.println(str);}
		// OK System.out.println(Logger.toString(listOfAbsoluteFilePaths, Logger.VERTICAL));
		
		
		ColumnHeaderUnifier chu = new ColumnHeaderUnifier(this.pathsToAllDataFiles,leadingColumns);
		this.mainColumnHeaders = chu.getUnifiedColumnHeadersAsArray();
		this.numberOfDataPoints = chu.getTotalNumberOfDataRows();
		//for (String str : this.mainColumnHeaders){System.out.print(str + ",");}
		//System.out.println("Main Column Headers:");
		// OK System.out.println(Logger.toString(mainColumnHeaders, Logger.VERTICAL));
		/*System.out.print("Total Number of Data Points: ");
		System.out.println(Integer.toString(this.numberOfDataPoints));*/
		
		
		MergeJob [] jobs = generateMergeJobs();
		for (MergeJob j : jobs){
			System.out.println(j.convertToString() + "\n\n");
		}
		
				
		String [] aggregationLines = aggregateCSVFiles(jobs);
		try {
			writeAggregationLinesToFile(aggregationLines);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private String[] aggregateCSVFiles(MergeJob[] jobs) {

		String aggLines [] = new String[this.numberOfDataPoints + 1];
		// OK System.out.println("Size of aggLine (Aggregation CSV File): " + aggLines.length);

		aggLines[0] = this.mainColumnHeaders[0];
		for (int i = 1; i < this.mainColumnHeaders.length; i++){
			aggLines[0] = aggLines[0] + "," + this.mainColumnHeaders[i];
		}
		// OK System.out.println("First Line of aggLines:\n" + aggLines[0]);
		
		int i = 1;
		for (MergeJob job : jobs){
			
			// Get all lines of a file
			String [] lines = null;
			try { lines = FileHelper.getAllLinesOfFile(new File(job.getPathToFile()));
			} catch (IOException e) { e.printStackTrace(); }
			
			// Process each line of the file and insert them one by one to aggLines.
			for (int j = 1; j < lines.length; j++){// j=1 because no need to process the column names in a file
				aggLines[i] = processLineAccordingToAlignment(lines[j],job.getAlignment(),job.getLeadingValues());
				i++;
			}
		}
		
		return aggLines;
	}
	
	private String processLineAccordingToAlignment(String string,
			int[] alignment, String[] leadingValues) {
		
		String line = "";
		String [] stringArrayFromFile = string.split(",");
		
		if (alignment.length > stringArrayFromFile.length){
			String [] tmp = new String[alignment.length];
			for (int i = 0; i < tmp.length; i++){
				if (i < stringArrayFromFile.length){
					tmp[i] = stringArrayFromFile[i];
				} else {
					tmp[i] = "";
				}
			}
			stringArrayFromFile = tmp;
		}
		
		String [] lineArr = new String[this.mainColumnHeaders.length];
		
		for (int i = 0; i < lineArr.length; i++) {
			lineArr[i] = "";
		}
		
		// tile the leading values
		for (int i = 0; i < leadingValues.length; i++) {
			lineArr[i] = leadingValues[i];
		}
		
		// tile the values from the file (at the end, some fields may remain blank)
		for (int i = 0; i < alignment.length; i++){
			try{
				lineArr[alignment[i]] = stringArrayFromFile[i];
			} catch (IndexOutOfBoundsException e){
				e.printStackTrace();
			}
		}
		
		//finally convert lineArr array to a single string
		for (int i = 0; i < (lineArr.length - 1); i++){
			line = line + lineArr[i] + ",";
		}
		line = line + lineArr[lineArr.length - 1];
		
		return line;
	}

	private void writeAggregationLinesToFile(String[] aggregationLines) 
			throws FileNotFoundException, IOException {
		FileHelper.writeFile(aggregationLines, new File(this.outputFileName));
		return;
	}



	private MergeJob[] generateMergeJobs() {
		
		MergeJob [] jobs = new MergeJob[this.pathsToAllDataFiles.size()];
		int i = 0;
		
		for (String str : this.pathsToAllDataFiles){
			MergeJob job = new MergeJob();
			job.setPathToFile(str);
			job.generateAlignment(this.mainColumnHeaders);
			job.generateLeadingValues(this.leadingColumns);// this.leadingValueExtractionFormat (idea)
			jobs[i] = job;
			i++;
		}
		return jobs;
	}
	
}