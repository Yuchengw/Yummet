package com.iamhere.entities;

import java.util.Date;

import org.joda.time.DateTime;

import com.iamhere.enums.OrderStatus;
import com.iamhere.mongodb.entities.DBEntityObject;
import com.iamhere.mongodb.entities.DBOrderObject;
import com.iamhere.platform.func.DmlValidationHandler;
import com.iamhere.utilities.TextUtil;

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
	
	public OrderObject(DBOrderObject db) {
		reloadAllFieldInformationFromDbObject(db);
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
		if (getseller() == null) {
			dml.addError("Order seller is not set!");
		}
		if (getbuyer() == null) {
			dml.addError("Order buyer is not set!");
		}
		if (getParentPost() == null) {
			dml.addError("Parent post is forget to set!");
		} else {
			if (getParentPost().getType() != PostObject.PROVIDE) {
				dml.addError("The order can only happen on provide type of post!");
			}
		}
		
		// The default status of the order is Open
		if(status == null) {
			setStatus(OrderStatus.OPEN);
		}
		super.saveHook_Validate(dml);
	}

	@Override
	public DBEntityObject getDbObject() {
		DBOrderObject dbOrder = new DBOrderObject();
		dbOrder.setJiaWithEntity(seller);
		dbOrder.setYiWithEntity(buyer);
		dbOrder.setSuccess(isSuccess);
		dbOrder.setTransactionDateTime(transactionDateTime);
		dbOrder.setParentPostWithEntity(parentPost);
		dbOrder.setThirdPartyInfo(thirdPartyInfo);
		dbOrder.setActualCost(actualCost);
		dbOrder.setQuantity(quantity);
		dbOrder.setScore(score);
		dbOrder.setStatus(status.getDbValue());
		dbOrder.setCreatedDate(getCreatedDate());
		dbOrder.setLastModifiedDate(getLastModifiedDate());
		if (!TextUtil.isNullOrEmpty(getId())) {
			dbOrder.setId(getId());
		}
		return dbOrder;
	}

	@Override
	public void reloadAllFieldInformationFromDbObject(DBEntityObject dbObject) {
		DBOrderObject dbOrder = (DBOrderObject) dbObject;
		setId(dbOrder.getId());
		setCreatedDate(dbOrder.getCreatedDate());
		setLastModifiedDate(dbOrder.getLastModifiedDate());
		setActualCost(dbOrder.getActualCost());
		setSeller(new UserObject(dbOrder.getSeller().toString()));
		setBuyer(new UserObject(dbOrder.getBuyer().toString()));
		setParentPost(new PostObject(dbOrder.getParentPost().toString()));
		setQuantity(dbOrder.getQuantity());
		setScore(dbOrder.getScore());
		setSuccess(dbOrder.isSuccess());
		setThirdPartyInfo(dbOrder.getThirdPartyInfo());
		setTransactionDateTime(dbOrder.getTransactionDateTime());
		setStatus(OrderStatus.fromDbValue(dbOrder.getStatus()));
	}
	
	public OrderObject load() throws Exception {
		return (OrderObject) super.load();
	}

	@Override
	public Class<?> getDbClass() {
		return DBOrderObject.class;
	}

	@Override
	public String getDbTableName() {
		return new DBOrderObject().getDbTableName();
	}

	
}