package com.mycompany.storegui;

public class Disc extends Media {

	protected String director;
	protected int length;

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Disc(int id, String title, String category, String director, int length, float funs, float cost,
			int quantity, String image) {
		super(id, title, category, funs, cost, quantity, image);
		this.director = director;
		this.length = length;
	}

}
