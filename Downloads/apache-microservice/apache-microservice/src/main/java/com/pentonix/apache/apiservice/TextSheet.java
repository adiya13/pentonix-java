package com.pentonix.apache.apiservice;

public class TextSheet {
	private Integer id;
	private String text;
	
	public TextSheet() {
		
	}

	public TextSheet(Integer id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "TextSheet [id=" + id + ", text=" + text + "]";
	}
	
	

}
