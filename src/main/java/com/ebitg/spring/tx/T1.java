package com.ebitg.spring.tx;

import java.util.Date;

public class T1 {
	private int id;
	private int count;
	private Date updateTime;

	public void addCount() {
		count++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return id + "";
	}
}
