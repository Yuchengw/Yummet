package com.iamhere.mongodb.entities;

import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import com.iamhere.entities.RecurEventInfo;
import com.iamhere.entities.UserObject;
import com.iamhere.platform.func.DmlValidationHandler;

/**
 * Mongodb representation for the Post object
 * 
 * @author jassica
 *
 */
@Document(collection = "Posts")
public class DBPostObject extends DBEntityObject {
	// private fields
	private String subject;
	private String location;
	private RecurEventInfo period;
	private int quantity;
	private String commentsOrDescription;
	private double cost;
	private String image;
	private UserObject[] partners;
	private String postCategory;
	private String status;
	private String visibility;
	private int numberOfOrders; // TODO: more detail
	private int numberOfLikes;
	private UserObject creator;
	private UserObject lastModifiedBy;
	private String type;
	private DateTime expireDate;

	// Related Object information which is relationship information in the
	// Relational DB
	private Set<String> postComments;

	public DBPostObject(String id) {
		setId(id);
	}

	public DBPostObject() {
		super();
	}

	public String getType() {
		return type;
	}

	/* Getters and Setters */
	public void setPostComments(Set<String> posts) {
		postComments = posts;
	}

	public Set<String> getPostComments() {
		return postComments;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public RecurEventInfo getPeriod() {
		return period;
	}

	public void setPeriod(RecurEventInfo period) {
		this.period = period;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getCommentsOrDescription() {
		return commentsOrDescription;
	}

	public void setCommentsOrDescription(String commentsOrDescription) {
		this.commentsOrDescription = commentsOrDescription;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		assert (cost >= 0);
		this.cost = cost;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public UserObject[] getPartners() {
		return partners;
	}

	public void setPartners(UserObject[] partners) {
		this.partners = partners;
	}

	// public void setPartners(UserObject[] partners) {
	// if (partners != null) {
	// String[] partnerIds = new String[partners.length];
	// for (int i = 0; i < partnerIds.length; i++) {
	// partnerIds[i] = partners[i].getEmail();
	// }
	// this.partners = partnerIds;
	// }
	// }

	public String getPostCategory() {
		return postCategory;
	}

	public void setPostCategory(String postCategory) {
		this.postCategory = postCategory;
	}

	public String getStatus() {
		return this.status;// PostStatus.fromDbValue(this.status);
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public int getNumberOfOrders() {
		return numberOfOrders;
	}

	public void setNumberOfOrders(int numberOfOrders) {
		this.numberOfOrders = numberOfOrders;
	}

	public int getNumberOfLikes() {
		return numberOfLikes;
	}

	public void setNumberOfLikes(int numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}

	public UserObject getCreator() {
		return creator;
	}

	public void setCreator(UserObject creator) {
		this.creator = creator;
	}

	public void setType(String type) {
		this.type = type;
	}

	public DateTime getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(DateTime expireDate) {
		this.expireDate = expireDate;
	}

	public UserObject getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(UserObject lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
	}

	@Override
	public String getDbTableName() {
		return "Posts";
	}

	@Override
	public String toString() {
		return "Post [subject=" + subject + ", location=" + location + "]";
	}
}