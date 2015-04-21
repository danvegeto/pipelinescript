package pipelinescript.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManager {
	Map<String, String> fileMap;
	public FileManager(){
		this.fileMap = new HashMap<>();
	}
	public void create(String variable, String filepath){
		fileMap.put(variable,filepath);
	}
	public void delete(String variable){
		fileMap.remove(variable);
	}
	public void move(String variable, String filepath){
		fileMap.put(variable, filepath);
	}
	public void set(String variable, Object value){
		
	}
	
	public Object get(String variable){
		
		return new Object();
	}
	// make sure it points proper CSV file
	public Graph getGraphFromCSV(String variable){
		String path = fileMap.get(variable);
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "\t";
		List<String[]> rtn = null;
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
		rtn = new ArrayList<>();
			while ((line = br.readLine()) != null) {
				String[] l = line.split(cvsSplitBy);
				rtn.add(l);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new Graph(rtn);
	}

}
