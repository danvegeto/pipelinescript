package pipelinescript.java;

import java.util.ArrayList;
import java.util.List;

public class Table {
	private String[][] table;
	
	public Table(int col, int row){
		this.table = new String[col][row];
	}
	public Table(String[][] t ){
		this.table = t;
	}
	public Table(List<String[]> tab){
		this.table = new String[tab.size()][tab.get(0).length];
		for (int i = 0 ; i < table.length ; i++) {
			 for (int j = 0 ; j < table[0].length ; j++) {
				 this.table[i][j] = ((String[])tab.get(i))[j];
			 }
		 }
	}
	
	@Override
  public boolean equals(Object other) {
		
		 Table otherTable = (Table)other;
		 String [][]comp = otherTable.table;
		 for (int i = 0 ; i < table.length ; i++) {
			 for (int j = 0 ; j < table[0].length ; j++) {
				 if (!this.table[i][j].equals(comp[i][j])) {
					 return false;
				 }
			 }
		 }
		 
		return true;
	}
	public String get(int i,int j) {
		 return table[i][j];
	}
	public void set(int i,int j, String val) {
		 table[i][j] = val;
	}

	public Table union(Table otherTable) {
		 
		 String [][]comp = otherTable.table;
		 List<String[]> tbl = new ArrayList<>();
		 for  (int i = 0 ; i < comp.length ; i++){
			 String [] arr1 = comp[i];
			 String [] arr2 = this.table[i];
			 if (!isEqual(arr1, arr2)){
				 tbl.add(arr2);
			 } 
				 tbl.add(arr1);
		 }
		 return new Table(tbl);
	}
	private boolean isEqual(String [] arr1, String [] arr2){
		
		if (arr1.length != arr2.length) {
			return false;
		}
		
		for (int i = 0 ; i < arr1.length ; i++ ) {
			if (!arr1[i].equals(arr1[i])){
				return false;
			}
		}
		return true;
	}
	 
}
