package com.pipelinescript;

public class ProcessManager
{
	public static void executeParallel(Function exec, String inPattern, String outPattern)
	{
		executeParallel(exec.getExec(), inPattern, outPattern);
	}
	
	public static void executeParallel(String exec, String inPattern, String outPattern)
	{
		for(int i = 0; ; i++)
		{
			String inFile = inPattern.replace(Config.SPLIT_CHAR, i+"");
			String outFile = outPattern.replace(Config.SPLIT_CHAR, i+"");
			
			if(!FileManager.exists(inFile))
				break;
			
			String output = PluginManager.execute(exec, inFile);
			FileManager.write(outFile, output);
		}
	}
}
