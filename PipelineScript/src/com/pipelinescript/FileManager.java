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
	
	public static Table readTable(String file)
	{
		String path = DATA_DIR + "/" + file;
		
		return new Table(read(path));
	}
	
	public static void writeTable(String file, Table data)
	{
		String path = DATA_DIR + "/" + file;
		
		write(path, data.getRows());
	}
	
	private static String[] read(String path)
	{
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
		
		return lines.toArray(new String[]{});
	}
	
	private static void write(String path, String[] data)
	{
		try
		{
			PrintWriter writer = new PrintWriter(new FileWriter(path));
			
			for(String line : data)
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
	
	public static void main(String[] args)
	{
		write("test_1.txt", new String[]{"foo", "bar"});
	}
}
