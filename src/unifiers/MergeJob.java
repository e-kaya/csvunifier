package unifiers;

import java.io.File;

import helpers.FileHelper;
import helpers.Logger;

public class MergeJob {
	
	private String pathToFile;
	private int [] alignment;
	private String [] leadingValues;
	
	public void generateAlignment(String[] mainColumnHeaders) {
		
		String [] columns = (FileHelper.readFirstLine(new File(this.pathToFile))).split(",");
		this.alignment = new int[columns.length];
		for (int i = 0; i < columns.length; i++){
			for (int j = 0; j < mainColumnHeaders.length; j++) {
				if (columns[i].equals(mainColumnHeaders[j])){
					this.alignment[i] = j;
				}
			}
		}
		return;
	}
	
	public void generateLeadingValues(String[] leadingColumns) {
		this.leadingValues = new String[leadingColumns.length];
		String [] tmp = (this.pathToFile).split("/");
		tmp = tmp[tmp.length-1].split("_"); // [en]_[0]_[2013]_[2014.csv] split(".") is not working, weird.
		this.leadingValues[0] = tmp[0];
		this.leadingValues[1] = tmp[1];
		this.leadingValues[2] = tmp[2] + "-" + tmp[3];
		
		return;
	}
	
	public String convertToString(){
		String str = "Path to File: " + this.pathToFile + "\n";
		str = str + "Alignment:\n" + Logger.toString(this.alignment, Logger.HORIZONTAL) + "\n";
		str = str + "Leading Values:\n" + Logger.toString(this.leadingValues, Logger.VERTICAL) + "\n";
		
		return str;
	}
	
	public String getPathToFile() {
		return pathToFile;
	}
	public void setPathToFile(String pathToFile) {
		this.pathToFile = pathToFile;
	}
	public int[] getAlignment() {
		return alignment;
	}
	public void setAlignment(int[] alignment) {
		this.alignment = alignment;
	}
	public String[] getLeadingValues() {
		return leadingValues;
	}
	public void setLeadingValues(String[] leadingValues) {
		this.leadingValues = leadingValues;
	}
	
}
