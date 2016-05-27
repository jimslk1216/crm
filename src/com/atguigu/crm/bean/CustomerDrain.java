package com.atguigu.crm.bean;

import java.util.Date;

public class CustomerDrain extends IdEntity {
	// 客户名称
	private Customer customer;
	// 订单时间
	private Date lastOrderDate;
	// 确认流失时间
	private Date drainDate;
	// 延缓流失
	private String delay;
	// 流失原因
	private String reason;
	// 身份
	private String status;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getLastOrderDate() {
		return lastOrderDate;
	}

	public void setLastOrderDate(Date lastOrderDate) {
		this.lastOrderDate = lastOrderDate;
	}

	public Date getDrainDate() {
		return drainDate;
	}

	public void setDrainDate(Date drainDate) {
		this.drainDate = drainDate;
	}

	public String getDelay() {
		return delay;
	}

	public void setDelay(String delay) {
		this.delay = delay;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CustomerDrain [customer=" + customer + ", lastOrderDate="
				+ lastOrderDate + ", drainDate=" + drainDate + ", delay="
				+ delay + ", reason=" + reason + ", status=" + status +"id"+this.id+ "]";
	}

}
