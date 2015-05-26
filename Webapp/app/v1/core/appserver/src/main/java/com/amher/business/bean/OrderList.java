package com.amher.business.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class OrderList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 530302966731210326L;

	private int count;
	private List<Order> Orders;

	public OrderList() {
		Orders = new ArrayList<Order>();
	}

	public OrderList(List<Order> Orders) {
		this.Orders = Orders;
		this.count = Orders.size();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@JsonSerialize
	public List<Order> getOrders() {
		return Orders;
	}

	public void setUsers(List<Order> Orders) {
		this.Orders = Orders;
	}

	public void add(Order order) {
		getOrders().add(order);
	}

	public Order get(int index) {
		return getOrders().get(index);
	}

	public void remove(int index) {
		getOrders().remove(index);
	}
}
