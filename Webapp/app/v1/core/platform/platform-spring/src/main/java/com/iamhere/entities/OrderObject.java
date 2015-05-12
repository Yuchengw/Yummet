package com.iamhere.entities;

import org.joda.time.DateTime;

import com.iamhere.enums.OrderStatus;
import com.iamhere.mongodb.entities.DBEntityObject;
import com.iamhere.mongodb.entities.DBOrderObject;
import com.iamhere.platform.func.DmlOperationWrapper;
import com.iamhere.platform.func.DmlValidationHandler;
import com.iamhere.utilities.TextUtil;

/**
 * Platform entity for the orders
 * @author jassica
 * @version 1
 */
public class OrderObject extends EntityObject {
	private static final long serialVersionUID = -6011241820070393954L;  
	private UserObject jiaFang;
	private UserObject yiFang;
	private boolean isSuccess;
	private DateTime transactionDateTime;
	private PostObject parentPost;
	private String thirdPartyInfo;
	private double actualCost; 
	private int quantity; 
	private double score;	
	private OrderStatus status;
	
	/*===================== Constructors =============================*/
	public OrderObject(UserObject jia, UserObject yi)  {
		setJiaFang(jia);
		setYiFang(yi);
	}
	
	public OrderObject(DBOrderObject db) {
		reloadAllFieldInformationFromDbObject(db);
	}

	public OrderObject(String id) {
		setId(id);
	}

	/*===================== Setters and Getters  =============================*/
	public UserObject getJiaFang() {
		return jiaFang;
	}

	public void setJiaFang(UserObject jiaFang) {
		this.jiaFang = jiaFang;
	}

	public UserObject getYiFang() {
		return yiFang;
	}

	public void setYiFang(UserObject yiFang) {
		this.yiFang = yiFang;
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
		DBOrderObject dbOrder = new DBOrderObject();
		dbOrder.setJiaFang(jiaFang);
//		dbOrder.setJiaWithEntity(jiaFang);
		dbOrder.setYiFang(yiFang);
//		dbOrder.setYiWithEntity(yiFang);
		dbOrder.setSuccess(isSuccess);
		dbOrder.setTransactionDateTime(transactionDateTime);
		dbOrder.setParentPost(parentPost);
//		dbOrder.setParentPostWithEntity(parentPost);
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
		setCreatedDate(new DateTime(dbOrder.getCreatedDate()));
		setLastModifiedDate(new DateTime(dbOrder.getLastModifiedDate()));
		setActualCost(dbOrder.getActualCost());
		setJiaFang(dbOrder.getJiaFang());
		setYiFang(dbOrder.getYiFang());
		setParentPost(dbOrder.getParentPost());
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

	
}
