package com.iamhere.entities;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

import com.iamhere.enums.PostStatus;
import com.iamhere.enums.PostVisibilityEnum;
import com.iamhere.mongodb.entities.DBEntityObject;
import com.iamhere.mongodb.entities.DBPostObject;
import com.iamhere.platform.func.DmlOperationWrapper;
import com.iamhere.platform.func.DmlValidationHandler;
import com.iamhere.utilities.TextUtil;

/**
 * Platform entity for the Post
 * 
 * @author jassica
 *
 */
public class PostObject extends EntityObject {
	private static final long serialVersionUID = -6011241820070393955L;
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

	// Related Object information which is relationship information in the
	// Relational DB
	private Set<String> postComments;
	private boolean postCommentsNumberIsChanged = false;

	public PostObject(UserObject creator, String subject, String location,
			int quantity) {
		setCreator(creator);
		setSubject(subject);
		setLocation(location);
		setQuantity(quantity);
		setPostCategory("Food");
		// Post visibility by default is Public
		setVisibility(PostVisibilityEnum.PUBLIC);
	}

	public PostObject(DBPostObject db) {
		reloadAllFieldInformationFromDbObject(db);
	}

	public PostObject(String id) {
		setId(id);
	}

	/**
	 * When a user create a post comments, we add the postCommentId to the Post
	 * @param postId
	 */
	public void addPostComments(String postId)  {
		if (postComments == null) {
			postComments = new HashSet<String>();
		}
		postComments.add(postId);
		postCommentsNumberIsChanged = true;
	}
	
	/**
	 * When a user remove a post comments, we remove the postCommentId from the Post
	 * @param postId
	 */
	public void removePostComments(String postId) {
		if (postComments != null) {
			postComments.remove(postId);
			postCommentsNumberIsChanged = true;
		}
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

	public String getPostCategory() {
		return postCategory;
	}

	public void setPostCategory(String postCategory) {
		this.postCategory = postCategory;
	}

	public PostStatus getStatus() {
		return this.status;
	}

	public void setStatus(PostStatus status) {
		this.status = status;
	}

	public PostVisibilityEnum getVisibility() {
		return visibility;
	}

	public void setVisibility(PostVisibilityEnum visibility) {
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

	public String getType() {
		return type;
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
			for (UserObject partner : getPartners()) {
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
		DBPostObject dbPost = new DBPostObject();
		dbPost.setSubject(subject);
		dbPost.setLocation(location);
		dbPost.setPeriod(period);
		dbPost.setQuantity(quantity);
		dbPost.setCommentsOrDescription(commentsOrDescription);
		dbPost.setCost(cost);
		dbPost.setImage(image);
		dbPost.setPartners(partners);
		dbPost.setPostCategory(postCategory);
		dbPost.setStatus(status.getDbValue());
		dbPost.setVisibility(visibility.getDbValue());
		dbPost.setNumberOfOrders(numberOfOrders);
		dbPost.setNumberOfLikes(numberOfLikes);
		dbPost.setCreator(creator);
		dbPost.setType(type);
		dbPost.setExpireDate(expireDate);
		dbPost.setLastModifiedBy(lastModifiedBy);
		dbPost.setCreatedDate(getCreatedDate());
		dbPost.setLastModifiedDate(getLastModifiedDate());
		if (!TextUtil.isNullOrEmpty(getId())) {
			dbPost.setId(getId());
		}

		// set related relation information
		dbPost.setPostComments(postComments);
		return dbPost;
	}

	@Override
	public void reloadAllFieldInformationFromDbObject(DBEntityObject dbObject) {
		DBPostObject dbPost = (DBPostObject) dbObject;
		setCommentsOrDescription(dbPost.getCommentsOrDescription());
		setCost(dbPost.getCost());
		setCreatedDate(dbPost.getCreatedDate());
		setCreator(dbPost.getCreator());
		setExpireDate(dbPost.getExpireDate());
		setId(dbPost.getId());
		setImage(dbPost.getImage());
		setLastModifiedBy(dbPost.getLastModifiedBy());
		setLastModifiedDate(dbPost.getLastModifiedDate());
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
		
		// set related relation information
		setPostComments(dbPost.getPostComments());
	}

	public PostObject load() throws Exception {
		return (PostObject) super.load();
	}

	@Override
	public Class<?> getDbClass() {
		return DBPostObject.class;
	}

	@Override
	public String getDbTableName() {
		return new DBPostObject().getDbTableName();
	}

	@Override
	public DmlOperationWrapper saveRelatedInfoDuringUpdate() {
		creator.addCreatedPosts(getId());
		return creator.save();
	}

	@Override
	public boolean isRelatedInfoUpdate() {
		return postCommentsNumberIsChanged;
	}

	@Override
	public DmlOperationWrapper saveRelatedInfoDuringRemove() {
		creator.removeCreatedPosts(getId());
		return creator.save();
	}
	
}
