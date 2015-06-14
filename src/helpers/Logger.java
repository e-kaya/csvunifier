package helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Logger {
	
	public static final int DEBUG = 0;
	public static final int INFO = 1;
	public static final int WARNING = 2;
	public static final int ERROR = 3;
	public static final int VERTICAL = 10;
	public static final int HORIZONTAL = 11;
	public File logfile = new File("./log.txt");
	
	public void log(ArrayList<String> list, int type) throws IOException{
		
		BufferedWriter bwLog = new BufferedWriter(new FileWriter(logfile, true));
		try {
			for(String str : list) {
				bwLog.write(str);
				bwLog.close();
			}
		} finally {
			
		}
		
		return;
	}
	
	public static String toString(ArrayList<String> list, int type){
		String str = "";
		if (type == Logger.VERTICAL){
			for (String s : list){
				str = str + s + "\n";
			}
		} else if (type == Logger.HORIZONTAL){
			for (String s : list){
				str = str + s + ",";
			}
		}
		return str;
	}
	
	public static String toString(String [] array, int type){
		String str = "";
		if (type == Logger.VERTICAL){
			for (int i = 0; i < array.length; i++){
				str = str + array[i] + "\n";
			}
		} else if (type == Logger.HORIZONTAL){
			for (int i = 0; i < array.length; i++){
				str = str + array[i] + ",";
			}
		}
		
		return str;
	}
	
	public static String toString(int [] array, int type){
		String str = "";
		if (type == Logger.VERTICAL){
			for (int i = 0; i < array.length; i++){
				str = str + Integer.toString(array[i]) + "\n";
			}
		} else if (type == Logger.HORIZONTAL){
			for (int i = 0; i < array.length; i++){
				str = str + Integer.toString(array[i]) + ",";
			}
		}
		
		return str;
	}

}
