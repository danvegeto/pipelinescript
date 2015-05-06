package com.pipelinescript;

public class Function
{
	private String file;
	
	public Function(String file)
	{
		this.file = file;
	}
	
	public Table execute(String... args)
	{
		return PluginManager.execute(file, args);
	}
}