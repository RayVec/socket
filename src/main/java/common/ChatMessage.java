package common;

import java.io.Serializable;

public class ChatMessage implements Serializable{
	String type;
	String sender;
	String senderNick;
	int senderAvatar;
	String desk;
	String content;
	String sendTime;
	String cookie;
	int deskAvatar;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSenderNick() {
		return senderNick;
	}
	public void setSenderNick(String senderNick) {
		this.senderNick = senderNick;
	}
	public int getSenderAvatar() {
		return senderAvatar;
	}
	public void setSenderAvatar(int senderAvatar) {
		this.senderAvatar = senderAvatar;
	}
	public String getDesk() {
		return desk;
	}
	public void setDesk(String desk) {
		this.desk = desk;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public String getCookie(){return this.cookie;}
	public void setCookie(String cookie){this.cookie=cookie;}
	public void setDeskAvatar(int deskAvatar){this.deskAvatar=deskAvatar;}
	public int getDeskAvatar(){return this.deskAvatar;}
}
