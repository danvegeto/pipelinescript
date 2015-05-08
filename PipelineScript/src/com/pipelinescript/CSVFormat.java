package com.pipelinescript;

import java.util.LinkedList;
import java.util.List;

public class CSVFormat extends TableFormat
{
	private String sep;
	
	public CSVFormat()
	{
		this(",");
	}
	
	public CSVFormat(String sep)
	{
		this.sep = sep;
	}
	
	@Override
	public String[] encode(String[][] data)
	{
		String[] lines = new String[data.length];
		
		for(int i = 0; i < lines.length; i++)
			lines[i] = String.join(sep, data[i]);
		
		return lines;
	}

	@Override
	public String[][] decode(String[] lines)
	{
		List<String[]> list = new LinkedList<String[]>();
		
		for(String line : lines)
			list.add(line.split(sep));
		
		return list.toArray(new String[][]{});
	}
}
