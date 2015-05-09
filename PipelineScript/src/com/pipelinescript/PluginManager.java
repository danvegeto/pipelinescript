package com.pipelinescript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PluginManager
{	
	public static String execute(String file, String... args)
	{
		List<String> rtn = new ArrayList<>();
		String ext = file.substring(file.lastIndexOf('.') + 1);
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
				 rtn.add(line);
				}          
				
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		return String.join("\n", rtn);
	}
}
