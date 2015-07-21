package com.yummet.entities;

import java.util.Date;

import org.joda.time.DateTime;

import com.yummet.enums.OrderStatus;
import com.yummet.mongodb.entities.DBEntityObject;
import com.yummet.mongodb.entities.DBOrderObject;
import com.yummet.platform.func.DmlOperationWrapper;
import com.yummet.platform.func.DmlValidationHandler;
import com.yummet.utilities.TextUtil;

/**
 * Platform entity for the orders
 * @author Jessica
 * @version 1
 *
 */
public class OrderObject extends EntityObject {
	private static final long serialVersionUID = -6011241820070393954L;  
	private UserObject seller;
	private UserObject buyer;
	private boolean isSuccess;
	private DateTime transactionDateTime;
	private PostObject parentPost;
	private String thirdPartyInfo;
	private double actualCost; 
	private int quantity; 
	private double score;	
	private OrderStatus status;
	
	/*===================== Constructors =============================*/
	public OrderObject(UserObject seller, UserObject buyer)  {
		setSeller(seller);
		setBuyer(buyer);
	}
	
	public OrderObject(String id) {
		setId(id);
	}

	/*===================== Setters and Getters  =============================*/
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

	public PostObject getParentPost() {
		return parentPost;
	}

	public void setParentPost(PostObject parentPost) {
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
	
	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	/*===================== Override super method =============================*/
	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
//		if (getseller() == null) {
//			dml.addError("Order seller is not set!");
//		}
//		if (getbuyer() == null) {
//			dml.addError("Order buyer is not set!");
//		}
//		if (getParentPost() == null) {
//			dml.addError("Parent post is forget to set!");
//		} else {
//			if (getParentPost().getType() != PostObject.PROVIDE) {
//				dml.addError("The order can only happen on provide type of post!");
//			}
//		}
//		
//		// The default status of the order is Open
//		if(status == null) {
//			setStatus(OrderStatus.OPEN);
//		}
//		super.saveHook_Validate(dml);
	}

	public OrderObject load() throws Exception {
		return (OrderObject) super.load();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DmlOperationWrapper saveRelatedInfoDuringUpdate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DmlOperationWrapper saveRelatedInfoDuringRemove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRelatedInfoUpdate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDbTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	
}