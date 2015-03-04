package com.pipelinescript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PipelineScript
{
	public static void main(String[] args)
	{
		Interpreter interpreter = new Interpreter();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		String line = getInput(reader);
		
		while(line != null)
		{
			interpreter.execute(line);
			line = getInput(reader);
		}
	}
	
	private static String getInput(BufferedReader reader)
	{
		System.out.print("> ");
		
		try
		{
			return reader.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
