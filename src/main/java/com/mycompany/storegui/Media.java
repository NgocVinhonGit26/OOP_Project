package com.mycompany.storegui;

public abstract class Media {
	private int id;
	private String title;
	private String category;
	private float funs;
	private float cost;
	private int quantity;
	private String image;

	public Media(int id, String title, String category, float funs, float cost, int quantity, String image) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.funs = funs;
		this.cost = cost;
		this.quantity = quantity;
		this.image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public float getFuns() {
		return funs;
	}

	public void setFuns(float funs) {
		this.funs = funs;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int compareTo(Object o) {
		if (o instanceof Media) {
			return this.getTitle().compareToIgnoreCase(((Media) o).getTitle());
		}
		return -9999;
	}

}