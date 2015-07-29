package com.yummet.system.context;

import com.yummet.business.bean.User;

/**
 * @author yucheng
 * @since 1
 * */
public class UserContextImpl implements UserContextIntf{

	private final User user;
	
	public UserContextImpl() {
		try {
			this.user = User.getInstance();
		} catch (Exception e) {
			throw new UnableToEstablishContextException(e);
		}
	}
	
	@Override
	final public String getUserId() {
		return getUserId(false);
	}

	@Override
	final public String getUserId(boolean nullOk) {
		return getUserId(false, false);
	}

	@Override
	final public String getUserId(boolean nullOk, boolean notAuthenticatedOk) {
		if (!notAuthenticatedOk) {
			//TODO: implement Exception Stack
			throw new NotAuthenticatedException();
		} else if (this.user == null && !nullOk) {
			throw new NotFindUserException();
		} else {
			return this.user.getId();
		}
	}

	@Override
	final public User getUser() {
		return getUser(false);
	}

	@Override
	final public User getUser(boolean nullOk) {
		return getUser(false, false);
	}

	@Override
	final public User getUser(boolean nullOk, boolean notAuthenticatedOk) {
		if (!notAuthenticatedOk) {
			//TODO: implement Exception Stack
			throw new NotAuthenticatedException();
		} else if (this.user == null && !nullOk) {
			throw new NotFindUserException();
		} else {
			return this.user;
		}
	}
}
