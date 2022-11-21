package com.pentonix.apache.apiservice;

import java.io.Serializable;
import java.util.List;

public class SubNodeResponse implements Serializable {
	private static final long serialVersionUID = -5616176897013108345L;
	 private String node;
	 private String nodename;
	 private String screentype;
	 private List header;
	 private String layout; //text,image,radio,comments
	 private List text;
	 private List bulletText;
	 private List button;
	 private List textWithSearch;
	 private List image;
	 private List radio;
	 private List comments;
	 private String mainNode;
	 private String prevNode;
	 private String nextNode;
	 
	public SubNodeResponse() {
		
	}
	
	public SubNodeResponse(String node, String nodename, String screentype, List header, String layout, List text,
			List bulletText, List button, List textWithSearch, List image, List radio, List comments, String mainNode,
			String prevNode, String nextNode) {
		super();
		this.node = node;
		this.nodename = nodename;
		this.screentype = screentype;
		this.header = header;
		this.layout = layout;
		this.text = text;
		this.bulletText = bulletText;
		this.button = button;
		this.textWithSearch = textWithSearch;
		this.image = image;
		this.radio = radio;
		this.comments = comments;
		this.mainNode = mainNode;
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
	public List getHeader() {
		return header;
	}
	public void setHeader(List header) {
		this.header = header;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public List getText() {
		return text;
	}
	public void setText(List text) {
		this.text = text;
	}
	public List getImage() {
		return image;
	}
	public void setImage(List image) {
		this.image = image;
	}
	public List getRadio() {
		return radio;
	}
	public void setRadio(List radio) {
		this.radio = radio;
	}
	public List getComments() {
		return comments;
	}
	public void setComments(List comments) {
		this.comments = comments;
	}
	public String getMainNode() {
		return mainNode;
	}
	public void setMainNode(String mainNode) {
		this.mainNode = mainNode;
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

	public List getBulletText() {
		return bulletText;
	}

	public void setBulletText(List bulletText) {
		this.bulletText = bulletText;
	}

	public List getButton() {
		return button;
	}

	public void setButton(List button) {
		this.button = button;
	}

	public List getTextWithSearch() {
		return textWithSearch;
	}

	public void setTextWithSearch(List textWithSearch) {
		this.textWithSearch = textWithSearch;
	}
	
	 
}
