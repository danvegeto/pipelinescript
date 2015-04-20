package pipelinescript.java;

import org.junit.Assert;
import org.junit.Test;



public class GraphTest {

	protected Graph graph;
	
	String [][]qtable = {{"0","1","1"},{"1","2","2"},{"0","2","4"}};
	Graph gr = new Graph(qtable);
	
	@Test
  public void testCase0() {
		Assert.assertEquals(gr.toCSV("TestFile", "\t"), 1);
  }
	
}

