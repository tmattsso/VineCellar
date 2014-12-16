package com.thomas.winecellar.data;

public class User {

	public static final int SALT_LENGTH_CHARS = 32;

	private int id;
	private String email;
	private String hashedPass;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	void setId(int id) {
		this.id = id;
	}

	public String getHashedPass() {
		return hashedPass;
	}

	public void setHashedPass(String hashedPass) {
		this.hashedPass = hashedPass;
	}
}
