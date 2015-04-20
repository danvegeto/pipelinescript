package pipelinescript.java;

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
