package unifiers;

import helpers.FileHelper;

import java.io.File;
import java.util.ArrayList;

public class ColumnHeaderUnifier {

	private ArrayList<String> pathsToDataFiles;
	private ArrayList<String> unifiedColumn;
	private String [] leadingColumns;
	private int totalNumberOfDataRows;
	/**
	 * @param pathsToDataFiles a list of paths to files whose header lines will be merged
	 * @param leadingColumns list of column names that are not provided in the csv files
	 */
	public ColumnHeaderUnifier(ArrayList<String> pathsToDataFiles,
			String[] leadingColumns) {
		super();
		this.pathsToDataFiles = pathsToDataFiles;
		this.leadingColumns = leadingColumns;
		this.unifiedColumn = new ArrayList<String>();
		this.totalNumberOfDataRows = 0;
		walkThroughFilesAndUpdate();
//		for (String str : this.unifiedColumn){ System.out.print(str + ",");} System.out.print("\n");
//		for (String str : this.pathsToDataFiles){ System.out.println("\n" + str);}
	}
	
	private void walkThroughFilesAndUpdate(){
		
		for (String str : this.leadingColumns){
			this.unifiedColumn.add(str);
		}
		
		for (String path : pathsToDataFiles) {
			File file = new File(path);
			String [] columns = null;
			
			String tmpStr = FileHelper.readFirstLine(file);
			if (!tmpStr.isEmpty()){
				columns = tmpStr.split(",");
				setTotalNumberOfDataRows(getTotalNumberOfDataRows() + (FileHelper.getNumberLines(file) - 1));
				
				if (unifiedColumn.isEmpty()){
					for (String str : columns){	unifiedColumn.add(str);	}
				} else {
					for (String str : columns){
						if (!unifiedColumn.contains(str)){
							unifiedColumn.add(str);							
						}
					}
				}
			} 
		}
	}
	
	public String [] getUnifiedColumnHeadersAsArray(){
		
		String [] unifiedColumnsArray = new String [this.unifiedColumn.size()];
		int i = 0;
		for (String str : this.unifiedColumn) {
			unifiedColumnsArray[i] = str;
			i++;
		}
		
		return unifiedColumnsArray;
	}

	public ArrayList<String> getPathsToDataFiles() {
		return pathsToDataFiles;
	}
	private void setTotalNumberOfDataRows(int totalNumberOfDataRows) {
		this.totalNumberOfDataRows = totalNumberOfDataRows;
	}
	public int getTotalNumberOfDataRows() {
		return totalNumberOfDataRows;
	}
	
}
