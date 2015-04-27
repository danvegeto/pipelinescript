package com.pipelinescript;

public class FileManager 
{
	public static Table readTable(String file)
	{
		return new Table(read(file));
	}
	
	public static void writeTable(String file, Table data)
	{
		write(file, data.getRows());
	}
	
	private static String[] read(String file)
	{
		//TODO
		return null;
	}
	
	private static void write(String file, String[] data)
	{
		//TODO
	}
}
