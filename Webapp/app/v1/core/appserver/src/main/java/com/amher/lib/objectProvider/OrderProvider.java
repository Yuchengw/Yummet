package com.amher.lib.objectProvider;

import java.util.List;

import com.amher.business.bean.Order;
import com.amher.business.bean.OrderList;
import com.amher.business.bean.User;

/**
 * @author yucheng
 * @version 1
 * */
public class OrderProvider {

	private static OrderList allOrders;
	/**
	 * mocking
	 * */
	static {
		allOrders = new OrderList();
		Order o1 = new Order("1", new User("1", "Yucheng", "Wang",
				"ycwmike@gmail.com", "1234"), new User("2", "George", "Lin",
				"gglin@gmail.com", "1234"));
		allOrders.add(o1);
	}

	public void add(Order user) {
		allOrders.add(user);
	}

	public Order get(int index) {
		return allOrders.get(index);
	}

	public List<Order> getAll() {
		return allOrders.getOrders();
	}

	public void remove(int id) {
		allOrders.remove(id);
	}

	/**
	 * update is expensive, think before do it
	 * */
	public void update(Order order) {
		update(order.getId(), order);
	}
	
	public void update(String index, Order newOrder) {
		List<Order> allOrderList = allOrders.getOrders();
		allOrderList.set(Integer.parseInt(index), newOrder);
	}
}
