package com.yummet.entities;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import com.yummet.enums.OrderStatus;
import com.yummet.platform.func.DmlOperationWrapper;
import com.yummet.platform.func.DmlValidationHandler;

/**
 * Platform entity for the orders
 * 
 * @author Jessica
 * @version 1
 *
 */
@Document(collection = "Orders")
public class OrderObject extends EntityObject {
	private static final long serialVersionUID = -6011241820070393954L;
	private UserObject seller;
	private UserObject buyer;
	private boolean isSuccess;
	private DateTime transactionDateTime;
	private String parentPost;
	private String parentPostType;
	private String thirdPartyInfo;
	private double actualCost;
	private int quantity;
	private double score;
	private OrderStatus status;

	/* ===================== Constructors ============================= */
	public OrderObject() {
	}

	public OrderObject(UserObject seller, UserObject buyer) {
		setSeller(seller);
		setBuyer(buyer);
	}

	public OrderObject(String id) {
		setId(id);
	}

	/* ===================== Setters and Getters ============================= */
	public UserObject getseller() {
		return seller;
	}

	public void setSeller(UserObject seller) {
		this.seller = seller;
	}

	public UserObject getbuyer() {
		return buyer;
	}

	public void setBuyer(UserObject buyer) {
		this.buyer = buyer;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public DateTime getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(DateTime transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public String getParentPost() {
		return parentPost;
	}

	public void setParentPost(String parentPost) {
		this.parentPost = parentPost;
	}

	public String getParentPostType() {
		return parentPostType;
	}

	public void setParentPostType(String parentPostType) {
		// P for provide post and R for request post
		this.parentPostType = parentPostType;
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

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	/* ===================== Override super method ============================= */
	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
		if (getseller() == null) {
			dml.addError("Order seller is not set!");
		}
		if (getbuyer() == null) {
			dml.addError("Order buyer is not set!");
		}
		if (getParentPost() == null) {
			dml.addError("Parent post is forget to set!");
		}
		if (getParentPostType() == null) {
			dml.addError("Parent post type is forget to set!");
		}
		// The default status of the order is Open
		if (status == null) {
			setStatus(OrderStatus.OPEN);
		}
		super.saveHook_Validate(dml);
	}

	@Override
	public void setId(String id) {
		super.setId(id);
		setCacheKey(id);
	}

	public OrderObject load() throws Exception {
		return (OrderObject) super.load();
	}

	@Override
	public String toString() {
		return "OrderInfo is [id=" + getId() + ", transaction datetime ="
				+ getTransactionDateTime() + ", parentPost = "
				+ getParentPost() + ", parentPostType = " + getParentPostType()
				+ ", actualCost = " + getActualCost() + ", quantity = "
				+ getQuantity() + ", score = " + getScore() + "]";
	}

	@Override
	public DmlOperationWrapper saveRelatedInfoDuringUpdate() {
		PostObject post = loadParentEntityInfo();
		// Try to load the post object information
		try {
			post = post.load();
			post.addOrder(getId(), getbuyer().getEmail());
			// TODO: verify if the parent has saved successfully
			return post.save();
		} catch (Exception e) {
			// TODO: if save fails, we need revert
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public DmlOperationWrapper saveRelatedInfoDuringRemove() {
		// update parent post information to remove the order information
		PostObject post = loadParentEntityInfo();
		// Try to load the post object information
		try {
			post = post.load();
			post.removeOrder(getId());
			// TODO: verify if the parent has saved successfully
			return post.save();
		} catch (Exception e) {
			// TODO: if save fails, we need revert
			e.printStackTrace();
		}
		return null;
	}

	protected PostObject loadParentEntityInfo() {
		String postType = getParentPostType();
		PostObject post = null;
		if (postType.equals("P")) {
			post = new ProvidePostObject(getParentPost());
		} else if (postType.equals("R")) {
			post = new RequestPostObject(getParentPost());
		}
		return post;
	}

	@Override
	public boolean isRelatedInfoUpdate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDbTableName() {
		return "Orders";
	}
}