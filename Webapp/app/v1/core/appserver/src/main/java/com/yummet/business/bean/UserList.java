package com.yummet.business.bean;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * This class could be used generically, partner list
 * @author yucheng
 * @version 1
 * */
public class UserList extends BeanObject {

	private static final long serialVersionUID = -1370183045720704453L;

	private int count;
	private List<User> users;

	public UserList() {
		users = new ArrayList<User>();
	}

	public UserList(List<User> users) {
		this.users = users;
		this.count = users.size();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@JsonSerialize
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	// extension
	public void add(User user) {
		getUsers().add(user);
	}

	public User get(int index) {
		return getUsers().get(index);
	}
	
	public void remove(int id) {
		getUsers().remove(id);
	}
}
