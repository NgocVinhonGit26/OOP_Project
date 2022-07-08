package com.mycompany.storegui;

public class DigitalVideoDisc extends Disc {
	private String producer;

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public DigitalVideoDisc(int id, String title, String category, int length, String director, float cost,
			int quantity, String image, String producer) {
		super(id, title, category, director, length, cost, quantity, image);
		this.producer = producer;
	}
}
