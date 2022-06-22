package com.mycompany.storegui;

public abstract class Media {
	protected int id;
	protected String title;
	protected String category;
	protected float cost;
	protected int quantity;

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

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public Media(int id, String title, String category, float cost, int quantity) {
		this.id = id;
		this.title = title;
		this.category = category;
		this.cost = cost;
		this.quantity = quantity;
	}
}
