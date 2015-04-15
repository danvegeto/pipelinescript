package pipeline.script;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	
	private List<Node> nodes;
	public Graph() {
		nodes = new ArrayList<>();
	}
	
	public Graph(List<Node> node) {
		nodes = node;
	}


}
class Node {
	String stringData;
	int intData;
	List<Node> adj; 
	public Node() {
		intData = -1;
		stringData = null;
		adj = new ArrayList<>();
	}
	
	public Node(int iData , String sData) {
		intData = iData;
		stringData = sData;
	}


	public List<Node> getNeighbor() {
		return adj;
	}


}
