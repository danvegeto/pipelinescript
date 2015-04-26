package com.pipelinescript;

import org.junit.Assert;
import org.junit.Test;

public class FileManagerTest {
	protected FileManager filemanager = new FileManager();
	
	//filemanager = new FileManager();
	String [][]qtable = {{"0","1","1"},{"1","2","2"},{"0","2","4"}};
	Graph gr = new Graph(qtable);
	
	@Test
  public void testCase0() {
		filemanager.create("test0", "./TestFile.csv");
		Graph g2 = filemanager.getGraphFromCSV("test0");
		Assert.assertEquals(g2 , gr);
  }
	
}
