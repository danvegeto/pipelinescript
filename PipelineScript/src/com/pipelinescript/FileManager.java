package com.pipelinescript;

import java.util.HashMap;
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

}
