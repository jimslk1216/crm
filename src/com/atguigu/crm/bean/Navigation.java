package com.atguigu.crm.bean;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;


public class Navigation implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String text;

	private Set<Navigation> children = new LinkedHashSet<>();
	private String state;

	private String url;

	public Navigation() {
	}

	public Navigation(Long id, String text, Set<Navigation> children,
			String state, String url) {
		super();
		this.id = id;
		this.text = text;
		this.children = children;
		this.state = state;
		this.url = url;
	}

	@Override
	public String toString() {
		return "Navigation [id=" + id + ", text=" + text  + ", state=" + state + ", url=" + url + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Set<Navigation> getChildren() {
		return children;
	}

	public void setChildren(Set<Navigation> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
