package com.pipelinescript;

import java.util.LinkedList;
import java.util.List;

public class CSVFormat extends TableFormat
{
	private char sep, quote;
	
	public CSVFormat()
	{
		this(',');
	}
	
	public CSVFormat(char sep)
	{
		this(sep, '"');
	}
	
	public CSVFormat(char sep, char quote)
	{
		this.sep = sep;
		this.quote = quote;
	}
	
	@Override
	public String encode(String[][] data)
	{
		String str = "";
		
		for(int i = 0; i < data.length; i++)
		{
			for(int j = 0; j < data[i].length; j++)
			{
				String val = data[i][j].replaceAll("\"", "\"\"");
				
				if(isNumber(val))
					str += val;
				else
					str += "\"" + val + "\"";
				
				if(j < data[i].length-1)
					str += sep;
			}
			
			str += "\n";
		}
		
		return str;
	}

	@Override
	public String[][] decode(String str)
	{
		List<String[]> lines = new LinkedList<String[]>();
		List<String> line = new LinkedList<String>();
		String value = "";
		
		boolean inQuote = false;
		
		for(int i = 0; i < str.length(); i++)
		{
			char c = str.charAt(i);
	
			if(inQuote)
			{
				if(c == quote)
				{
					if(i < str.length()-1 && str.charAt(i+1) == quote)
					{
						value += quote;
						i++;
					}
					else
					{
						inQuote = false;
					}
				}
				else
				{
					value += c;
				}
			}
			else
			{
				if(c == sep)
				{
					line.add(value);
					value = "";
				}
				else if(c == '\n')
				{
					line.add(value);
					lines.add(line.toArray(new String[]{}));
					
					value = "";
					line = new LinkedList<String>();
				}
				else
				{
					value += c;
				}
			}
			
			if(i == str.length()-1)
			{
				if(!value.isEmpty())
					line.add(value);
				
				if(!line.isEmpty())
					lines.add(line.toArray(new String[]{}));
			}
		}
		
		return lines.toArray(new String[][]{});
	}
	
	private static boolean isNumber(String str)  
	{  
		try  
		{
			Double.parseDouble(str);
		}
		catch(NumberFormatException nfe)
		{  
			return false;  
		}
		
		return true;  
	}
}
