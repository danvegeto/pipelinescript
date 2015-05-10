package com.pipelinescript;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class FileManager 
{
	public static final String DATA_DIR = "data";
	
	public static String read(String file)
	{
		String path = DATA_DIR + "/" + file;
		
		List<String> lines = new LinkedList<String>();
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(path));
		
			String line = reader.readLine();
			
			while(line != null)
			{
				lines.add(line);
				line = reader.readLine();
			}
			
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return String.join("\n", lines);
	}
	
	public static void write(String file, String data)
	{
		String path = DATA_DIR + "/" + file;
		
		try
		{
			PrintWriter writer = new PrintWriter(new FileWriter(path));
			
			for(String line : data.split("\n"))
			{
				writer.println(line);
			}
			
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
