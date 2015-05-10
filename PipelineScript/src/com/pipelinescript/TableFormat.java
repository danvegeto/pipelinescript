package com.pipelinescript;

public abstract class TableFormat
{
	public abstract String encode(String[][] data);
	public abstract String[][] decode(String str);
}
