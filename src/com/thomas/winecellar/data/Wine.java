package com.thomas.winecellar.data;

public class Wine {

	public enum WineType {
		WHITE, RED, ROSE, CHAMPAGNE, SPARKLING, SWEET;

		@Override
		public String toString() {
			switch (this) {
			case WHITE:
				return "White";
			case RED:
				return "Red";
			case CHAMPAGNE:
				return "Champagne";
			case SPARKLING:
				return "Sparkling";
			case ROSE:
				return "Rosé";
			case SWEET:
				return "Sweet";

			default:
				return "";
			}
		};
	}

	private int id = -1;

	private String name;
	private String producer;
	private String comment;
	private String region;
	private String appellation;
	private String country;
	private WineType type;
	private int amount = 0;
	private int year = -1;

	private String drinkFrom;
	private String drinkUntil;
	private String drinkBest;
	private String grapes;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getDrinkFrom() {
		return drinkFrom;
	}

	public void setDrinkFrom(String drinkFrom) {
		this.drinkFrom = drinkFrom;
	}

	public String getDrinkUntil() {
		return drinkUntil;
	}

	public void setDrinkUntil(String drinkUntil) {
		this.drinkUntil = drinkUntil;
	}

	public String getDrinkBest() {
		return drinkBest;
	}

	public void setDrinkBest(String drinkBest) {
		this.drinkBest = drinkBest;
	}

	public String getGrapes() {
		return grapes;
	}

	public void setGrapes(String grapes) {
		this.grapes = grapes;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Wine)) {
			return false;
		}
		final Wine other = (Wine) obj;
		return id == other.id;
	}

	@Override
	public int hashCode() {
		return id;
	}

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

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getAppellation() {
		return appellation;
	}

	public void setAppellation(String appellation) {
		this.appellation = appellation;
	}
}
