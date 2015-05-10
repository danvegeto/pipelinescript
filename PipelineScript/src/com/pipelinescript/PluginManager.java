package com.pipelinescript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PluginManager
{	
	public static String execute(Function function, String... args)
	{
		return execute(function.getExec(), args);
	}
	
	public static String execute(String exec, String... args)
	{
		if(exec.endsWith(".py"))
			exec = "python plugins/" + exec;
		
		List<String> rtn = new ArrayList<>();
		
		String arguments = "";
		for (String arg : args) 
		{
			if(arg.contains(Config.SPLIT_CHAR))
			{
				for(int i = 0; ; i++)
				{
					String path = arg.replace(Config.SPLIT_CHAR, i+"");
					
					if(!FileManager.exists(path))
						break;
					
					arguments += " data/" + path;
				}
			}
			else
			{
				arguments += " data/" + arg;
			}
		}
		try 
		{
			
			String cmd = exec+arguments;
			System.out.println(cmd);
			
			Process pr = Runtime.getRuntime().exec(cmd);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			
			String line = "";
			
			while((line = bfr.readLine()) != null) 
			{
				rtn.add(line);
			}          
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}			
	
		return String.join("\n", rtn);
	}
}
