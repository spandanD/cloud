package org.sdas.cloud.core.entity;

public class Channel<T> {
	private String name;
	private T content;

	public Channel(String name, T content){
		this.name = name;
		this.content = content;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

}
