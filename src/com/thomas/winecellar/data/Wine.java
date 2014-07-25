package com.thomas.winecellar.data;

public class Wine {

	public enum WineType {
		WHITE, RED, ROSE, BUBBLY
	}

	private String name;
	private String producer;
	private String comment;
	private WineType type;
	private int id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public WineType getType() {
		return type;
	}

	public void setType(WineType type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
