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
 * @author jassica
 *
 */
public class OrderObject extends EntityObject {
	private UserObject jiaFang;
	private UserObject yiFang;
	private boolean isSuccess;
	private Date TransactionDateTime;
	private PostObject parentPost;
	private String thirdPartyInfo;
	private double actualCost; 
	private int quantity; 
	private double score;	
	private OrderStatus status;
	
	private DBOrderObject dbOrder;
	
	/*===================== Constructors =============================*/
	public OrderObject(UserObject jia, UserObject yi)  {
		dbOrder = new DBOrderObject();
		setJiaFang(jia);
		setYiFang(yi);
	}
	
	public OrderObject(DBOrderObject db) {
		this.dbOrder = db;
		reloadAllFieldInformationFromDbObject();
	}

	public OrderObject(String id) {
		dbOrder = new DBOrderObject(id);
		setId(id);
	}

	/*===================== Setters and Getters  =============================*/
	public UserObject getJiaFang() {
		return jiaFang;
	}

	public void setJiaFang(UserObject jiaFang) {
		this.jiaFang = jiaFang;
		dbOrder.setJiaWithEntity(jiaFang);
	}

	public UserObject getYiFang() {
		return yiFang;
	}

	public void setYiFang(UserObject yiFang) {
		this.yiFang = yiFang;
		dbOrder.setYiWithEntity(yiFang);
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
		dbOrder.setSuccess(isSuccess);
	}

	public Date getTransactionDateTime() {
		return TransactionDateTime;
	}

	public void setTransactionDateTime(Date transactionDateTime) {
		TransactionDateTime = transactionDateTime;
		dbOrder.setTransactionDateTime(transactionDateTime);
	}

	public PostObject getParentPost() {
		return parentPost;
	}

	public void setParentPost(PostObject parentPost) {
		this.parentPost = parentPost;
		dbOrder.setParentPostWithEntity(parentPost);
	}

	public String getThirdPartyInfo() {
		return thirdPartyInfo;
	}

	public void setThirdPartyInfo(String thirdPartyInfo) {
		this.thirdPartyInfo = thirdPartyInfo;
		dbOrder.setThirdPartyInfo(thirdPartyInfo);
	}

	public double getActualCost() {
		return actualCost;
	}

	public void setActualCost(double actualCost) {
		this.actualCost = actualCost;
		dbOrder.setActualCost(actualCost);
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		dbOrder.setQuantity(quantity);
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
		dbOrder.setScore(score);
	}
	
	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
		dbOrder.setStatus(status.getDbValue());
	}
	
	/*===================== Override super method =============================*/
	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
		if (getJiaFang() == null) {
			dml.addError("Order jiafang is not set!");
		}
		if (getYiFang() == null) {
			dml.addError("Order yifang is not set!");
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
		if (!TextUtil.isNullOrEmpty(getId())) {
			dbOrder.setId(getId());
		}
		return dbOrder;
	}

	@Override
	public void reloadAllFieldInformationFromDbObject() {
		setId(dbOrder.getId());
		setCreatedDate(new DateTime(dbOrder.getCreatedDate()));
		setLastModifiedDate(new DateTime(dbOrder.getLastModifiedDate()));
		setActualCost(dbOrder.getActualCost());
		setJiaFang(new UserObject(dbOrder.getJiaFang().toString()));
		setYiFang(new UserObject(dbOrder.getYiFang().toString()));
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

	
}
