package com.amher.lib.objectProvider;

import java.util.List;

import com.amher.business.bean.User;
import com.amher.business.bean.UserList;

/**
 * @author yucheng
 * @version 1
 * */
public class UserProvider {

	private static UserList allUsers;

	static {
		allUsers = new UserList();
		User u1 = new User("1", "Yucheng", "Wang", "ycwmike@gmail.com");
		User u2 = new User("2", "George", "Lin", "gglin@gmail.com");
		allUsers.add(u1);
		allUsers.add(u2);
	}

	public void add(User user) {
		allUsers.add(user);
	}

	public User get(String index) {
		return allUsers.get(Integer.parseInt(index));
	}

	public List<User> getAll() {
		return allUsers.getUsers();
	}

	public void remove(int id) {
		allUsers.remove(id);
	}
	
	/**
	 * update is expensive, think before do it
	 * */
	public void update(User updateUser) {
		update(updateUser.getId(), updateUser);
	}
	
	public void update(String index, User newUser) {
		List<User> allUserList = allUsers.getUsers();
		allUserList.set(Integer.parseInt(index), newUser);
	}
}
