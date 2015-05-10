package com.pipelinescript;

public class Table
{
	public static final TableFormat DEFAULT_FORMAT = new CSVFormat();
	
	private TableFormat format;
	private String[][] data;
	
	public Table()
	{
		this(DEFAULT_FORMAT);
	}
	
	public Table(TableFormat format)
	{
		this(format, new String[0][0]);
	}
	
	public Table(String str)
	{
		this(DEFAULT_FORMAT, str);
	}
	
	public Table(TableFormat format, String str)
	{
		this(format, format.decode(str));
	}
	
	public Table(String[][] data)
	{
		this(DEFAULT_FORMAT, data);
	}
	
	public Table(TableFormat format, String[][] data)
	{
		this.format = format;
		this.data = data;
	}
	
	public TableFormat getFormat()
	{
		return format;
	}
	
	public void setFormat(TableFormat format)
	{
		this.format = format;
	}
	
	public String[][] getData()
	{
		return data;
	}
	
	public int getRows()
	{
		return data.length;
	}
	
	public int getCols()
	{
		if(data.length == 0)
			return 0;
		
		return data[0].length;
	}
	
	public String get(int row, int col) 
	{
		return data[row][col];
	}
	
	public void set(int row, int col, String val) 
	{
		data[row][col] = val;
	}
	
	@Override
	public boolean equals(Object obj) 
	{	
		return obj instanceof Table && ((Table)obj).getData().equals(data);
	}
	
	@Override
	public String toString()
	{
		return format.encode(data);
	}
}
