package com.pipelinescript;

import java.util.List;

public class Table
{
	public static final String SEPERATOR = "\t";
	
	private int rows, cols;
	private String[][] data;
	
	public Table(int rows, int cols)
	{
		this(new String[rows][cols]);
	}
	
	public Table(List<String[]> list)
	{
		this(list.toArray(new String[0][0]));
	}
	
	public Table(String[][] data)
	{
		this.data = data;
		
		rows = data.length;
		
		if(rows > 0)
			cols = data[0].length;
	}
	
	public String[][] get()
	{
		return data;
	}
	
	public int getRowCount()
	{
		return rows;
	}
	
	public int getColCount()
	{
		return cols;
	}
	
	public String get(int row, int col) 
	{
		return data[row][col];
	}
	
	public void set(int row, int col, String val) 
	{
		data[row][col] = val;
	}
	
	public String[] getRow(int row)
	{
		return data[row];
	}
	
	public String[] getCol(int col)
	{
		String[] array = new String[rows];
		
		for(int i = 0; i < rows; i++)
			array[i] = data[i][col];
		
		return array;
	}
	
	@Override
	public boolean equals(Object obj) 
	{	
		return obj instanceof Table && ((Table)obj).get().equals(data);
	}
	
	@Override
	public String toString()
	{
		String str = "";
		
		for(String[] row : data)
			str += String.join(SEPERATOR, row) + "\n";
		
		return str;
	}
}
