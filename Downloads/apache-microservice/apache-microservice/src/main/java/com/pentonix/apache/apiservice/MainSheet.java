package com.pentonix.apache.apiservice;

public class MainSheet {
	private String node;
	private String nodename;
	private String screentype;
	private String header;
	private String layout;
//	private String footer;
	private String subNode;
	private String prevNode;
	private String nextNode;
	public MainSheet() {
		
	}

	

	public MainSheet(String node, String nodename, String screentype, String header, String layout, String subNode,
			String prevNode, String nextNode) {
		super();
		this.node = node;
		this.nodename = nodename;
		this.screentype = screentype;
		this.header = header;
		this.layout = layout;
		this.subNode = subNode;
		this.prevNode = prevNode;
		this.nextNode = nextNode;
	}



	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public String getScreentype() {
		return screentype;
	}

	public void setScreentype(String screentype) {
		this.screentype = screentype;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	
	
	public String getSubNode() {
		return subNode;
	}



	public void setSubNode(String subNode) {
		this.subNode = subNode;
	}



	public String getPrevNode() {
		return prevNode;
	}



	public void setPrevNode(String prevNode) {
		this.prevNode = prevNode;
	}



	public String getNextNode() {
		return nextNode;
	}



	public void setNextNode(String nextNode) {
		this.nextNode = nextNode;
	}



	@Override
	public String toString() {
		return "MainSheet [node=" + node + ", nodename=" + nodename + ", screentype=" + screentype + ", header="
				+ header + ", layout=" + layout + ", subNode=" + subNode + ", prevNode=" + prevNode + ", nextNode="
				+ nextNode + "]";
	}



}
