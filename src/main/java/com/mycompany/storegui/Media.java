package com.mycompany.storegui;

public abstract class Media {
	protected int id;
	protected String title;
	protected String category;
	protected float funs;
	protected float cost;
	protected int quantity;
	protected String image;

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

}
