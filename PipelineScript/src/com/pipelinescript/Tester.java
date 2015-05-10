package com.pipelinescript;

public class Tester
{
	public static void main(String[] args) throws Exception
	{
		System.out.println("foo");
		
		Function f = new Function("copy.py");
		
		
		System.out.println(PluginManager.execute(f, "table.csv" ));
	}

}
