
package com.pipelinescript;
public class Pipeline
{

public static void main(String[] args) throws Exception
{
Function head = new Function("head");
 Function scrape = new Function("newspaper/scrape.py");
 Function download = new Function("newspaper/download.py");
 FileManager.write("source.txt", "http://www.nytimes.com");
 FileManager.write("urls.csv", PluginManager.execute(scrape, "source.txt" ));
 FileManager.split("url#.txt", PluginManager.execute(head, "urls.csv" ));
 ProcessManager.executeParallel(download, "url#.txt", "text#.txt");
 System.out.println(PluginManager.execute(head, "text#.txt" ));
}

}
