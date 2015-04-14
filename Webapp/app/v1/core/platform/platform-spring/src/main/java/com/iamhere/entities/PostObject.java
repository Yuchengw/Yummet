package com.iamhere.entities;

import org.joda.time.DateTime;

import com.iamhere.enums.PostStatus;
import com.iamhere.enums.PostVisibilityEnum;
import com.iamhere.mongodb.entities.DBEntityObject;
import com.iamhere.mongodb.entities.DBPostObject;
import com.iamhere.platform.func.DmlValidationHandler;
import com.iamhere.utilities.TextUtil;

/**
 * Platform entity for the Post
 * @author jassica
 *
 */
public class PostObject extends EntityObject {
	// Final static constant for different post types
	public final static String REQUEST = "REQUEST";
	public final static String PROVIDE = "PROVIDE";
	
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
	private PostStatus status;
	private PostVisibilityEnum visibility;
	private int numberOfOrders; // TODO: more detail
	private int numberOfLikes;
	private UserObject creator;
	private UserObject lastModifiedBy;
	private String type;
	private DateTime expireDate;
	
	// DbObject information holder
	DBPostObject dbPost;
			
	public PostObject(UserObject creator, String subject, String location, int quantity) {
		dbPost = new DBPostObject();
		setCreator(creator);
		setSubject(subject);
		setLocation(location);
		setQuantity(quantity);
		setPostCategory("Food");
		// Post visibility by default is Public
		setVisibility(PostVisibilityEnum.PUBLIC);
	}
	
	public PostObject(DBPostObject db) {
		this.dbPost = db;
		reloadAllFieldInformationFromDbObject();
	}

	public PostObject(String id) {
		dbPost = new DBPostObject(id);
		setId(id);
	}

	/* Getters and Setters */
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
		dbPost.setSubject(subject);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
		dbPost.setLocation(location);
	}

	public RecurEventInfo getPeriod() {
		return period;
	}

	public void setPeriod(RecurEventInfo period) {
		this.period = period;
		dbPost.setPeriod(period);
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		dbPost.setQuantity(quantity);
	}

	public String getCommentsOrDescription() {
		return commentsOrDescription;
	}

	public void setCommentsOrDescription(String commentsOrDescription) {
		this.commentsOrDescription = commentsOrDescription;
		dbPost.setCommentsOrDescription(commentsOrDescription);
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
		dbPost.setCost(cost);
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
		dbPost.setImage(image);
	}

	public UserObject[] getPartners() {
		return partners;
	}

	public void setPartners(UserObject[] partners) {
		this.partners = partners;
		dbPost.setPartners(partners);
	}

	public String getPostCategory() {
		return postCategory;
	}

	public void setPostCategory(String postCategory) {
		this.postCategory = postCategory;
		dbPost.setPostCategory(postCategory);
	}

	public PostStatus getStatus() {
		return this.status;
	}

	public void setStatus(PostStatus status) {
		this.status = status;
		dbPost.setStatus(status.getDbValue());
	}

	public PostVisibilityEnum getVisibility() {
		return visibility;
	}

	public void setVisibility(PostVisibilityEnum visibility) {
		this.visibility = visibility;
		dbPost.setVisibility(visibility.getDbValue());
	}
	
	public int getNumberOfOrders() {
		return numberOfOrders;
	}

	public void setNumberOfOrders(int numberOfOrders) {
		this.numberOfOrders = numberOfOrders;
		dbPost.setNumberOfOrders(numberOfOrders);
	}

	public int getNumberOfLikes() {
		return numberOfLikes;
	}

	public void setNumberOfLikes(int numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
		dbPost.setNumberOfLikes(numberOfLikes);
	}

	public UserObject getCreator() {
		return creator;
	}

	public void setCreator(UserObject creator) {
		this.creator = creator;
		dbPost.setCreatorWithEntity(creator);
	}

	public String getType()  {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
		dbPost.setType(type);
	}
	
	public DateTime getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(DateTime expireDate) {
		this.expireDate = expireDate;
		dbPost.setExpireDate(expireDate.toDate());
	}
	
	public UserObject getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(UserObject lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
		dbPost.setLastModifiedByWithEntity(lastModifiedBy);
	}

	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
		// cost should be greater than or equal to 0
		if (TextUtil.isNullOrEmpty(getSubject())) {
			dml.addError("The subject is not set!");
		}
		if (TextUtil.isNullOrEmpty(getLocation())) {
			dml.addError("The location is not set!");
		}
		if (getQuantity() < 0) {
			dml.addError("The quntity cannot be negative!");
		}
		if (getCost() < 0) {
			dml.addError("The cost cannot be negative number!");
		}
		if (TextUtil.isNullOrEmpty(getType())) {
			dml.addError("The post type is not set!");
		}
		
		if (getPartners() != null) {
			for (UserObject partner: getPartners()) {
				if (partner == null) {
					dml.addError("One of the partner is not set!");
					break;
				}
			}
		}
		
		if (getCreator() == null) {
			dml.addError("Post creator is not set!");
		}
		if (getLastModifiedBy() == null) {
			dml.addError("Post last modify by is not set!");
		}
		
		// the post default category is food
		if (TextUtil.isNullOrEmpty(getPostCategory())) {
			setPostCategory("Food");
		}
		super.saveHook_Validate(dml);
	}

	@Override
	public DBEntityObject getDbObject() {
		if (!TextUtil.isNullOrEmpty(getId())) {
			dbPost.setId(getId());
		}
		return dbPost;
	}

	@Override
	public void reloadAllFieldInformationFromDbObject() {
		setCommentsOrDescription(dbPost.getCommentsOrDescription());
		setCost(dbPost.getCost());
		setCreatedDate(new DateTime(dbPost.getCreatedDate()));
		setCreator(new UserObject(dbPost.getCreator().toString()));
		setExpireDate(new DateTime(dbPost.getExpireDate()));
		setId(dbPost.getId());
		setImage(dbPost.getImage());
		setLastModifiedBy(new UserObject(dbPost.getLastModifiedBy().toString()));
		setLastModifiedDate(new DateTime(dbPost.getLastModifiedDate()));
		setLocation(dbPost.getLocation());
		setNumberOfLikes(dbPost.getNumberOfLikes());
		setNumberOfOrders(dbPost.getNumberOfOrders());
		setPeriod(dbPost.getPeriod());
		// TODO
		setPartners(null);
		setPostCategory(dbPost.getPostCategory());
		setQuantity(dbPost.getQuantity());
		setStatus(PostStatus.fromDbValue(dbPost.getStatus()));
		setVisibility(PostVisibilityEnum.fromDbValue(dbPost.getVisibility()));
		setSubject(dbPost.getSubject());
		setType(dbPost.getType());
	}

	public PostObject load() throws Exception {
		return (PostObject) super.load();
	}
}
