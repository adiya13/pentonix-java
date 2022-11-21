package com.pentonix.apache.apiservice;

public class PreviousSheet {
	private Integer id;
	private Double text;
	public PreviousSheet() {
		
	}
	public PreviousSheet(Integer id, Double text) {
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
	public Double getText() {
		return text;
	}
	public void setText(Double text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "PreviousSheet [id=" + id + ", text=" + text + "]";
	}
	
	

}
