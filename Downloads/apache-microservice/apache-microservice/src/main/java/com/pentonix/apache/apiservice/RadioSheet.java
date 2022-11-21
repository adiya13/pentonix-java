package com.pentonix.apache.apiservice;

public class RadioSheet {
	private String id;
	private String next;
	private String text;
	
	public RadioSheet() {
		
	}

	public RadioSheet(String id, String next, String text) {
		super();
		this.id = id;
		this.next = next;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "RadioSheet [id=" + id + ", next=" + next + ", text=" + text + "]";
	}
	
	
	

}
