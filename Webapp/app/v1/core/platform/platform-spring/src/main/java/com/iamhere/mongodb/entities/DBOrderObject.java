package com.iamhere.mongodb.entities;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import com.iamhere.entities.PostObject;
import com.iamhere.entities.UserObject;
import com.iamhere.platform.func.DmlValidationHandler;

/**
 * MongoDb representation of the Order platform entity
 * @author jassica
 *
 */
@Document(collection = "Orders")
public class DBOrderObject extends DBEntityObject {
	private String jiaFang;
	private String yiFang;
	private boolean isSuccess;
	private DateTime TransactionDateTime;
	private String parentPost;
	private String thirdPartyInfo;
	private double actualCost; 
	private int quantity; 
	private double score;	
	private String status;
	
	public DBOrderObject()  {
	}

	public DBOrderObject(String id) {
		setId(id);
	}

	public String getJiaFang() {
		return jiaFang;
	}

	public void setJiaFang(String jiaFang) {
		this.jiaFang = jiaFang;
	}

	public String getYiFang() {
		return yiFang;
	}

	public void setYiFang(String yiFang) {
		this.yiFang = yiFang;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public DateTime getTransactionDateTime() {
		return TransactionDateTime;
	}

	public void setTransactionDateTime(DateTime transactionDateTime) {
		TransactionDateTime = transactionDateTime;
	}

	public String getParentPost() {
		return parentPost;
	}

	public void setParentPost(String parentPost) {
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
	
	public void setJiaWithEntity(UserObject jia) {
		this.jiaFang = jia == null ? null : jia.getEmail();
	}
	
	public void setYiWithEntity(UserObject yi) {
		this.yiFang = yi == null ? null : yi.getEmail();
	}
	
	public void setParentPostWithEntity(PostObject post) {
		this.parentPost = post == null ? null : post.getId();
	}
	
	public void setStatus(String dbValue) {
		this.status = dbValue;
	}
	
	public String getStatus() {
		return status;
	}
	
	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
	}

	@Override
	public String getDbTableName() {
		return "Orders";
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Orders [TODO to fill in]";
	}

	

}
