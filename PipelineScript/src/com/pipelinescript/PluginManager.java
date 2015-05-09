package com.pipelinescript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PluginManager
{	
	// FOR TEST PURPOSES LEFT 
	/*
	public static void main(String[] args){
		String pt = "./test/PluginManagerTest.py";
		String[]  arg = new String[]{"1","2","3"};
		Table tb = execute(pt, arg);
	}
	*/
	
	
	/*
	 * Example Usage
	 * String pythonSource = "./test/PluginManagerTest.py";
	 * Arguments
		 String[]  arg = new String[]{"1","2","3"};
		
		 Table tableb = execute(pythonSource, arg);
	 * 
	 * 
	 * */
	public static Table execute(String file, String... args)
	{
		//TODO
		List<String> rtn = new ArrayList<>();
		String ext = file.substring(file.lastIndexOf('.') + 1);
		String [] lines = null;
		if (ext.equals("py")){
			String arguments = "";
			for (String arg : args) {
				arguments +=" "+ arg;
			}
			try {
				Process pr = Runtime.getRuntime().exec("python "+file+arguments);
				BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				String line = "";
				while((line = bfr.readLine()) != null) {
				 //System.out.println(line);
				 rtn.add(line);
				}          
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			lines = new String[rtn.size()];
			
			for (int i = 0 ; i< rtn.size() ; i++) {
				lines[i] = rtn.get(i);
			}
			
		}
		return new Table(lines);
	}
}
