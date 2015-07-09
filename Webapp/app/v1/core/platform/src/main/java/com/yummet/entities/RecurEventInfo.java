package com.yummet.entities;

import org.joda.time.DateTime;

import com.mongodb.ReflectionDBObject;
import com.yummet.enums.TimeUnit;

/**
 * @author jassica
 * @version 1
 */
public class RecurEventInfo extends ReflectionDBObject {
	private DateTime startDatetime;
	private DateTime lastHappenDatetime;
	private boolean isRecur;
	private int recurOffset;
	private TimeUnit  recurUnit;
	
	public RecurEventInfo(DateTime start) {
		assert (start != null);
		this.startDatetime = start;
	}
	
	public boolean isRecur() {
		return isRecur;
	}

	public void setRecur(boolean isRecur) {
		this.isRecur = isRecur;
	}

	public DateTime getStartDatetime() {
		return startDatetime;
	}

	public void setStartDatetime(DateTime startDatetime) {
		this.startDatetime = startDatetime;
	}

	public DateTime getLastHappenDatetime() {
		return lastHappenDatetime;
	}

	public void setLastHappenDatetime(DateTime lastHappenDatetime) {
		this.lastHappenDatetime = lastHappenDatetime;
	}

	public int getRecurOffset() {
		return recurOffset;
	}

	public void setRecurOffset(int recurOffset) {
		this.recurOffset = recurOffset;
	}

	public TimeUnit getRecurUnit() {
		return recurUnit;
	}

	public void setRecurUnit(TimeUnit recurUnit) {
		this.recurUnit = recurUnit;
	}
}