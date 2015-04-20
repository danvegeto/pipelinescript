package pipelinescript.java;

import org.junit.Assert;
import org.junit.Test;


public class TableTest {
	protected Table table;
	String[][] st = {{"sel", "test"}, {"tr","ed"}};
	Table t1 = new Table(st);
	
	String[][] st2 = {{"sel1", "test1"}, {"tr","ed"}};
	Table t2 = new Table(st2);
	
	String[][] res = {{"tr","ed"}};
	
	@Test(timeout = 2000)
  public void testCase0() {
      Assert.assertEquals(res, t1.intersection(t2));
  }
}
