package com.yummet.entities;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;

import com.yummet.enums.PostStatus;
import com.yummet.enums.PostVisibilityEnum;
import com.yummet.platform.func.DmlOperationWrapper;
import com.yummet.platform.func.DmlValidationHandler;
import com.yummet.utilities.TextUtil;

/**
 * Platform entity for the Post
 * 
 * @author Jessica
 * @version 1
 *
 */
public abstract class PostObject extends EntityObject {
	private static final long serialVersionUID = -6011241820070393955L;

	// private fields
	protected String subject;
	protected String location;
	protected RecurEventInfo period;
	protected int quantity;
	protected String commentsOrDescription;
	protected double cost;
	protected String image;
	protected UserObject[] partners;
	protected String postCategory;
	protected PostStatus status;
	protected PostVisibilityEnum visibility;
	protected int numberOfOrders; // TODO: more detail
	protected int numberOfLikes;
	protected UserObject creator;
	protected UserObject lastModifiedBy;
	protected DateTime expireDate;

	// Related Object information which is relationship information in the
	// Relational DB
	protected Set<String> postComments;
	protected boolean postCommentsNumberIsChanged = false;

	public PostObject() {
	}
	
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

	public PostObject(String id) {
		setId(id);
		setPostCategory("Food");
		// Post visibility by default is Public
		setVisibility(PostVisibilityEnum.PUBLIC);
	}

	/* Getters and Setters */
	public void setId(String id) {
		super.setId(id);
		setCacheKey(id);
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

	/**
	 * When a user create a post comments, we add the postCommentId to the Post
	 * 
	 * @param postId
	 */
	public void addPostComments(String postId) {
		if (postComments == null) {
			postComments = new HashSet<String>();
		}
		postComments.add(postId);
		postCommentsNumberIsChanged = true;
	}

	/**
	 * When a user remove a post comments, we remove the postCommentId from the
	 * Post
	 * 
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
	public PostObject load() throws Exception {
		return (PostObject) super.load();
	}

	@Override
	public String toString() {
		return  "Post [id=" + getId() + ", subject=" + subject + ", location=" + location +  "]";
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
//		TODO
//		creator.removeCreatedPosts(getId());
//		return creator.save();
		return null;
	}
}