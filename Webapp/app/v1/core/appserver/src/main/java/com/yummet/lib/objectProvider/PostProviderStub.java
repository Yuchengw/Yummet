package com.yummet.lib.objectProvider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yummet.business.bean.Post;

public class PostProviderStub {
	List<Post> postList;

	PostProviderStub() {
		postList = new ArrayList<Post>();
		for (int i = 0; i < 100; i++) {
			Post post = new Post();
			post.setId("" + i);
			post.setCost((i+1) * 100.0);
			post.setCreatedDate(new Date());
			postList.add(new Post());
		}
	}
	
	public List<Post> get(int size, int cursor) {
		if(cursor >= 0 && cursor < postList.size() && size > 0) {
			return postList.subList(cursor, Math.min(cursor+size, postList.size()));
		}
		return new ArrayList<Post>();
	}
}
