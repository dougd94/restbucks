package com.coffeeshop.coffeeshop_app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Orders {
	@Id
	@Column(name="OrderID")
	private int OrderID;
	@Column(name = "coffee")
	private String coffee;
	@Column(name = "milk")
	private String milk;
	@Column(name = "sugar")
	private int sugar;
	@Column(name = "status")
	private String status;
	
	public Orders() {
		coffee="";
		milk="";
		sugar = 0;
		status="";
		OrderID=0;
	}

	public int getOrderID() {
		return OrderID;
	}

	public void setOrderID(int orderID) {
		OrderID = orderID;
	}

	public String getCoffee() {
		return coffee;
	}

	public void setCoffee(String coffee) {
		this.coffee = coffee;
	}

	public String getMilk() {
		return milk;
	}

	public void setMilk(String milk) {
		this.milk = milk;
	}

	public int getSugar() {
		return sugar;
	}

	public void setSugar(int sugar) {
		this.sugar = sugar;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
