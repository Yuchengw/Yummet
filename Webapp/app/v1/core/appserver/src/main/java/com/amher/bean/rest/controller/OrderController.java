package com.amher.bean.rest.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amher.business.bean.Order;
import com.amher.business.bean.OrderList;

/**
 * @author yucheng
 * @version 1
 * */
public interface OrderController {
	public @ResponseBody Order getPost(@PathVariable String id);

	public @ResponseBody Order updatePost(@RequestBody String body);
	
	public @ResponseBody Order addPost(@RequestBody String body);

	public @ResponseBody OrderList removePost(@PathVariable String id);
	
	public @ResponseBody OrderList getPosts();
}
