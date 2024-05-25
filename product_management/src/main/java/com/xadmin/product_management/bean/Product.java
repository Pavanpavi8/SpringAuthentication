package com.xadmin.product_management.bean;

public class Product {
	
	private int id;
	private String Name;
	private double price;
	private double stocks;
	public Product(int id, String name, double price, double stocks) {
		super();
		this.id = id;
		this.Name = name;
		this.price = price;
		this.stocks = stocks;
	}
	public Product(String name, double price, double stocks) {
		super();
		Name = name;
		this.price = price;
		this.stocks = stocks;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public void setStocks(double stocks) {
		this.stocks = stocks;
	}
	public double getStocks() {
		return stocks;
	}
}
