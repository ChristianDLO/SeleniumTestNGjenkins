package utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataParameterize {
	private FileReader dataSource;
	private BufferedReader dataIn;
	private String td1;
	private String td2;
	private String td3;

	public DataParameterize (String file)  {

		try {
			dataSource = new FileReader(file);
			dataIn = new BufferedReader(dataSource);
		} catch (IOException e) {
			System.out.println("Could not find the data file!");			
		}
	}

	public boolean nextRecord() {
		String nextLine;
		

		try {
			nextLine = dataIn.readLine();
			if (nextLine != null) {
				String[] count = nextLine.split(":");
				if(!(count[0].isEmpty()))
					td1 = count[0].toString();
				if(!(count[1].isEmpty()))
					td2 = count[1].toString();
				if(!(count[2].isEmpty()))
					td3 = count[2].toString();
				return true;
			} else {
				td1 = null;
				td2 = null;
				td3 = null;
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public String getTd1() {
		return td1;
	}

	public String getTd2() {
		return td2;
	}

	public String getTd3() {
		return td3;
	}

}
