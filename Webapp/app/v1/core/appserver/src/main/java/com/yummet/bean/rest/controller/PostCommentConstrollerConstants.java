package com.yummet.bean.rest.controller;

/**
 * @author yucheng
 * @version 1
 * */
public class PostCommentConstrollerConstants {
	public static final String DUMMY_POSTCOMMENT = "/postcomment/dummy";
	public static final String GET_POSTCOMMENT = "/postcomment/{id}";
	public static final String GET_ALL_POSTCOMMENT = "/postcomments/{postid}";
	public static final String CREATE_POSTCOMMENT = "/postcomment/create/{postid}/{siblingid}";
	public static final String UPDATE_POSTCOMMENT = "/postcomment/update/{postcommentid}";
	public static final String DELETE_POSTCOMMENT = "/postcomment/delete/{postcommentid}";
}
