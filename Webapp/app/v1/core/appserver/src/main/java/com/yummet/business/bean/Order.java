package com.yummet.business.bean;

import java.util.Date;

/**
 * @author yucheng
 * @version 1
 * */
public class Order extends BeanObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8295725653761130449L;

	private String Id;
	private String thirdPartyInfo;
	private User buyer;
	private User solder;
	private Date TransactionDateTime;
	private Post parentPost;
	private int quantity;
	private double actualCost;
	private double score;
	private boolean isSuccess;

	public Order(String id) {
		setId(id);
	}

	public Order(User buyer, User solder) {
		setBuyer(buyer);
		setSolder(solder);
	}

	public Order(String id, User buyer, User solder) {
		setId(id);
		setBuyer(buyer);
		setSolder(solder);
	}

	// Getter and Setter
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		this.Id = id;
		super.setId(id);
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public User getSolder() {
		return solder;
	}

	public void setSolder(User solder) {
		this.solder = solder;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Date getTransactionDateTime() {
		return TransactionDateTime;
	}

	public void setTransactionDateTime(Date transactionDateTime) {
		TransactionDateTime = transactionDateTime;
	}

	public Post getParentPost() {
		return parentPost;
	}

	public void setParentPost(Post parentPost) {
		this.parentPost = parentPost;
	}

	public String getThirdPartyInfo() {
		return thirdPartyInfo;
	}

	public void setThirdPartyInfo(String thirdPartyInfo) {
		this.thirdPartyInfo = thirdPartyInfo;
	}

	public double getActualCost() {
		return actualCost;
	}

	public void setActualCost(double actualCost) {
		this.actualCost = actualCost;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
