package com.pipelinescript;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
// node1, node2, weight  
public class Graph extends Table {
	Table graph;
	private List<Node> nodes;
	public Graph(int numofEdge) {
		super(numofEdge,3);
		graph = getTable();
		//nodes = new ArrayList<>();
	}
	public Graph(String[][] g) {
		super(g);
		graph = getTable();
		//nodes = new ArrayList<>();
	}
	public int toCSV(String fileName,
					String separator){
	//Delimiter used in CSV file

	 final String COMMADELIMITER = ",";
	 final String NEW_LINE_SEPARATOR = "\n";
	 final String TAB_SEPARATOR = "\t";
	 // IF YOU LIKE TO ADD FILE HEADER AS OPTION
	 final String FILE_HEADER = "id,firstName,lastName,gender,age";
	 
	 FileWriter fileWriter = null;

	 try{
		 fileWriter = new FileWriter(fileName);
		 // Write the CSV file header
		 //fileWriter.append(FILE_HEADER.toString());
		 //Add a new line separator after the header
		 // fileWriter.append(NEW_LINE_SEPARATOR);
		 String[][] table = graph.getTableString();
		 for (String[] line : table) {
			 for (int j = 0 ; j < line.length ; j++) {
				 fileWriter.append(line[j]);
				 fileWriter.append(separator);
			 }
			 fileWriter.append(NEW_LINE_SEPARATOR);
		 }
		 System.out.println("CSV file was created successfully !!!");
		 return 1;
	 }catch(Exception e){
		 return -1;
		 /*
		 System.out.println("Error in CsvFileWriter !!!");
		 e.printStackTrace();
		 */

	 } finally {
		 	try {
		 		fileWriter.flush();
		 		fileWriter.close();
		 	} catch (IOException e) {
		 		return -1;
		 		/*
		 		System.out.println("Error while flushing/closing fileWriter !!!");
		 			e.printStackTrace();
		 			*/
		 	}
	 }

	 
	
	}
	
	public List<Node> getGraphNode(){
		List<Node> nodes = new ArrayList<>();
		String [][]edge = graph.getTableString();
		int numEdge = edge.length;
		
		for (String[] ed : edge) {
			int n1 = Integer.parseInt(ed[0]);
			int n2 = Integer.parseInt(ed[1]);
			int weig = Integer.parseInt(ed[2]);
			
			if (!nodes.contains(new Node(n1))) {
				nodes.add(new Node(n1));
			}
			if (!nodes.contains(new Node(n2))){
				nodes.add(new Node(n2));
			} 
			Node node1 = nodes.get(nodes.indexOf(new Node(n1)));
			Node node2 = nodes.get(nodes.indexOf(new Node(n2)));
			node1.adj.put(node2, weig);
			// INDIRECTED GRAPH, REMOVE THIS LINE TO MAKE IT DIRECTED GRAPH
			node2.adj.put(node1, weig); 
		}
		return nodes;
	}
	
}
class Node {
	String stringData;
	int nodeNumber;
	Map<Node, Integer> adj; 
	public Node() {
		stringData = null;
		adj = new HashMap<>();
	}
	
	public Node(int nodeNum) {
		adj = new HashMap<>();
		this.nodeNumber= nodeNum;
	}
	public Node(int nodeNum , String sData) {
		adj = new HashMap<>();
		this.nodeNumber= nodeNum;
		stringData = sData;
		
	}
	public Map getNeighbor() {
		return adj;
	}

	@Override
	public boolean equals(Object o){
		Node n = (Node)o;
		 return n.nodeNumber == this.nodeNumber ? true:false;
	}

}
