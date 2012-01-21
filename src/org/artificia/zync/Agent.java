package org.artificia.zync;

import java.util.Vector;

public class Agent 
{
	private Vector<Node> nodes;
	
	public void addNodes(Node inNode)
	{
		nodes.add(inNode);
	}
	
	public Vector<Node> getNodes()
	{
		return nodes;
	}
	
	public void test_CreateLocalNode()
	{
		LocalFileSystem fs = new LocalFileSystem("/Users/Scott/Music/ZyncTest/porticoLocalFSSettings.xml");
		Node node = new Node(fs);
		node.test_populateDatabase();
		node.test_dumpDatabase();
	}
}
