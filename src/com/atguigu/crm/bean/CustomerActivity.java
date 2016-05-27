package com.atguigu.crm.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CustomerActivity extends IdEntity {
	// 顾客
	private Customer customer;
	// 时间
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	// 地点
	private String place;
	// 概要
	private String title;
	// 详细信息
	private String description;

	public Customer getCustomer() {
		return customer;
	}

	@Override
	public String toString() {
		return "CustomerActivity [customer=" + customer + ", date=" + date
				+ ", place=" + place + ", title=" + title + ", description="
				+ description + "]";
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
