package com.chatroom.model;

/**
 * WebSocket message model
 */
public class Message {
	private String type;
	private String username;
	private String msg;
	private Integer onlineCount;

	public Message(String username, String msg) {
		this.username = username;
		this.msg = msg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public String getMsg() {
		return msg;
	}

	public Integer getOnlineCount() {
		return onlineCount;
	}

	public void setOnlineCount(Integer count) {
		this.onlineCount = count;
	}

}
