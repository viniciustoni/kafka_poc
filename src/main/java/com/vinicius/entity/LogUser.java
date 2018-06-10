package com.vinicius.entity;

import java.util.Date;

public class LogUser {

    private Long userId;
    private String userName;
    private Date dataRegistry;

    public LogUser() {
    }

    public LogUser(Long userId, String userName, Date dataRegistry) {
	this.userId = userId;
	this.userName = userName;
	this.dataRegistry = dataRegistry;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public Date getDataRegistry() {
	return dataRegistry;
    }

    public void setDataRegistry(Date dataRegistry) {
	this.dataRegistry = dataRegistry;
    }

    @Override
    public String toString() {
	return "LogUser [userId=" + userId + ", userName=" + userName + ", dataRegistry=" + dataRegistry + "]";
    }
}
